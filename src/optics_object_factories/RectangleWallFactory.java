package optics_object_factories;

import optics_objects.materials.OpticsObject;
import optics_objects.materials.RectangleWall;
import util.Vector2d;

public class RectangleWallFactory extends OpticsObjectFactory {
	
	public RectangleWallFactory() {
		String[] names = new String[] { "Width", "Height" };
		double[] values = new double[] { 30, 200};
		Vector2d[] bounds = new Vector2d[values.length];
		bounds[0] = new Vector2d(7, 800);
		bounds[1] = new Vector2d(7, 800);

		super.setSliders(names, bounds, values);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new RectangleWall(origin,
				super.getSliderValue(0), super.getSliderValue(1));
	}

}
