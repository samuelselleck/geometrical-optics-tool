package optics_object_generators;

import optics_objects.OpticsObject;
import optics_objects.PointLightSource;
import util.Vector2d;

public class PointLightSourceCreator extends ObjectCreator {

	public PointLightSourceCreator() {
		String[] names = new String[] { "LightRays"};
		double[] values = new double[] { 30};
		Vector2d[] bounds = new Vector2d[values.length];
		bounds[0] = new Vector2d(11, 300);

		super.setSliders(names, bounds, values);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new PointLightSource(origin, (int) Math.round(super.getSliderValue(0)));
	}
}
