package optics_objects.lights;

import optics_objects.templates.LightSource;
import util.Vector2d;

public class DiffractionGratingLightSource extends LightSource {

	public DiffractionGratingLightSource(Vector2d origin, double slitsPerUnitDistance, int nrMax) {
		super(origin, nrMax, false);
	}

}
