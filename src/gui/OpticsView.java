package gui;

import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.StrokeLineJoin;
import optics_logic.OpticsModel;
import optics_objects.templates.LightSource;
import optics_objects.templates.Material;
import optics_objects.templates.OpticsObject;
import util.Utils;

public class OpticsView {
	private OpticsModel model;
	private Canvas canvas;
	
	private OpticsObject selected;
	
	public OpticsView(double width, double height) {
		this.canvas = new Canvas(width, height);
		selected = null;
	}
	
	//TODO Do this in a better way for color mode
	public void calculateAndDrawRays() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		gc.setLineJoin(StrokeLineJoin.ROUND);
		gc.setGlobalBlendMode(BlendMode.SRC_OVER);
		gc.setFill(Paint.valueOf("BLACK"));
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
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
		
		Stop[] stops = new Stop[] { new Stop(0, new Color(1, 1, 1, 0.15)), new Stop(1, new Color(1, 1, 1, 0.5))};
		LinearGradient fillGradient = new LinearGradient(0, 0.5, 1, 0, true, CycleMethod.NO_CYCLE, stops);
		gc.setFill(fillGradient);
		for(Material m : model.getMaterials()) {
			if(m != selected) {
				m.draw(gc, model.getSettings());	
			}
		}
		if(selected != null) {
			stops = new Stop[] { new Stop(0, new Color(0.6, 1, 1, 0.15)), new Stop(1, new Color(0.6, 1, 1, 0.5))};
	        fillGradient = new LinearGradient(0, 0.5, 1, 0, true, CycleMethod.NO_CYCLE, stops);
			gc.setFill(fillGradient);
			selected.draw(gc, model.getSettings());
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
			l.draw(gc, model.getSettings());
		}
		gc.stroke();
	}
	
	public void select(OpticsObject obj) {
		this.selected = obj;
	}
	
	public Canvas getCanvas() {
		return canvas;
	}

	public void setOpticsModel(OpticsModel model) {
		this.model = model;
	}
}
