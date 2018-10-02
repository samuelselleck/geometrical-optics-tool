package optics_object_factories;

import optics_objects.OpticsObject;
import optics_objects.RectangleLens;
import util.Vector2d;

public class RectangleLensFactory extends OpticsObjectFactory {

	public RectangleLensFactory() {
		String[] names = new String[] { "Width", "Height", "Refractionindex"};
		double[] values = new double[] {500, 100, 1.5};
		Vector2d[] bounds = new Vector2d[values.length];
		bounds[0] = new Vector2d(7, 800);
		bounds[1] = new Vector2d(7, 800);
		bounds[2] = new Vector2d(1, 10);
		super.setSliders(names, bounds, values);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new RectangleLens(origin, super.getSliderValue(2), super.getSliderValue(0),
				super.getSliderValue(1));
	}

}
