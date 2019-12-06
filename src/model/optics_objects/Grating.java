package model.optics_objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import util.Vector2d;

public abstract class Grating extends Apparatus {
	private static final long serialVersionUID = 1L;

	public Grating(Map<String, DoubleProperty> properties) {
		super(properties);
		update();
	}

	@Override
	public List<Vector2d> getScatteredLight(Vector2d ray, Vector2d surface, int wavelength) {
		
		double d = get("Grating Constant");
		double sign = ray.crossSign(surface);
		Vector2d normalOut = surface.copy().normalize().rotate(-sign*Math.PI/2);
		double angleIn = Math.abs(ray.angleTo(normalOut));
		List<Vector2d> scattered = new ArrayList<>();
		int n = 1;
		while(n*wavelength/d < 1) {
			double angleOut = Math.asin(n*wavelength/d - Math.sin(angleIn));
			scattered.add(normalOut.copy().rotate(angleOut));
			scattered.add(normalOut.copy().rotate(-angleOut));
		    n++;
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
