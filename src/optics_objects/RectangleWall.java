package optics_objects;

import util.Vector2d;

public class RectangleWall extends Wall {

	public RectangleWall(Vector2d origin, double width, double height) {
		this.origin = origin;
		points.add(new Vector2d(-width/2,-height/2));
		points.add(new Vector2d(-width/2, height/2));
		points.add(new Vector2d(width/2, height/2));
		points.add(new Vector2d(width/2, -height/2));
		super.createBounds();
	}
}
