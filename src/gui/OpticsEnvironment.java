package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import gui.optics_object_creators.OpticsObjectCreator;
import gui.optics_tabs.OpticsCreatorsBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import model.LensMaterial;
import model.OpticsModel;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class OpticsEnvironment {
	public static final int EDGE_LEASE = 10;
	
	private OpticsModel model;
	private OpticsCanvas view;
	private OpticsCreatorsBox opticsBox;
	public ObservableList<LensMaterial> modelLensMaterials;
	
	private OpticsObjectCreator opticsObjectCreator;
	private OpticsObject draging;
	private Vector2d offset;
	private OpticsObject selected;
	private boolean dragged;
	private boolean rotating;
	private boolean movingRotationPoint;
	private Vector2d lastPos;
	private double rotationFactor;
	private boolean movableRotationPoint;

	public OpticsEnvironment(OpticsModel model, OpticsCanvas view) {
		
		this.model = model;
		this.modelLensMaterials = FXCollections.observableArrayList(model.getMetadata().getLensMaterials());
		this.view = view;
		
		connect(model, view);
		
		rotationFactor = 1;
		draging = null;
		offset = Vector2d.zero();
		selected = null;
		this.dragged = false;
		this.rotating = false;
		this.movingRotationPoint = false;
		this.movableRotationPoint = false;
		redraw();	
	}
	
	public void connectOpticsBox(OpticsCreatorsBox opticsBox) {
		this.opticsBox = opticsBox;
		EventHandler<Event> creation = e -> {
			opticsObjectCreator = opticsBox.getCurrentOpticsObjectCreator();
		};
		
		opticsBox.onUpdated(e -> {
			view.redraw();
		});
		
		view.getCanvas().setOnTouchPressed(creation);
		view.getCanvas().setOnMousePressed(creation);
	}

	private void connect(OpticsModel model, OpticsCanvas view) {
		Canvas canvas = view.getCanvas();
		view.setOpticsModel(model);
		
		canvas.setOnMouseReleased(e -> {
			if(!e.getButton().equals(MouseButton.MIDDLE)) {
				release(e.getX(), e.getY());
			}
			lastPos = null;
		});
		canvas.setOnTouchReleased(e -> {
			release(e.getTouchPoint().getX(), e.getTouchPoint().getY());
		});

		
		canvas.setOnDragDetected(e -> {
			if(!e.getButton().equals(MouseButton.MIDDLE)) {
				Vector2d pos = view.getTablePos(e.getX(), e.getY());
				draging = model.getOpticsObjectAt(pos.x, pos.y);
				if(selected != null) {
					if(selected.withinRotationPoint(pos) && movableRotationPoint) {
						movingRotationPoint = true;
					}
				}
				if(draging == null) {
					if(selected != null && !movingRotationPoint) {
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
				if(lastPos != null) {
					view.translate(e.getX() - lastPos.x, e.getY() - lastPos.y);
				}
				lastPos = new Vector2d(e.getX(), e.getY());
			} else {
				dragged = true;
				if(movingRotationPoint) {
					selected.setRotationOrigin(currPos.x, currPos.y);
				} else if (draging != null) {
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
			if(e.getTouchCount() == 0) {
				Vector2d p = view.getTablePos(e.getX(), e.getY());
				view.scale(e.getDeltaY()/400, p.x, p.y);
				redraw();
			}
		});
		
		canvas.setOnZoom(e -> {
			Vector2d p = view.getTablePos(e.getX(), e.getY());
			view.scale(e.getZoomFactor(), p.x, p.y);
		});
	}
	
	public void onKeyReleased(KeyEvent e) {
		if (e.getCode() == KeyCode.DELETE && selected != null) {
			model.remove(selected);
			selected = null;
			view.deselect();
			redraw();
		}
	}
	
	private void release(double x, double y) {
		Vector2d pos = view.getTablePos(x, y);
		Canvas canvas = view.getCanvas();
		
		boolean inBounds = !(
				x < EDGE_LEASE ||
				x > canvas.getWidth() - EDGE_LEASE ||
				y < EDGE_LEASE ||
				y > canvas.getHeight() - EDGE_LEASE
				);
		
		if (draging != null) {
			if (!inBounds) {
				model.remove(draging);
				selected = null;
				view.deselect();
			} else if(!movingRotationPoint) {
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
		lastPos = null;
		movingRotationPoint = false;
		redraw();
	}
	
	private void select(OpticsObject obj) {
		if(!obj.getBool("NoFocus")) {
			selected = obj;
			opticsBox.setEditing(obj);
			view.select(obj);
		}
	}
	
	public void deselect() {
		selected = null;
		view.deselect();
	}
	
	private void rotate(OpticsObject obj, Vector2d pFrom, Vector2d pTo) {
		Vector2d before = obj.getRotationOrigin().sub(pFrom);
		Vector2d after = obj.getRotationOrigin().sub(pTo);
		double angle = after.angleToInDegrees(before);
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
	
	public OpticsCreatorsBox getOpticsCreatorBox() {
		return opticsBox;
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

	public OpticsCanvas getView() {
		return view;
	}

	public void setMovableRotationPoint(boolean b) {
		this.movableRotationPoint = b;
	}

	public ObservableList<LensMaterial> getLensMaterials() {
		return modelLensMaterials;
	}
}
