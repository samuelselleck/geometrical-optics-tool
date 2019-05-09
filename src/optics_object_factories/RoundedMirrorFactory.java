package optics_object_factories;

import optics_objects.materials.RoundedMirror;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class RoundedMirrorFactory extends OpticsObjectFactory {
	
	public RoundedMirrorFactory() {
		addSlider("Diameter", 103, 800, 200);
		addSlider("Depth", 11, 500, 30);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new RoundedMirror(origin, getParam("Diameter"),
				getParam("Depth"));
	}
}
