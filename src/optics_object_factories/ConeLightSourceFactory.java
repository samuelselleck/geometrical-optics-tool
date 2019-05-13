package optics_object_factories;

import gui.OpticsView;
import optics_objects.lights.ConeLightSource;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class ConeLightSourceFactory extends OpticsObjectFactory {

	public ConeLightSourceFactory(OpticsView view) {
		super(view);
		addSlider("Cone Angle", 11, 180, 30);
		addSlider("LightRays", 11, 100, 20);
	}
	
	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new ConeLightSource(origin, getParam("Cone Angle")/180*Math.PI,
				getIntParam("LightRays"));
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj instanceof ConeLightSource;
	}
}
