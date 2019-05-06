package optics_objects.templates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gui.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import optics_logic.LightRay;
import optics_logic.OpticsSettings;
import util.Vector2d;

public abstract class LightSource extends OpticsObject {
	private static final long serialVersionUID = 1L;
	ArrayList<LightRay> light;
	private int waveLength = 440;

	public LightSource(Vector2d origin, int rayCount, boolean fixedPosition) {
		super(origin, fixedPosition);
		light = new ArrayList<>();
	}
	
	public int getWaveLength() {
		return waveLength;
	}
	
	public void setWaveLength(int w) {
		waveLength = w;
	}

	public void calculateRayPaths(List<Material> materials, int wavelength) {
		light.stream().forEach(l -> {
			l.calculatePath(materials, wavelength);
		});
	}
	
	
	protected void clearLightRays() {
		light.clear();
	}

	@Override
	public void draw(GraphicsContext gc, OpticsSettings settings) {
		for(LightRay l : light) {
			l.draw(gc, settings.drawOnlyHitting());
		}
	}

	@Override
	public void drawSelection(GraphicsContext gc, OpticsSettings settings) {
		gc.setStroke(Paint.valueOf("GRAY"));
		
		double r = getRadius();
		gc.strokeOval(this.getOrigin().x-r, this.getOrigin().y-r, r*2, r*2);
	}
	
	@Override
	public void rotateOp(double angle) {
		for(LightRay l : light) {
			l.rotate(angle);
		}
	}
	
	private double getRadius() {
		return (Main.HEIGHT/10);
	}
	
	public boolean withinTouchHitBox(Vector2d pos) {
		return pos.distSquared(this.getOrigin()) < getRadius()*getRadius();
	}
	
	public void addLightRay(Vector2d offset, Vector2d ray) {
			light.add(new LightRay(origin, offset, ray));
	}
	
	public void addLightRay(Vector2d ray) {
		addLightRay(new Vector2d(0, 0), ray);
	}
	
	public static int lightWaveMax() {
		return Integer.parseInt(Main.properties.getProperty("maxwavelength"));
	}
	
	public static int lightWaveMin() {
		return Integer.parseInt(Main.properties.getProperty("minwavelength"));
	}
	
	public static int lightWaveDefault() {
		return Integer.parseInt(Main.properties.getProperty("defaultwavelength"));
	}
	
	public static int colorModeRayCount() {
		return Integer.parseInt(Main.properties.getProperty("colormoderaycount"));
	}
}
