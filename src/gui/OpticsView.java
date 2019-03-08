package gui;

import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import optics_logic.OpticsModel;
import optics_objects.templates.LightSource;
import optics_objects.templates.Material;
import util.Utils;

public class OpticsView {
	private OpticsModel model;
	private Canvas canvas;
	
	public OpticsView(double width, double height) {
		this.canvas = new Canvas(width, height);
	}
	
	//TODO Do this in a better way for color mode
	public void calculateAndDrawRays() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Paint.valueOf("BLACK"));
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setGlobalBlendMode(BlendMode.SRC_OVER);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Material m : model.getMaterials()) {
			m.draw(gc, model.getSettings());
		}
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
	}
	
	private void calculateAndDrawRays(List<LightSource> lights, GraphicsContext gc, int wavelength, float alpha) {
		
		for(LightSource l : lights) {
			l.calculateRayPaths(model.getMaterials(), wavelength);
		}
		int rgb[] = Utils.waveLengthToRGB(wavelength);
		gc.setStroke(Color.rgb(rgb[0], rgb[1], rgb[2], alpha));
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
