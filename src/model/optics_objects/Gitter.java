package model.optics_objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.ModelMetadata;
import model.RayIntersectionData;
import util.Vector2d;

public abstract class Gitter extends Material {
	private static final long serialVersionUID = 1L;

	public Gitter(Map<String, DoubleProperty> properties) {
		super(properties);
		update();
	}

	@Override
	public List<Vector2d> getScatteredLight(RayIntersectionData data, ModelMetadata metadata, int wavelength) {
		Vector2d surface = this.getSegment(data.surfaceId);
		double d = get("Grating Constant");
		double sign = data.ray.crossSign(surface);
		Vector2d normalOut = surface.copy().normalize().rotate(-sign*Math.PI/2);
		double angleIn = data.ray.angleTo(normalOut);
		List<Vector2d> scattered = new ArrayList<>();
		for(int n = -100; n < 100; n++) {
			double term = Math.sin(angleIn) - n*wavelength/d;
			if(term > -1 && term < 1) {
				double angleOut = Math.asin(term);
				scattered.add(normalOut.copy().rotate(angleOut));
			}
		}
		
		return scattered;
	}
	
	@Override
	public void draw(GraphicsContext gc, ModelMetadata metadata, boolean selected) {
		
		gc.setStroke(new Color(0.5, 0.5, 0.7, 1));
		gc.setLineWidth(selected ?  4 : 3);
		gc.beginPath();
		for (int i = 0; i < getPointCount(); i++) {
			Vector2d p = getPoint(i);
			gc.lineTo(p.x, p.y);
		}
		gc.stroke();
		
		super.draw(gc, metadata, selected);
	}

}
