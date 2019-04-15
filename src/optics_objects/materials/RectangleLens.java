package optics_objects.materials;

import optics_objects.templates.Lens;
import util.Vector2d;

public class RectangleLens extends Lens {
	private static final long serialVersionUID = 1L;

	public RectangleLens(Vector2d origin, double refractionindex, double width, double height, boolean fixedPosition) {
		super(origin, refractionindex, fixedPosition);

		setPoints(width, height);
	}
	
	public void setPoints(double width, double height) {
		clearPoints();
		points.add(new Vector2d(width/2, -height/2));
		points.add(new Vector2d(width/2, height/2));
		points.add(new Vector2d(-width/2, height/2));
		points.add(new Vector2d(-width/2,-height/2));
		
		super.createBounds();
	}

}
