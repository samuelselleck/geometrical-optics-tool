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
import util.Utils;

public class OpticsView {
	private OpticsModel model;
	private Canvas canvas;
	private String actionText="";
	
	public OpticsView(double width, double height) {
		this.canvas = new Canvas(width, height);
	}
	
	public void setActionText(String text) {
		actionText = text;
	}
	
	//TODO Do this in a better way for color mode
	public void drawView() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		gc.setLineJoin(StrokeLineJoin.ROUND);
		gc.setGlobalBlendMode(BlendMode.SRC_OVER);
		gc.setFill(Paint.valueOf("BLACK"));
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		//Draw light rays
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
		
		//Draw lences
		for(Material m : model.getMaterials()) {
			m.draw(gc, model.getSettings());
		}

		gc.setFill(Paint.valueOf("GRAY"));
		gc.fillRect(0, canvas.getHeight()-20, 200, 20);
		
		gc.setStroke(Paint.valueOf("WHITE"));
		gc.strokeText(actionText, 10, canvas.getHeight()-5);
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
	
	public Canvas getCanvas() {
		return canvas;
	}

	public void setOpticsModel(OpticsModel model) {
		this.model = model;
	}
}
