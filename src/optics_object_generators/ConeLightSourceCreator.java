package optics_object_generators;

import optics_objects.ConeLightSource;
import optics_objects.OpticsObject;
import util.Vector2d;

public class ConeLightSourceCreator extends ObjectCreator{

	public ConeLightSourceCreator() {
		String names[] = new String[]{ "Cone Angle", "LightRays"};
		double values[] = new double[] {30, 20};
		Vector2d[] bounds = new Vector2d[values.length];
		bounds[0] = new Vector2d(11, 180);
		bounds[1] = new Vector2d(11, 300);
		super.setSliders(names, bounds, values);
	}
	
	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new ConeLightSource(origin, super.getSliderValue(0)/180*Math.PI,
				(int) Math.round(super.getSliderValue(1)));
	}
}
