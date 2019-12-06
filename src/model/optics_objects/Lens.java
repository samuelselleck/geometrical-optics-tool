package model.optics_objects;

import java.awt.Graphics2D;
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
import model.LightPathNode.RayIntensityTuple;
import util.Vector2d;

public abstract class Lens extends Apparatus {
	private static final long serialVersionUID = 1L;
	
	public static ObservableList<LensMaterial> MATERIALS = FXCollections.observableArrayList();
	
	static {
		
		MATERIALS.add(new LensMaterial("Glass",
				new double[] {0.8, 0.8, 0.9},
				1.03961212, 0.231792344, 1.01046945, 6.00069867e-3, 2.00179144e-2, 103.560653));
		MATERIALS.add(new LensMaterial("Saphire",
				new double[] {1, 0.2, 0.5},
				1.43134930, 0.65054713, 5.3414021, 5.2799261e-3, 1.42382647e-2, 325.017834));
	}
	
	LinearGradient fillGradient, fillGradientSelected;
	
	public Lens(Map<String, DoubleProperty> properties) {
		super(properties);
	}
	
	@Override
	public List<RayIntensityTuple> getScatteredLight(Vector2d ray, Vector2d surface, double intensity, int wavelength) {
		List<RayIntensityTuple> scattered = new ArrayList<>();
		double cross = surface.crossSign(ray);
		//convert the line to a normal:
		Vector2d normal = surface.copy().rotate(cross*Math.PI / 2).normalize();
		double angleIn = ray.angleTo(normal);
		double angleOut = getAngle(angleIn, wavelength, cross > 0);
		normal.rotate(angleOut);
		RayIntensityTuple scatteredRay = new RayIntensityTuple(normal, intensity*0.9);
		scattered.add(scatteredRay);
		return scattered;
	}
	
	private double getAngle(double angleIn, double wavelength, boolean into) {
		double angleOut;
		
		double currRefrac = getLensMaterial()
				.refraction(wavelength, get("Refraction Multiplier"));
		
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
	
	@Override
	public void init() {
		super.init();
		LensMaterial lm = getLensMaterial();

	    Stop[] stopsSelected = new Stop[] { new Stop(0, lm.color(0.45)), new Stop(1, lm.color(0.8))};
	    fillGradientSelected = new LinearGradient(0, 0.5, 1, 0, true, CycleMethod.NO_CYCLE, stopsSelected);
	    Stop[] stops = new Stop[] { new Stop(0, lm.color(0.25)), new Stop(1, lm.color(0.6))};
	    fillGradient = new LinearGradient(0, 0.5, 1, 0, true, CycleMethod.NO_CYCLE, stops);
	}
	@Override
	public void createBounds() {
		points.add(points.get(0).copy()); //Close loop
		super.createBounds();
	}
	
	@Override
	public void draw(GraphicsContext gc, boolean selected) {
		
		gc.setFill(selected ? fillGradientSelected : fillGradient);
		gc.beginPath();
		for (int i = 0; i < getPointCount(); i++) {
			Vector2d p = getPoint(i);
			gc.lineTo(p.x, p.y);
		}
		gc.fill();
	}
	
	@Override
	public void draw(Graphics2D g, boolean selected) {
		
	}
	
	private LensMaterial getLensMaterial() {
		int index = (int)get("Material Index");
		if(index < MATERIALS.size()) {
			return MATERIALS.get(index);
		} else {
			return MATERIALS.get(0);
		}
	}
}
