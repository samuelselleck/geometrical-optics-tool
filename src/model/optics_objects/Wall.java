package model.optics_objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import model.ModelMetadata;
import model.RayIntersectionData;
import util.Vector2d;

public abstract class Wall extends Material {
	private static final long serialVersionUID = 1L;

	public Wall(Map<String, DoubleProperty> properties) {
		super(properties);
	}
	
	@Override
	public List<Vector2d> getScatteredLight(RayIntersectionData data, ModelMetadata metadata, int wavelength) {
		return new ArrayList<>();
	}
	
	@Override
	public void createBounds() {
		points.add(points.get(0).copy()); //Close loop
		super.createBounds();
	}
	
	@Override
	public void draw(GraphicsContext gc, ModelMetadata metadata, boolean selected) {
		if(selected) {
			Stop[] stops = new Stop[] { new Stop(0, new Color(0.3, 0.3, 0.3, 1)), new Stop(1, new Color(0.6, 0.6, 0.6, 1))};
	        LinearGradient fillGradient = new LinearGradient(0, 0.5, 1, 0, true, CycleMethod.NO_CYCLE, stops);
			gc.setFill(fillGradient);
		} else {
			Stop[] stops = new Stop[] { new Stop(0, new Color(0.1, 0.1, 0.1, 1)), new Stop(1, new Color(0.3, 0.3, 0.3, 1))};
	        LinearGradient fillGradient = new LinearGradient(0, 0.5, 1, 0, true, CycleMethod.NO_CYCLE, stops);
			gc.setFill(fillGradient);
		}
		gc.beginPath();
		for (int i = 0; i < getPointCount(); i++) {
			Vector2d p = getPoint(i);
			gc.lineTo(p.x, p.y);
		}
		gc.fill();
		
		super.draw(gc, metadata, selected);
	}
}
