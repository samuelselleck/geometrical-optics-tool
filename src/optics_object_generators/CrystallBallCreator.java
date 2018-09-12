package optics_object_generators;

import optics_objects.Lens;
import optics_objects.OpticsObject;
import optics_objects.Prism;
import util.Vector2d;

public class CrystallBallCreator extends ObjectCreator {
	
	public CrystallBallCreator() {
		String[] names = new String[] {"Radius", "Refractionindex" };
		double[] values = new double[] { 80, 1.5 };
		Vector2d[] bounds = new Vector2d[values.length];
		bounds[0] = new Vector2d(11, 500);
		bounds[1] = new Vector2d(1, 10);
		super.setSliders(names, bounds, values);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new Prism(origin, Lens.LENSRESOLUTION, super.getSliderValue(0),
				super.getSliderValue(1));
	}
}
