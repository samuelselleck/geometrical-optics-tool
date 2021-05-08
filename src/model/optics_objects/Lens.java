package model.optics_objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import model.LensMaterial;
import model.RayIntersectionData;
import util.Vector2d;

public abstract class Lens extends Material {
	private static final long serialVersionUID = 1L;
	public static ObservableList<LensMaterial> MATERIALS = FXCollections.observableArrayList();
	
	static {
		
		MATERIALS.add(new LensMaterial("Glass",
				new double[] {0.8, 0.8, 0.9},
				1.03961212, 0.231792344, 1.01046945, 6.00069867e-3, 2.00179144e-2, 103.560653));
		MATERIALS.add(new LensMaterial("Saphire",
				new double[] {1, 0.2, 0.5},
				1.43134930, 0.65054713, 5.3414021, 5.2799261e-3, 1.42382647e-2, 325.017834));
		MATERIALS.add(new LensMaterial("Constant (n = 1.5)",
				new double[] {1, 1, 1},
				1.5*1.5 - 1, 0, 0, 0, 0, 0));
	}
	
	public Lens(Map<String, DoubleProperty> properties) {
		super(properties);
	}
	
	@Override
	public List<Vector2d> getScatteredLight(RayIntersectionData data, int wavelength) {
		Vector2d surface = this.getSegment(data.surfaceId);
		List<Vector2d> scattered = new ArrayList<>();
		double cross = surface.crossSign(data.ray);
		//convert the line to a normal:
		Vector2d normal = surface.rotate(cross*Math.PI / 2).normalize();
		double angleIn = data.ray.angleTo(normal);
		double angleOut = getAngle(angleIn, wavelength, cross > 0);
		normal.rotate(angleOut);
		scattered.add(normal);
		return scattered;
	}
	
	private double getAngle(double angleIn, double wavelength, boolean into) {
		double angleOut;
		
		double currRefrac = getRefraction(wavelength);
		
		double invrefrac = 1/currRefrac;
		if (into) {
			// Från luft till lens:
			angleOut = Math.asin(invrefrac * Math.sin(angleIn));
		} else if (Math.abs(angleIn) <= Math.asin(invrefrac)) {
			// Från lens till luft:
			angleOut = Math.asin(currRefrac * Math.sin(angleIn));
		} else {
			// Totalreflektion:
			angleOut = Math.PI - angleIn;
		}
		return angleOut;
	}
	
	protected double getRefraction(double wavelength) {
		return MATERIALS.get((int)get("Material Index"))
		.refractionSellmeier(wavelength);
	}
	
	@Override
	public void createBounds() {
		points.add(points.get(0).copy()); //Close loop
		super.createBounds();
	}
	
	@Override
	public void draw(GraphicsContext gc, boolean selected) {
		
		Stop[] stops;
		LensMaterial lm = MATERIALS.get((int)get("Material Index"));
		
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
		
		super.draw(gc, selected);
	}
}
