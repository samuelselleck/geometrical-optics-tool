package optics_object_factories;

import optics_objects.materials.FlatMirror;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class FlatMirrorFactory extends OpticsObjectFactory {

	public FlatMirrorFactory() {
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
