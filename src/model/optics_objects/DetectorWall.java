package model.optics_objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gui.Main;
import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import util.Vector2d;

public class DetectorWall extends RectangleWall {
	private static final long serialVersionUID = 1L;
	
	private transient List<Vector2d> detectorPoints = new ArrayList<>();
	
	public DetectorWall(Map<String, DoubleProperty> properties) {
		super(properties);
	}
	
	@Override
	public void onHit(Vector2d position, Vector2d surface) {
		Vector2d offset = surface.copy().normalize()
				.rotateDegrees(90)
				.mult(get("Width")*Main.DPCM/2);
		
		detectorPoints.add(offset.add(position));
	}
	
	@Override
	protected void clear() {
		if(detectorPoints == null) {
			detectorPoints = new ArrayList<>();
		} else {
			detectorPoints.clear();
		}
	}
	
	@Override
	public void draw(GraphicsContext gc, boolean selected) {
		super.draw(gc, selected);
		
		gc.setFill(Color.rgb(255, 0, 0, 0.2));
		for(Vector2d p : detectorPoints) {
			gc.fillOval(p.x - 3, p.y - 3, 6, 6);
		}
		detectorPoints.clear();
	}
}
