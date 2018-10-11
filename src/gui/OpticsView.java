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
	OpticsModel model;
	Canvas canvas;
	
	public OpticsView(double width, double height) {
		this.canvas = new Canvas(width, height);
	}
	
	public void calculateAndDrawRays() {
		
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
	
	public Canvas getCanvas() {
		return canvas;
	}

	public void setModel(OpticsModel model) {
		this.model = model;
	}
}
