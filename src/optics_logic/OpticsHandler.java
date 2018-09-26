package optics_logic;

import java.util.ArrayList;
import java.util.List;

import gui.Main;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.TouchEvent;
import javafx.scene.paint.Paint;
import optics_object_factories.OpticsObjectFactory;
import optics_objects.Material;
import optics_objects.OpticsObject;
import optics_objects.LightSource;
import util.Vector2d;

public class OpticsHandler {
	public static final int EDGE_LEASE = 10;
	
	OpticsObjectFactory opticsObjectCreator;
	Canvas canvas;
	List<Material> materials;
	List<LightSource> lights;
	OpticsObject draging;
	Vector2d deltaPos;
	double rotationFactor;

	public OpticsHandler(Canvas canvas) {
		rotationFactor = 1;
		draging = null;
		this.canvas = canvas;
		clear();
		// Place objects/move an object that was picked up.
		canvas.setOnMouseReleased(e -> {
			double x = e.getX();
			double y = e.getY();
			if (draging != null) {
				if (x < EDGE_LEASE || x > canvas.getWidth() - EDGE_LEASE || y < EDGE_LEASE
						|| y > canvas.getHeight() - EDGE_LEASE) {
					if (draging instanceof Material) {
						materials.remove(draging);
						calculateRayPaths();
					} else if (draging instanceof LightSource) {
						lights.remove(draging);
					}
				} else {
					draging.setOrigin(new Vector2d(x, y).add(deltaPos));
					update(draging);
				}
				
				draging = null;
			} else {
				if (!(x < EDGE_LEASE || x > canvas.getWidth() - EDGE_LEASE || y < EDGE_LEASE
						|| y > canvas.getHeight() - EDGE_LEASE)) {
					createObject(new Vector2d(x, y));
				}
			}
			redraw();
			e.consume();
		});

		// Draging objects arround.
		canvas.setOnDragDetected(e -> {
			Vector2d pos = new Vector2d(e.getX(), e.getY());
			double closest = Double.MAX_VALUE;
			for (LightSource l : lights) {
				double dist = l.getOrigin().dist(pos);
				if (dist < Main.HEIGHT / 10 && closest > dist) {
					closest = dist;
					draging = l;
					deltaPos = l.getOrigin().copy().sub(pos);
				}
			}
			for (Material l : materials) {		
				if(l.withinTouchHitBox(pos)) {
					double dist = l.getOrigin().dist(pos);
					if (closest > dist) {
						closest = dist;
						draging = l;
						deltaPos = l.getOrigin().copy().sub(pos);
					}
				}
			}
			e.consume();
		});
		
		// Update and draw when the mouse is moved.
		canvas.setOnMouseDragged(e -> {
			Vector2d pos = new Vector2d(e.getX(), e.getY());
			if (draging != null) {
				draging.setOrigin(pos.add(deltaPos));
				update(draging);
				redraw();
			}
			e.consume();
		});
		
		//Rotation.
		canvas.setOnRotate(e -> {
			if(draging != null) {
				draging.rotate(e.getAngle()/180.0*Math.PI*rotationFactor);
				update(draging);
				redraw();
			}
			e.consume();
		});
	}

	public void setOpticsObjectCreator(OpticsObjectFactory objCreator) {
		this.opticsObjectCreator = objCreator;
	}

	private void createObject(Vector2d origin) {
		if (opticsObjectCreator != null) {
			OpticsObject newOpticsObj = opticsObjectCreator.getOpticsObject(origin);
			if(newOpticsObj != null) {
				if (newOpticsObj instanceof LightSource) {
					LightSource newLight = (LightSource) newOpticsObj;
					lights.add(newLight);
					newLight.calculateRayPaths(materials);
				} else if (newOpticsObj instanceof Material) {
					materials.add((Material) newOpticsObj);
					calculateRayPaths();
				}
			}
		}
	}
	
	private void update(OpticsObject o) {
		if (o instanceof LightSource) {
			((LightSource) o).calculateRayPaths(materials);
		} else if (o instanceof Material) {
			calculateRayPaths();
		}
	}

	private void calculateRayPaths() {
		for(LightSource l : lights) {
			l.calculateRayPaths(materials);
		}
	}

	public void redraw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Paint.valueOf("BLACK"));
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		if(LightSource.WHITE) gc.setGlobalBlendMode(BlendMode.SCREEN);
		for(LightSource l : lights) {
			l.draw(gc);
		}
		gc.setGlobalBlendMode(BlendMode.SRC_OVER);
		for(Material m : materials) {
			m.draw(gc);
		}
	}
	
	public void setEvent(EventHandler<TouchEvent> e) {
		canvas.setOnTouchPressed(e);
	}

	public void clear() {
		lights.clear();
		clearMaterials();
	}

	public void clearLights() {
		lights.clear();
		redraw();
	}

	public void clearMaterials() {
		materials.clear();
		calculateRayPaths();
		redraw();
	}
	
	public void setRotationFactor(double fac) {
		this.rotationFactor = fac;
	}

	public Object getOpticsObjectList() {
		return new ArrayList<OpticsObject>(materials).addAll(lights);
	}

	public void setOpticsObjects(List<OpticsObject> opticsObjects) {
		materials.clear();
		lights.clear();
		for(OpticsObject o : opticsObjects) {
			if(o instanceof Material) {
				materials.add((Material)o);
			} else {
				lights.add((LightSource)o);
			}
		}
		calculateRayPaths();
		redraw();
	}
}
