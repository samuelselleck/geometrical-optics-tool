package optics_object_generators;

import optics_objects.BeamLightSource;
import optics_objects.OpticsObject;
import util.Vector2d;

public class BeamLightSourceCreator extends ObjectCreator {

	public BeamLightSourceCreator() {
		String[] names = new String[] { "Diameter", "Lightrays"};
		double[] values = new double[] { 80, 20};
		Vector2d[] bounds = new Vector2d[values.length];
		bounds[0] = new Vector2d(23, 400);
		bounds[1] = new Vector2d(1, 300);
		super.setSliders(names, bounds, values);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new BeamLightSource(origin, super.getSliderValue(0),
				(int) Math.round(super.getSliderValue(1)));
	}

}
