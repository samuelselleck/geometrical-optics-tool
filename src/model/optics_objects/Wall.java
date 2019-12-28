package model.optics_objects;

import java.awt.Graphics2D;
import java.util.List;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import model.LightPathNode.RayIntensityTuple;
import util.Vector2d;

public abstract class Wall extends Apparatus {

	public Wall(Map<String, DoubleProperty> properties) {
		super(properties);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public List<RayIntensityTuple> getScatteredLight(Vector2d ray, Vector2d surface, double intensity, int wavelength) {
		return null;
	}
	
	@Override
	public void createBounds() {
		points.add(points.get(0).copy()); //Close loop
		super.createBounds();
	}
	
	@Override
	public void draw(Graphics2D g, boolean selected) {
		
	}
}
