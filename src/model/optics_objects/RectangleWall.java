package model.optics_objects;

import util.Vector2d;

public class RectangleWall extends Wall {
	private static final long serialVersionUID = 1L;
	
	public RectangleWall(Vector2d origin, double width, double height) {
		super(origin);
		super.addProperty("Width", width);
		super.addProperty("Height", height);
		update();
	}
	
	@Override
	protected void update()  {
		super.clear();
		points.add(new Vector2d(-get("Width")/2,-get("Height")/2));
		points.add(new Vector2d(-get("Width")/2, get("Height")/2));
		points.add(new Vector2d(get("Width")/2, get("Height")/2));
		points.add(new Vector2d(get("Width")/2, -get("Height")/2));
		super.initObject();
	}
}