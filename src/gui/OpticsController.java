package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gui.optics_object_creators.OpticsObjectCreator;
import gui.optics_tabs.OpticsCreatorsBox;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import model.OpticsModel;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class OpticsController {
	public static final int EDGE_LEASE = 10;
	
	private OpticsModel model;
	private OpticsView view;
	private OpticsCreatorsBox opticsBox;
	
	private OpticsObjectCreator opticsObjectCreator;
	private OpticsObject draging;
	private Vector2d offset;
	private OpticsObject selected;
	private boolean dragged;
	private boolean rotating;
	private Vector2d lastPos;
	private double rotationFactor;

	public OpticsController(OpticsModel model, OpticsView view, OpticsCreatorsBox opticsBox) {
		this.model = model;
		this.view = view;
		this.opticsBox = opticsBox;
		
		connect(model, view);
		
		EventHandler<Event> creation = e -> {
			opticsObjectCreator = opticsBox.getCurrentOpticsObjectCreator();
		};
		
		opticsBox.onUpdated(e -> {
			view.redraw();
		});
		
		view.getCanvas().setOnTouchPressed(creation);
		view.getCanvas().setOnMousePressed(creation);
		
		rotationFactor = 1;
		draging = null;
		offset = Vector2d.zero();
		selected = null;
		this.dragged = false;
		this.rotating = false;
		
		redraw();	
	}

	private void connect(OpticsModel model, OpticsView view) {
		Canvas canvas = view.getCanvas();
		view.setOpticsModel(model);
		
		canvas.setOnMouseReleased(e -> {
			if(e.getButton() != MouseButton.MIDDLE) {
				Vector2d pos = view.getTablePos(e.getX(), e.getY());
				boolean inBounds = !(
						e.getX() < EDGE_LEASE ||
						e.getX() > canvas.getWidth() - EDGE_LEASE ||
						e.getY() < EDGE_LEASE ||
						e.getY() > canvas.getHeight() - EDGE_LEASE
						);
				
				if (draging != null) {
					if (!inBounds) {
						model.remove(draging);
						selected = null;
						view.deselect();
					} else {
						draging.setOrigin(pos.x + offset.x, pos.y + offset.y);
					}	
					draging = null;
				} else if (!dragged) {
					OpticsObject atMouse = model.getOpticsObjectAt(pos.x, pos.y);
					if(atMouse != null) {
						select(atMouse);
					} else if (inBounds && opticsObjectCreator != null) {
						OpticsObject newObj = opticsObjectCreator.getOpticsObject(new Vector2d(pos.x, pos.y));
						model.addOpticsObject(newObj);
						select(newObj);
					}
				}
				rotating = false;
				dragged = false;
			}
			lastPos = null;
			redraw();
		});

		
		canvas.setOnDragDetected(e -> {
			if(!e.getButton().equals(MouseButton.MIDDLE)) {
				Vector2d pos = view.getTablePos(e.getX(), e.getY());
				draging = model.getOpticsObjectAt(pos.x, pos.y);
				if(draging == null) {
					if(selected != null) {
						rotating = true;
					}
				} else {
					select(draging);
					offset = new Vector2d(pos.x, pos.y).sub(draging.getOrigin()).neg();
				}
				lastPos = pos;
			} else {
				lastPos = new Vector2d(e.getX(), e.getY());
			}
		});
		canvas.setOnMouseDragged(e -> {
			Vector2d currPos  = view.getTablePos(e.getX(), e.getY());
			
			if(e.getButton().equals(MouseButton.MIDDLE)) {
				if(lastPos != null)
				view.translate(e.getX() - lastPos.x, e.getY() - lastPos.y);
				lastPos = new Vector2d(e.getX(), e.getY());
			} else {
				dragged = true;
				if (draging != null) {
					draging.setOrigin(currPos.x + offset.x, currPos.y + offset.y);
				} else if (rotating) {
					if(lastPos != null)
					rotate(selected, lastPos, currPos);
					lastPos = currPos;
				}
			}
			redraw();
		});
		
		canvas.setOnRotate(e -> {
			if(draging != null) {
				draging.rotate(e.getAngle()/180.0*Math.PI*rotationFactor);
				redraw();
			}
		});
		
		canvas.setOnScroll(e -> {
			Vector2d p = view.getTablePos(e.getX(), e.getY());
			view.scale(e.getDeltaY()/400, p.x, p.y);
			redraw();
		});
	}
	
	private void select(OpticsObject obj) {
		selected = obj;
		opticsBox.setEditing(obj);
		view.select(obj);
	}
	
	private void deselect() {
		selected = null;
		view.deselect();
	}
	
	private void rotate(OpticsObject obj, Vector2d pFrom, Vector2d pTo) {
		Vector2d before = obj.getOrigin().copy().sub(pFrom);
		Vector2d after = obj.getOrigin().copy().sub(pTo);
		double angle = after.angleTo(before);
		if(Double.isFinite(angle))
		obj.rotate(angle*rotationFactor);
	}

	public void clear() {
		deselect();
		model.clear();
		redraw();
	}

	public void clearLights() {
		deselect();
		model.clearLights();
		redraw();
	}

	public void clearMaterials() {
		deselect();
		model.clearMaterials();
		redraw();
	}
	
	public void redraw() {
		view.redraw();
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
		deselect();
		redraw();
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

	public OpticsView getView() {
		return view;
	}
}
