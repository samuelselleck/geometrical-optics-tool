package model.optics_objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
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
	public void draw(Graphics2D g, boolean selected) {
		g.setColor(Color.LIGHT_GRAY);
		Vector2d p2 = getPoint(0);
		for(int i = 1; i < getPointCount(); i++) {
			Vector2d p1 = p2;
			p2 = getPoint(i);
			g.drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
		}
	}
}
