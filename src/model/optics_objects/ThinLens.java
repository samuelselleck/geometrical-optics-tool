package model.optics_objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gui.Main;
import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.RayIntersectionData;
import util.Vector2d;

public class ThinLens extends Material {
	private static final long serialVersionUID = 1L;

	public ThinLens(Map<String, DoubleProperty> properties) {
		super(properties);
		update();
	}
	
	@Override
	public List<Vector2d> getScatteredLight(RayIntersectionData data, int wavelength) {
		Vector2d surface = this.getSegment(data.surfaceId);
		List<Vector2d> scattered = new ArrayList<>();
		double cross = surface.crossSign(data.ray);
		//convert the line to a normal:
		Vector2d normal = surface.rotate(cross*Math.PI / 2).normalize();
		double angleIn = data.ray.angleTo(normal);
		Vector2d orig = this.getOrigin();
		Vector2d distVec = orig.sub(data.position);
		double dist = distVec.length()*distVec.crossSign(data.ray);
		double angleOut = getAngle(angleIn, dist);
		normal.rotate(angleOut);
		scattered.add(normal);
		return scattered;
	}
	
	private double getAngle(double angleIn, double y) {
		double f = this.get("Focal Length")*Main.DPCM;
		double out = Math.atan(Math.tan(angleIn) - y/f);
		return out;
	}
	
	@Override
	protected void update() {
		super.clear();
		Vector2d unit = new Vector2d(0, 1).mult(get("Diameter")*Main.DPCM / 2);
		points.add(unit);
		points.add(unit.copy().mult(-1));
		super.init();
	}
	
	@Override
	public void draw(GraphicsContext gc, boolean selected) {
		
		gc.setStroke(new Color(0.7, 0.7, 0.7, 1));
		if(selected) {
			gc.setLineWidth(6);
		} else {
			gc.setLineWidth(4);
		}
		gc.beginPath();
		for (int i = 0; i < getPointCount(); i++) {
			Vector2d p = getPoint(i);
			gc.lineTo(p.x, p.y);
		}
		gc.stroke();
		
		super.draw(gc, selected);
	}
}
