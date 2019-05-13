package optics_object_factories;

import gui.OpticsView;
import optics_objects.lights.PointLightSource;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class PointLightSourceFactory extends OpticsObjectFactory {

	public PointLightSourceFactory(OpticsView view) {
		super(view);
		addSlider("LightRays", 11, 300, 30);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new PointLightSource(origin, getIntParam("LightRays"));
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj instanceof PointLightSource;
	}
}
