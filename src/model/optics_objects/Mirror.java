package model.optics_objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.LightPathNode.RayIntensityTuple;
import util.Vector2d;

public abstract class Mirror extends Apparatus {
	
	private static final long serialVersionUID = 1L;
	
	public Mirror(Map<String, DoubleProperty> properties) {
		super(properties);
	}
	
	@Override
	public List<RayIntensityTuple> getScatteredLight(Vector2d ray, Vector2d surface, double intensity, int wavelength) {
		
		double angle = ray.angleTo(surface);
		Vector2d newRay = ray.copy().rotate(-2*angle);
		
		List<RayIntensityTuple> scattered = new ArrayList<>();
		scattered.add(new RayIntensityTuple(newRay, intensity));
		return scattered;
	}
	
	@Override
	public void draw(GraphicsContext gc, boolean selected) {
		
		gc.setStroke(new Color(0.7, 0.7, 0.7, 1));
		if(selected) {
			gc.setLineWidth(3);
		} else {
			gc.setLineWidth(2);
		}
		gc.beginPath();
		for (int i = 0; i < getPointCount(); i++) {
			Vector2d p = getPoint(i);
			gc.lineTo(p.x, p.y);
		}
		gc.stroke();
	}
}
