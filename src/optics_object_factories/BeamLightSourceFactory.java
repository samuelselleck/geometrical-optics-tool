package optics_object_factories;

import optics_objects.lights.BeamLightSource;
import optics_objects.templates.LightSource;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class BeamLightSourceFactory extends OpticsObjectFactory {

	public BeamLightSourceFactory() {
		addSlider("Diameter", 23, 400, 80);
		addSlider("LightRays", 1, 100, 20);
		addSlider("WaveLength", 380, 700, LightSource.lightWaveDefault());
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new BeamLightSource(origin, getParam("Diameter"),
				getIntParam("LightRays"), getIntParam("WaveLength"),fixedPos());
	}

	@Override
	public void updateOpticsObject(OpticsObject object) {
		((BeamLightSource) object).setBeamRays(getParam("Diameter"),getIntParam("LightRays"),getIntParam("WaveLength"));
	}
	
	

}
