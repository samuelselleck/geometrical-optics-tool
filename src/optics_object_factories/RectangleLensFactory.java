package optics_object_factories;

import optics_objects.materials.RectangleLens;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class RectangleLensFactory extends OpticsObjectFactory {

	public RectangleLensFactory() {
		addSlider("Width", 7, 800, 500);
		addSlider("Height", 7, 800, 100);
		addSlider("Refractionindex", 1, 3, 1.5);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new RectangleLens(origin, getParam("Refractionindex"), getParam("Width"),
				getParam("Height"));
	}

}
