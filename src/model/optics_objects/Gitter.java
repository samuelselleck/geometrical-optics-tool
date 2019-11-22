package model.optics_objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import util.Vector2d;

public abstract class Gitter extends Material {
	private static final long serialVersionUID = 1L;

	public Gitter(Vector2d origin, Map<String, DoubleProperty> properties) {
		super(origin, properties);
	}

	@Override
	public List<Vector2d> getScatteredLight(Vector2d ray, Vector2d surface, int wavelength) {
		
		double sign = ray.crossSign(surface);
		Vector2d normalOut = surface.copy().normalize().rotate(-sign*Math.PI/2);
		List<Vector2d> scattered = new ArrayList<>();
		double step = Math.PI/10;
		for(int i = -3; i <= 3; i++) {
			scattered.add(normalOut.copy().rotate(i*step));
		}
		return scattered;
	}
	
	@Override
	public void draw(GraphicsContext gc, boolean selected) {
		
		gc.setStroke(new Color(0.5, 0.5, 0.7, 1));
		if(selected) {
			gc.setLineWidth(5);
		} else {
			gc.setLineWidth(4);
		}
		gc.beginPath();
		for (int i = 0; i < getPointCount(); i++) {
			Vector2d p = getPoint(i);
			gc.lineTo(p.x, p.y);
		}
		gc.stroke();
	}

}
