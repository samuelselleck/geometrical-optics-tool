package optics_object_factories;

import optics_objects.materials.OpticsObject;
import optics_objects.materials.RoundedMirror;
import util.Vector2d;

public class RoundedMirrorFactory extends OpticsObjectFactory {
	
	public RoundedMirrorFactory() {
		String[] names = new String[] { "Diameter", "Depth"};
		double[] values = new double[] { 200, 30 };
		Vector2d[] bounds = new Vector2d[values.length];
		bounds[0] = new Vector2d(103, 800);
		bounds[1] = new Vector2d(11, 500);
		
		super.setSliders(names, bounds, values);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new RoundedMirror(origin, super.getSliderValue(0),
				super.getSliderValue(1));
	}
}
