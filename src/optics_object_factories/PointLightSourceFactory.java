package optics_object_factories;

import optics_objects.materials.OpticsObject;
import optics_objects.materials.PointLightSource;
import util.Vector2d;

public class PointLightSourceFactory extends OpticsObjectFactory {

	public PointLightSourceFactory() {
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
