package optics_objects.materials;

import util.Vector2d;

public class RectangleLens extends Lens {
	private static final long serialVersionUID = 1L;

	public RectangleLens(Vector2d origin, double refractionindex, double width, double height) {
		super(origin, refractionindex);
		points.add(new Vector2d(width/2, -height/2));
		points.add(new Vector2d(width/2, height/2));
		points.add(new Vector2d(-width/2, height/2));
		points.add(new Vector2d(-width/2,-height/2));
		
		
		
		super.createBounds();
	}

}
