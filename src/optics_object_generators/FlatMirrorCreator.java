package optics_object_generators;

import optics_objects.FlatMirror;
import optics_objects.OpticsObject;
import util.Vector2d;

public class FlatMirrorCreator extends ObjectCreator {

	public FlatMirrorCreator() {
		String[] names = new String[] { "Diameter" };
		double[] values = new double[] { 200 };
		Vector2d[] bounds = new Vector2d[values.length];
		bounds[0] = new Vector2d(103, 800);
		
		super.setSliders(names, bounds, values);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new FlatMirror(origin, super.getSliderValue(0));
	}

}
