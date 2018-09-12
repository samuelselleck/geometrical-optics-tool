package optics_object_generators;

import optics_objects.OpticsObject;
import optics_objects.Prism;
import util.Vector2d;

public class PrismCreator extends ObjectCreator {

	public PrismCreator() {
		double[] values = new double[] { 3, 80, 1.5 };
		Vector2d[] bounds = new Vector2d[values.length];
		String[] names = new String[] { "Edges", "Radius", "Refractionindex" };
		bounds[0] = new Vector2d(3, 8);
		bounds[1] = new Vector2d(11, 300);
		bounds[2] = new Vector2d(1, 10);
		super.setSliders(names, bounds, values);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new Prism(origin, (int) Math.round(super.getSliderValue(0)), super.getSliderValue(1),
				super.getSliderValue(2));
	}
}
