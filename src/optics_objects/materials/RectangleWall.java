package optics_objects.materials;

import optics_objects.templates.Wall;
import util.Vector2d;

public class RectangleWall extends Wall {
	private static final long serialVersionUID = 1L;

	public RectangleWall(Vector2d origin, double width, double height) {
		super(origin);
		points.add(new Vector2d(-width/2,-height/2));
		points.add(new Vector2d(-width/2, height/2));
		points.add(new Vector2d(width/2, height/2));
		points.add(new Vector2d(width/2, -height/2));
		super.createBounds();
	}
}
