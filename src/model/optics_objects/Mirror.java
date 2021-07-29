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

public abstract class Mirror extends Material {
	
	private static final long serialVersionUID = 1L;
	
	public Mirror(Map<String, DoubleProperty> properties) {
		super(properties);
	}
	
	@Override
	public List<Vector2d> getScatteredLight(RayIntersectionData data, ModelMetadata metadata, int wavelength) {
		Vector2d surface = this.getSegment(data.surfaceId);
		double angle = data.ray.angleTo(surface);
		Vector2d newRay = data.ray.copy().rotate(-2*angle);
		
		List<Vector2d> scattered = new ArrayList<>();
		scattered.add(newRay);
		return scattered;
	}
	
	@Override
	public void draw(GraphicsContext gc, ModelMetadata metadata, boolean selected) {
		
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
		
		super.draw(gc, metadata, selected);
	}
}
