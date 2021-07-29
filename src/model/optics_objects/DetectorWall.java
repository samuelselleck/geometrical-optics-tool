package model.optics_objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import gui.Main;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.text.TextAlignment;
import model.ModelMetadata;
import model.RayIntersectionData;
import util.Vector2d;

public class DetectorWall extends Material {
	private static final long serialVersionUID = 1L;
	
	private transient List<Vector2d> detectorPoints = new ArrayList<>();
	private transient String label = "(test)";
	
	public DetectorWall(Map<String, DoubleProperty> properties) {
		super(properties);
		update();
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
	public void onHit(Vector2d position, int surfaceId) {
		if(surfaceId == 0) {
			Vector2d surface = this.getSegment(0);
			Vector2d offset = surface.copy().normalize()
					.rotateDegrees(-90)
					.mult(get("Width")*Main.DPCM/2);
			
			detectorPoints.add(offset.add(position));
		}
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
	protected void update()  {
		super.clear();
		double w = get("Width")*Main.DPCM;
		double h = get("Height")*Main.DPCM;
		
		points.add(new Vector2d(0, -h));
		points.add(new Vector2d(0, 0));
		points.add(new Vector2d(w, 0));
		points.add(new Vector2d(w, -h));
		super.init();
	}
	
	@Override
	public void draw(GraphicsContext gc, ModelMetadata metadata, boolean selected) {
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
		
		Vector2d p1 = getOrigin().add(points.get(0));
		Vector2d p2 = getOrigin().add(points.get(1));
		Vector2d p3 = getOrigin().add(points.get(2));
		gc.setLineWidth(3);
		gc.setStroke(Paint.valueOf("gray"));
		gc.strokeLine(p1.x, p1.y, p2.x, p2.y);
		gc.strokeLine(p2.x, p2.y, p3.x, p3.y);
		
		
		gc.setFill(Color.rgb(255, 0, 0, 0.2));
		for(Vector2d p : detectorPoints) {
			gc.fillOval(p.x - 3, p.y - 3, 6, 6);
		}
		
		Vector2d zero = getOrigin();
		OptionalDouble average = detectorPoints.stream()
				.mapToDouble(p -> {
					return p.dist(zero)/Main.DPCM;
					}).average();
		
		label = average.isPresent() ? String.format("%.2f", average.getAsDouble()) : "";
		
		
		Vector2d labelPos = new Vector2d(0, 20).rotateDegrees(get("Rotation")).add(getOrigin());
		gc.setFill(Paint.valueOf("white"));
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.fillText(label, labelPos.x, labelPos.y);
		
		
		detectorPoints.clear();
		
		super.draw(gc, metadata, selected);
	}
}
