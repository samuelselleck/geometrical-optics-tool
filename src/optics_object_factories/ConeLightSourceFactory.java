package optics_object_factories;

import optics_objects.lights.ConeLightSource;
import optics_objects.templates.LightSource;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class ConeLightSourceFactory extends OpticsObjectFactory {

	public ConeLightSourceFactory() {
		addSlider("Cone Angle", 11, 180, 30);
		addSlider("LightRays", 11, 100, 20);
		addSlider("WaveLength", 380, 700, LightSource.lightWaveDefault());
	}
	
	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new ConeLightSource(origin, getParam("Cone Angle")/180*Math.PI,
				getIntParam("LightRays"), getIntParam("LightRays"), fixedPos());
	}

	@Override
	public void updateOpticsObject(OpticsObject object) {
		((ConeLightSource)object).setConeRays(getParam("Cone Angle")/180*Math.PI, getIntParam("LightRays"),getIntParam("WaveLength"));
	}
}
