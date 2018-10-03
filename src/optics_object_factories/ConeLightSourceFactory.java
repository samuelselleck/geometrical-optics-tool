package optics_object_factories;

import optics_objects.materials.ConeLightSource;
import optics_objects.materials.OpticsObject;
import util.Vector2d;

public class ConeLightSourceFactory extends OpticsObjectFactory{

	public ConeLightSourceFactory() {
		String names[] = new String[]{ "Cone Angle", "LightRays"};
		double values[] = new double[] {30, 20};
		Vector2d[] bounds = new Vector2d[values.length];
		bounds[0] = new Vector2d(11, 180);
		bounds[1] = new Vector2d(11, 100);
		super.setSliders(names, bounds, values);
	}
	
	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new ConeLightSource(origin, super.getSliderValue(0)/180*Math.PI,
				(int) Math.round(super.getSliderValue(1)));
	}
}
