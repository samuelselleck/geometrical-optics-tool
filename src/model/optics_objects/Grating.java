package model.optics_objects;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.LightPathNode.RayIntensityTuple;
import util.Vector2d;

public abstract class Grating extends Apparatus {
	private static final long serialVersionUID = 1L;

	public Grating(Map<String, DoubleProperty> properties) {
		super(properties);
		update();
	}

	@Override
	public List<RayIntensityTuple> getScatteredLight(Vector2d ray, Vector2d surface, double intensity, int wavelength) {
		
		double d = get("Grating Constant");
		double sign = ray.crossSign(surface);
		Vector2d normalOut = surface.copy().normalize().rotate(-sign*Math.PI/2);
		double angleIn = Math.abs(ray.angleTo(normalOut));
		List<RayIntensityTuple> scattered = new ArrayList<>();
		int n = 1;
		while(n*wavelength/d < 1) {
			double angleOut = Math.asin(n*wavelength/d - Math.sin(angleIn));
			
			RayIntensityTuple scatteredRay = new RayIntensityTuple(normalOut.copy().rotate(angleOut), intensity);
			scattered.add(scatteredRay);
			scatteredRay = new RayIntensityTuple(normalOut.copy().rotate(-angleOut), intensity);
			scattered.add(scatteredRay);
		    n++;
		}
		
		return scattered;
	}
	
	@Override
	public void draw(Graphics2D g, boolean selected) {
		
	}

}
