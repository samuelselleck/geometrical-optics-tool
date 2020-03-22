package model.optics_objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import gui.Main;
import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.text.TextAlignment;
import util.Vector2d;

public class DetectorWall extends RectangleWall {
	private static final long serialVersionUID = 1L;
	
	private transient List<Vector2d> detectorPoints = new ArrayList<>();
	private transient String label = "(test)";
	
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
		if(selected) {
			Stop[] stops = new Stop[] { new Stop(0, new Color(0.1, 0.1, 0.1, 1)), new Stop(1, new Color(0.2, 0.2, 0.2, 1))};
	        LinearGradient fillGradient = new LinearGradient(0, 0.5, 1, 0, true, CycleMethod.NO_CYCLE, stops);
			gc.setFill(fillGradient);
		} else {
			Stop[] stops = new Stop[] { new Stop(0, new Color(0.05, 0.05, 0.05, 1)), new Stop(1, new Color(0.15, 0.15, 0.15, 1))};
	        LinearGradient fillGradient = new LinearGradient(0, 0.5, 1, 0, true, CycleMethod.NO_CYCLE, stops);
			gc.setFill(fillGradient);
		}
		gc.beginPath();
		for (int i = 0; i < getPointCount(); i++) {
			Vector2d p = getPoint(i);
			gc.lineTo(p.x, p.y);
		}
		gc.fill();
		
		gc.setFill(Color.rgb(255, 0, 0, 0.2));
		for(Vector2d p : detectorPoints) {
			gc.fillOval(p.x - 3, p.y - 3, 6, 6);
		}
		
		Vector2d zero = getOrigin().add(Vector2d.midPoint(points.get(2), points.get(3)));
		OptionalDouble average = detectorPoints.stream()
				.mapToDouble(p -> {
					return p.dist(zero)/Main.DPCM;
					}).average();
		
		label = average.isPresent() ? String.format("(%.2f)", average.getAsDouble()) : "";
		
		
		Vector2d labelPos = new Vector2d(get("Width")*Main.DPCM + 20, 0).rotateDegrees(get("Rotation")).add(getOrigin());
		gc.setFill(Paint.valueOf("white"));
		gc.fillText(label, labelPos.x, labelPos.y);
		Vector2d p1 = getOrigin().add(points.get(2));
		Vector2d p2 = getOrigin().add(points.get(3));
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setStroke(Paint.valueOf("gray"));
		gc.strokeLine(p1.x, p1.y, p2.x, p2.y);
		
		detectorPoints.clear();
	}
}
