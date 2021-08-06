package model.optics_objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import model.LensMaterial;
import model.ModelMetadata;
import model.RayIntersectionData;
import util.Vector2d;

public abstract class Lens extends Material {
	private static final long serialVersionUID = 1L;
	
	public Lens(Map<String, DoubleProperty> properties) {
		super(properties);
	}
	
	@Override
	public List<Vector2d> getScatteredLight(RayIntersectionData data, ModelMetadata metadata, int wavelength) {
		Vector2d surface = this.getSegment(data.surfaceId);
		List<Vector2d> scattered = new ArrayList<>();
		double cross = surface.crossSign(data.ray);
		//convert the line to a normal:
		Vector2d normal = surface.rotate(cross*Math.PI / 2).normalize();
		double angleIn = data.ray.angleTo(normal);
		double angleOut = getAngle(angleIn, metadata, wavelength, cross > 0);
		normal.rotate(angleOut);
		scattered.add(normal);
		return scattered;
	}
	
	private double getAngle(double angleIn, ModelMetadata metadata, double wavelength, boolean into) {
		double angleOut;
		
		double currRefrac = getRefraction(metadata, wavelength)/metadata.getAmbient(wavelength);
		
		double invrefrac = 1/currRefrac;
		
		if (into && Math.abs(invrefrac * Math.sin(angleIn)) <= 1) {
			// Från luft till lens:
			angleOut = Math.asin(invrefrac * Math.sin(angleIn));
		} else if (!into && Math.abs(currRefrac*Math.sin(angleIn)) <= 1) {
			// Från lens till luft:
			angleOut = Math.asin(currRefrac * Math.sin(angleIn));
		} else {
			// Totalreflektion:
			angleOut = Math.PI - angleIn;
		}
		return angleOut;
	}
	
	protected double getRefraction(ModelMetadata metadata, double wavelength) {
		return metadata.getLensMaterial((int)get("Material Index"))
		.refractionSellmeier(wavelength);
	}
	
	@Override
	public void createBounds() {
		points.add(points.get(0).copy()); //Close loop
		super.createBounds();
	}
	
	@Override
	public void draw(GraphicsContext gc, ModelMetadata metadata, boolean selected) {
		
		Stop[] stops;
		LensMaterial lm = metadata.getLensMaterial((int)get("Material Index"));
		
		if(selected) {
			stops = new Stop[] { new Stop(0, lm.color(0.45)), new Stop(1, lm.color(0.8))};	        
		} else {
			stops = new Stop[] { new Stop(0, lm.color(0.25)), new Stop(1, lm.color(0.6))};
		}
		LinearGradient fillGradient = new LinearGradient(0, 0.5, 1, 0, true, CycleMethod.NO_CYCLE, stops);
		gc.setFill(fillGradient);
		
		gc.beginPath();
		for (int i = 0; i < getPointCount(); i++) {
			Vector2d p = getPoint(i);
			gc.lineTo(p.x, p.y);
		}
		gc.fill();
		
		super.draw(gc, metadata, selected);
	}
}
