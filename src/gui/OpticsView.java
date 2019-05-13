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

public class OpticsView {
	private OpticsModel model;
	private Canvas canvas;
	
	public OpticsView(double width, double height) {
		this.canvas = new Canvas(width, height);
	}
	
	//TODO Do this in a better way for color mode
	public void drawView(OpticsObject picked) {
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
					wavelength <= LightSource.lightWaveMax();
					wavelength += step/LightSource.colorModeRayCount()) {
				calculateAndDrawRays(model.getLights(), gc, wavelength, 0.6f);
			}
			
		} else {
			for(LightSource l : model.getLights()) {
				calculateAndDrawRay(l, gc, 1);
			}
		}
		gc.setGlobalBlendMode(BlendMode.SRC_OVER);
		
		//Draw lenses
		for(Material m : model.getMaterials()) {
			m.draw(gc, model.getSettings());
		}
		
		if(picked!=null) {
			picked.drawSelection(gc, model.getSettings());
		}

	}
	
	private void calculateAndDrawRay(LightSource l, GraphicsContext gc, float alpha) {
		l.calculateRayPaths(model.getMaterials());

		int rgb[] = Utils.waveLengthToRGB(l.getWaveLength());
		Paint p = Color.rgb(rgb[0], rgb[1], rgb[2], alpha);
		gc.setStroke(p);

		gc.beginPath();
		l.draw(gc, model.getSettings());
		gc.stroke();
	}

	private void calculateAndDrawRays(List<LightSource> lights, GraphicsContext gc, int wavelength, float alpha) {

		//Den utkommenterade koden kommer att krascha programmet om man introducerar en färg-ljuskälla till en
		//diffraction grating, i vissa fall. Den är dock snabbare, så jag låter den stå kvar om någon vill
		//försöka optimera.

		for(LightSource l : lights) {
			l.setWaveLength(wavelength);
			calculateAndDrawRay(l, gc, alpha);
			//l.calculateRayPaths(model.getMaterials());
		}
		/*int rgb[] = Utils.waveLengthToRGB(wavelength);
		Paint p = Color.rgb(rgb[0], rgb[1], rgb[2], alpha);
		gc.setStroke(p);

		gc.beginPath();
		for(LightSource l : lights) {
			l.draw(gc, model.getSettings());
		}
		gc.stroke();*/
	}
	
	public Canvas getCanvas() {
		return canvas;
	}

	public void setOpticsModel(OpticsModel model) {
		this.model = model;
	}
}
