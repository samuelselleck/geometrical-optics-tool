package optics_object_factories;

import optics_objects.lights.BeamLightSource;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class BeamLightSourceFactory extends OpticsObjectFactory {

	public BeamLightSourceFactory() {
		addSlider("Diameter", 23, 400, 80);
		addSlider("LightRays", 1, 100, 20);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new BeamLightSource(origin, getParam("Diameter"),
				getIntParam("LightRays"), fixedPos());
	}

}
