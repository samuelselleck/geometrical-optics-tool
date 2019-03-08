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
import optics_logic.OpticsModel;
import optics_logic.OpticsSettings;
import optics_object_factories.OpticsObjectFactory;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class OpticsController {
	public static final int EDGE_LEASE = 10;
	
	private OpticsModel model;
	
	private OpticsObjectFactory opticsObjectFactory;
	private OpticsObject draging;
	private OpticsObject lastDragged;
	private boolean dragged;
	private boolean rotating;
	private Vector2d lastPos;
	
	private OpticsView view;
	private double rotationFactor;

	public OpticsController(OpticsModel model, OpticsView view) {
		this.model = model;
		this.view = view;
		connect(model, view);
		
		rotationFactor = 1;
		draging = null;
		lastDragged = null;
		this.dragged = false;
		this.rotating = false;
		
		redraw();	
	}

	public void setOpticsObjectCreator(OpticsObjectFactory oof) {
		this.opticsObjectFactory = oof;
	}

	private void connect(OpticsModel model, OpticsView view) {
		Canvas canvas = view.getCanvas();
		view.setOpticsModel(model);
		
		canvas.setOnMouseReleased(e -> {
			double x = e.getX();
			double y = e.getY();
			boolean inBounds = !(
					x < EDGE_LEASE ||
					x > canvas.getWidth() - EDGE_LEASE ||
					y < EDGE_LEASE ||
					y > canvas.getHeight() - EDGE_LEASE
					);
			
			if (draging != null) {
				if (!inBounds) {
					model.remove(draging);
				} else {
					draging.setOrigin(x, y);
				}	
				lastDragged = draging;
				draging = null;
			} else if (!dragged) {
				if (inBounds) {
					model.addOpticsObject(opticsObjectFactory, x, y);
				}
			}
			rotating = false;
			dragged = false;
			redraw();
		});

		
		canvas.setOnDragDetected(e -> {
			draging = model.getOpticsObjectAt(e.getX(), e.getY());
			if(draging == null) {
				rotating = true;
			}
			lastPos = new Vector2d(e.getX(), e.getY());
		});
		
		canvas.setOnMouseDragged(e -> {
			dragged = true;
			if (draging != null) {
				if(draging.isFixed()) {
					Vector2d currPos = new Vector2d(e.getX(), e.getY());
					rotate(draging, lastPos, currPos);
					lastPos = currPos;
				} else {
					draging.setOrigin(e.getX(), e.getY());
				}
			} else if (rotating) {
				Vector2d currPos = new Vector2d(e.getX(), e.getY());
				rotate(lastDragged, lastPos, currPos);
				lastPos = currPos;
			}
			redraw();
		});
		
		canvas.setOnRotate(e -> {
			if(draging != null) {
				draging.rotate(e.getAngle()/180.0*Math.PI*rotationFactor);
				redraw();
			}
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
		redraw();
	}

	public void clearLights() {
		model.clearLights();
		redraw();
	}

	public void clearMaterials() {
		model.clearMaterials();
		redraw();
	}
	
	public void redraw() {
		view.calculateAndDrawRays();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
