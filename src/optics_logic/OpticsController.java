package optics_logic;

import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.TouchEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import optics_object_factories.OpticsObjectFactory;
import optics_objects.templates.LightSource;
import optics_objects.templates.Material;
import optics_objects.templates.OpticsObject;
import util.Utils;

public class OpticsController {
	public static final int EDGE_LEASE = 10;
	
	OpticsModel model;
	
	OpticsObjectFactory oof;
	OpticsObject draging;
	
	Canvas canvas;
	double rotationFactor;

	public OpticsController(OpticsModel model, Canvas canvas) {
		this.model = model;
		this.canvas = canvas;
		connect(model, canvas);
		
		rotationFactor = 1;
		draging = null;
		
		calculateAndDrawRays();
		
	}

	public void setOpticsObjectCreator(OpticsObjectFactory oof) {
		this.oof = oof;
	}

	public void calculateAndDrawRays() {
		
		//TODO refactor and create canvas class that takes and views opticsModel
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Paint.valueOf("BLACK"));
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setGlobalBlendMode(BlendMode.SRC_OVER);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Material m : model.getMaterials()) {
			m.draw(gc);
		}
		if(LightSource.WHITE) {
			//canvas.setEffect(new BoxBlur(5, 5, 3)); ??? TODO
			gc.setGlobalBlendMode(BlendMode.SCREEN);
			int step = LightSource.LIGHTWAVEMAX - LightSource.LIGHTWAVEMIN;
			for(int wavelength = LightSource.LIGHTWAVEMIN; wavelength < LightSource.LIGHTWAVEMAX; wavelength += step/12) {
				calculateAndDrawRays(model.getLights(), gc, wavelength, 0.6f);
			}
		} else {
			calculateAndDrawRays(model.getLights(), gc, LightSource.DEFAULTWAVE, 1f);
		}
	}
	
	private void calculateAndDrawRays(List<LightSource> lights, GraphicsContext gc, int wavelength, float alpha) {
		
		for(LightSource l : lights) {
			l.calculateRayPaths(model.getMaterials(), wavelength);
		}
		int rgb[] = Utils.waveLengthToRGB(wavelength);
		gc.setStroke(Color.rgb(rgb[0], rgb[1], rgb[2], alpha));
		gc.beginPath();
		for(LightSource l : lights) {
			l.draw(gc);
		}
		gc.stroke();
	}
	
	private void connect(OpticsModel model, Canvas canvas) {
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
					model.addOpticsObject(oof, x, y);
				}
			}
			calculateAndDrawRays();
		});

		
		canvas.setOnDragDetected(e -> {
			draging = model.getOpticsObjectAt(e.getX(), e.getY());
		});
		
		canvas.setOnMouseDragged(e -> {
			if (draging != null) {
				draging.setOrigin(e.getX(), e.getY());
				calculateAndDrawRays();
			}
		});
		
		canvas.setOnRotate(e -> {
			if(draging != null) {
				draging.rotate(e.getAngle()/180.0*Math.PI*rotationFactor);
				calculateAndDrawRays();
			}
			e.consume();
		});
	}
	
	public void setEvent(EventHandler<TouchEvent> e) {
		canvas.setOnTouchPressed(e);
		//TODO fix better communication line
	}

	public void clear() {
		model.clear();
		clearMaterials();
	}

	public void clearLights() {
		model.clearLights();
		calculateAndDrawRays();
	}

	public void clearMaterials() {
		model.clearMaterials();
		calculateAndDrawRays();
	}
	
	public void setRotationFactor(double fac) {
		this.rotationFactor = fac;
	}

	public OpticsModel getOpticsModel() {
		return model;
	}

	public void setOpticsModel(OpticsModel model) {
		this.model = model;
		connect(model, canvas);
		calculateAndDrawRays();
	}

	public Canvas getCanvas() {
		return canvas;
	}
}
