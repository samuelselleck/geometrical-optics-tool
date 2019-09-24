package model.optics_objects;

import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import util.Vector2d;

public abstract class Mirror extends Material {
	
	private static final long serialVersionUID = 1L;
	
	public Mirror(Vector2d origin, Map<String, DoubleProperty> properties) {
		super(origin, properties);
	}
	
	@Override
	public double getAngle(double angleIn, double wavelength, boolean dir) {
		return Math.PI - angleIn;
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
