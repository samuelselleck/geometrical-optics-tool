package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import optics_logic.OpticsModel;
import optics_logic.OpticsSettings;
import optics_object_factories.OpticsObjectFactory;
import optics_objects.materials.DiffractionGrating;
import optics_objects.templates.Material;
import optics_objects.templates.OpticsObject;
import settings.BigSettingsBox;
import util.Vector2d;

public class OpticsController {
	public static final int EDGE_LEASE = 10;
	
	private OpticsModel model;
	
	private OpticsObjectFactory opticsObjectFactory;
	private BigSettingsBox settings;
	private Vector2d offset;
	private Vector2d lastPos;
	
	private OpticsObject picked;
	
	private OpticsView view;
	private double rotationFactor;
	
	private boolean dragged;
	private boolean rotating;
	private boolean pickedThisClick;

	public OpticsController(OpticsModel model, OpticsView view) {
		this.model = model;
		this.view = view;
		connect(model, view);
		
		rotationFactor = 1;
		offset = Vector2d.zero();
		
		picked = null;
		this.dragged = false;
		this.rotating = false;
		this.pickedThisClick = false;
		
		redraw();	
	}

	public void setOpticsObjectCreator(OpticsObjectFactory oof) {
		this.opticsObjectFactory = oof;
	}
	
	public void setSettingsBox(BigSettingsBox b) {
		settings = b;
	}

	private void connect(OpticsModel model, OpticsView view) {
		Canvas canvas = view.getCanvas();
		view.setOpticsModel(model);
		
		canvas.setOnMouseReleased(e -> {
			double x = e.getX();
			double y = e.getY();
			
			boolean inBounds = this.inBounds(e.getX(), e.getY());
			
			if (picked != null) {
				//Delete object if outside window
				if (!inBounds) {
					model.remove(picked);
					if(picked instanceof DiffractionGrating){
						model.remove(((DiffractionGrating) picked).getLightSource());
					}
					picked = null;
				} else if(!rotating){
					picked.setOrigin(x + offset.x, y + offset.y);
				}
				
				if(!dragged && !pickedThisClick) {
					picked = null;
				}
				
			} else {
				if (inBounds && opticsObjectFactory != null) {
					OpticsObject newObj = opticsObjectFactory.getOpticsObjectWithId(new Vector2d(x, y));
					model.addOpticsObject(newObj);
					picked = newObj;
				}
			}
			
			rotating = false;
			dragged = false;
			pickedThisClick = false;
			redraw();
		});
		
		canvas.setOnMouseDragged(e -> {
		
			dragged = true;
			if (picked != null) {
				 if(rotating) {
						Vector2d currPos = new Vector2d(e.getX(), e.getY());
						rotate(picked, lastPos, currPos);
						lastPos = currPos;
				
				 } else if(picked.isFixed()) {
					Vector2d currPos = new Vector2d(e.getX(), e.getY());
					rotate(picked, lastPos, currPos);
					lastPos = currPos;
				 } else {
					picked.setOrigin(e.getX() + offset.x, e.getY() + offset.y);
				 }
				 
			}
			
			redraw();
		});
		
		canvas.setOnRotate(e -> {
			
			if(picked != null) {
				picked.rotate(e.getAngle()/180.0*Math.PI*rotationFactor);
				redraw();
			}
			
		});
	
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
		
			OpticsObject hit = model.getOpticsObjectAt(e.getX(), e.getY());
			
			if(hit!=null) {
				picked = hit;
				settings.selectSettingsTab(picked);
				pickedThisClick = true;
			}
			
			if(picked!=null) {
			if(!picked.withinTouchHitBox(new Vector2d(e.getX(),e.getY()))) {
				rotating = true;

			} else {
				offset = new Vector2d(e.getX(), e.getY()).sub(picked.getOrigin()).neg();
			}
			lastPos = new Vector2d(e.getX(), e.getY());
			}
			
			redraw();
		});
	}
	
	private void rotate(OpticsObject obj, Vector2d pFrom, Vector2d pTo) {
		Vector2d before = obj.getOrigin().copy().sub(pFrom);
		Vector2d after = obj.getOrigin().copy().sub(pTo);
		double angle = after.angleTo(before);
		if(Double.isFinite(angle))
		obj.rotate(angle*rotationFactor);
	}
	
	public void setBeforeObjectCreation(EventHandler<Event> e) {
		view.getCanvas().setOnTouchPressed(e);
		view.getCanvas().setOnMousePressed(e);
		//TODO fix better communication line
	}

	public void clear() {
		model.clear();
		picked = null;
		redraw();
	}

	public void clearLights() {
		model.clearLights();
		picked = null;
		redraw();
	}

	public void clearMaterials() {
		model.clearMaterials();
		picked = null;
		redraw();
	}
	
	public void redraw() {
		view.drawView(picked);
	}
	
	public void setRotationFactor(double fac) {
		this.rotationFactor = fac;
	}

	public OpticsModel getOpticsModel() {
		return model;
	}

	public void setOpticsModel(OpticsModel model) {
		this.model = model;
		connect(model, view);
		redraw();
	}
	
	public OpticsSettings getModelSettings() {
		return model.getSettings();
	}
	
	public void saveScreenshotTo(File saveFile) {
		WritableImage image = new WritableImage(
				(int)view.getCanvas().getWidth(), (int)view.getCanvas().getHeight());
		image = view.getCanvas().snapshot(null, image);
		BufferedImage screenshot = SwingFXUtils.fromFXImage(image, null);
		try {
			ImageIO.write(screenshot, "png", saveFile);
		} catch (IOException e) {
			System.err.println("Failed to save screenshot");
		}
	}
	
	private boolean inBounds(double x, double y) {
		return !(
				x < EDGE_LEASE ||
				x > view.getCanvas().getWidth() - EDGE_LEASE ||
				y < EDGE_LEASE ||
				y > view.getCanvas().getHeight() - EDGE_LEASE
				);
	}
	
	public OpticsObjectFactory currentOpticsObjectFactory() {
		return this.opticsObjectFactory;
	}

	public EventHandler getSettingsUpdateListener() {
		return e -> {
			if(picked!=null) {
				this.opticsObjectFactory.updateOpticsObject(picked);
			}
			
			redraw();
		};
	}
	
	public EventHandler getDeselectListener() {
		return e-> {
			picked = null;
			redraw();
		};
	}
}
