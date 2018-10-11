package gui;


import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.TouchEvent;
import optics_logic.OpticsModel;
import optics_object_factories.OpticsObjectFactory;
import optics_objects.templates.OpticsObject;

public class OpticsController {
	public static final int EDGE_LEASE = 10;
	
	OpticsModel model;
	
	OpticsObjectFactory opticsObjectFactory;
	OpticsObject draging;
	
	OpticsView view;
	double rotationFactor;

	public OpticsController(OpticsModel model, OpticsView view) {
		this.model = model;
		this.view = view;
		connect(model, view);
		
		rotationFactor = 1;
		draging = null;
		
		redraw();
		
	}

	public void setOpticsObjectCreator(OpticsObjectFactory oof) {
		this.opticsObjectFactory = oof;
	}

	
	
	private void connect(OpticsModel model, OpticsView view) {
		Canvas canvas = view.getCanvas();
		view.setModel(model);
		
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
				draging = null;
			} else {
				if (inBounds) {
					model.addOpticsObject(opticsObjectFactory, x, y);
				}
			}
			redraw();
		});

		
		canvas.setOnDragDetected(e -> {
			draging = model.getOpticsObjectAt(e.getX(), e.getY());
		});
		
		canvas.setOnMouseDragged(e -> {
			if (draging != null) {
				draging.setOrigin(e.getX(), e.getY());
				redraw();
			}
		});
		
		canvas.setOnRotate(e -> {
			if(draging != null) {
				draging.rotate(e.getAngle()/180.0*Math.PI*rotationFactor);
				redraw();
			}
		});
	}
	
	public void setEvent(EventHandler<TouchEvent> e) {
		view.getCanvas().setOnTouchPressed(e);
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

	public Canvas getCanvas() {
		return view.getCanvas();
	}
}
