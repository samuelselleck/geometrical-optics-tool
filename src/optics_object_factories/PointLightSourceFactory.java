package optics_object_factories;

import optics_objects.lights.PointLightSource;
import optics_objects.templates.LightSource;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class PointLightSourceFactory extends OpticsObjectFactory {

	public PointLightSourceFactory() {
		addSlider("LightRays", 11, 300, 30);
		addSlider("WaveLength", 380, 700, LightSource.lightWaveDefault());
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new PointLightSource(origin, getIntParam("LightRays"), getIntParam("WaveLength"),fixedPos());
	}

	@Override
	public void updateOpticsObject(OpticsObject object) {
		((PointLightSource)object).setPointLightRays(getIntParam("LightRays"),getIntParam("WaveLength"));
		
	}
}
