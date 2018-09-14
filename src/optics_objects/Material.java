package optics_objects;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import util.Vector2d;

public abstract class Material extends OpticsObject {
	private Vector2d botRig, topLef;
	protected ArrayList<Vector2d> points;

	public Material() {
		points = new ArrayList<>();
	}

	public void createBounds() {
		botRig = Vector2d.zero();
		topLef = Vector2d.zero();
		for (int i = 0; i < points.size(); i++) {
			Vector2d p1 = points.get(i);
			if (p1.x < topLef.x)
				topLef.x = p1.x;
			if (p1.y < topLef.y)
				topLef.y = p1.y;
			if (p1.x > botRig.x)
				botRig.x = p1.x;
			if (p1.y > botRig.y)
				botRig.y = p1.y;
		}
		// Safety margins:
		topLef.add(new Vector2d(-1, -1));
		botRig.add(new Vector2d(1, 1));
	}

	public abstract double getAngle(double angleIn, double wavelength, boolean dir);

	public void draw(GraphicsContext gc) {
		
	}

	public Vector2d getPoint(int index) {
		return origin.copy().add(points.get(index % points.size()));
	}
	
	public Vector2d getSegment(int index) {
		return points.get((index + 1) % points.size()).copy().sub(points.get(index));
	}
	
	public void rotate(double angle) {
		for(Vector2d p : points) {
			p.rotate(angle);
		}
		this.createBounds();
	}

	public Vector2d getBottomRightBound() {
		return origin.copy().add(botRig);
	}

	public Vector2d getTopLeftBound() {
		return origin.copy().add(topLef);
	}

	public int getPointCount() {
		return points.size();
	}
}
