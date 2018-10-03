package optics_object_factories;

import optics_objects.materials.Lens;
import optics_objects.materials.OpticsObject;
import optics_objects.materials.Prism;
import util.Vector2d;

public class CrystallBallFactory extends OpticsObjectFactory {
	
	public CrystallBallFactory() {
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
