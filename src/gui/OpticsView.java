package gui;

import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineJoin;
import optics_logic.OpticsModel;
import optics_objects.templates.LightSource;
import optics_objects.templates.Material;
import optics_objects.templates.OpticsObject;
import util.Utils;
import util.Vector2d;

public class OpticsView {
	private OpticsModel model;
	private GraphicsContext gc;
	private Canvas canvas;
	private double scale, xTranslation, yTranslation;
	
	private OpticsObject selected;
	
	public OpticsView(double width, double height) {
		this.canvas = new Canvas(width, height);
		this.gc = canvas.getGraphicsContext2D();
		gc.setLineJoin(StrokeLineJoin.ROUND);
		gc.save();
		this.scale = 1;
		this.xTranslation = 0;
		this.yTranslation = 0;
		selected = null;
	}
	
	//TODO Do this in a better way for color mode
	public void redraw() {
		
		gc.setGlobalBlendMode(BlendMode.SRC_OVER);
		gc.setFill(Paint.valueOf("BLACK"));
		Vector2d p1 = getTablePos(0, 0);
		Vector2d p2 = getTablePos(canvas.getWidth(), canvas.getHeight());
		gc.fillRect(p1.x, p1.y, p2.x - p1.x, p2.y - p1.y);
		gc.setLineWidth(0.5);
		
		if(model.getSettings().colorMode()) {
			gc.setGlobalBlendMode(BlendMode.SCREEN);
			int step = LightSource.lightWaveMax() - LightSource.lightWaveMin();
			for(int wavelength = LightSource.lightWaveMin();
					wavelength < LightSource.lightWaveMax();
					wavelength += step/LightSource.colorModeRayCount()) {
				calculateAndDrawRays(model.getLights(), gc, wavelength, 0.6f);
			}
			
		} else {
			calculateAndDrawRays(model.getLights(), gc, LightSource.lightWaveDefault(), 1f);
		}
		
		gc.setGlobalBlendMode(BlendMode.SRC_OVER);
		
		for(Material m : model.getMaterials()) {
			if(m != selected) {
				m.draw(gc, model.getSettings(), false);	
			}
		}
		if(selected != null) {
			selected.draw(gc, model.getSettings(), true);
		}
	}
	
	private void calculateAndDrawRays(List<LightSource> lights, GraphicsContext gc, int wavelength, float alpha) {
		
		for(LightSource l : lights) {
			l.calculateRayPaths(model.getMaterials(), wavelength);
		}
		int rgb[] = Utils.waveLengthToRGB(wavelength);
		Paint p = Color.rgb(rgb[0], rgb[1], rgb[2], alpha);
		gc.setStroke(p);
		gc.beginPath();
		for(LightSource l : lights) {
			l.draw(gc, model.getSettings(), false);
		}
		gc.stroke();
	}
	
	public void select(OpticsObject obj) {
		this.selected = obj;
	}
	
	public void deselect() {
		this.selected = null;
	}
	
	public void translate(double x, double y) {
		xTranslation += x;
		yTranslation += y;
		gc.translate(x/scale, y/scale);
	}
	
	public void scale(double factor, double x, double y) {
		double old = scale;
		scale += factor;
		scale = Math.max(scale, 0.5);
		scale = Math.min(scale, 2);
		
		gc.scale(scale/old, scale/old);
		translate(-x*(scale - old), -y*(scale - old));
	}
	
	public void resetView() {
		gc.restore();
		gc.save();
		xTranslation = 0;
		yTranslation = 0;
		scale = 1;
	}
	
	public Vector2d getTablePos(double screenX, double screenY) {
		return new Vector2d((-xTranslation + screenX)/scale, (-yTranslation + screenY)/scale);
	}
	
	public Canvas getCanvas() {
		return canvas;
	}

	public void setOpticsModel(OpticsModel model) {
		this.model = model;
	}
}
