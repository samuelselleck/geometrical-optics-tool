package optics_object_factories;

import optics_objects.materials.Prism;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class CrystallBallFactory extends OpticsObjectFactory {
	
	public CrystallBallFactory() {
		addSlider("Radius", 11, 500, 80);
		addSlider("Refractionindex", 1, 3, 1.5);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new Prism(origin, OpticsObject.getResolution(), getParam("Radius"),
				getParam("Refractionindex"), fixedPos());
	}
}
