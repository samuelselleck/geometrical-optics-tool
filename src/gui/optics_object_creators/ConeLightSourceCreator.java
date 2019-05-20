package gui.optics_object_creators;

import gui.OpticsView;
import model.optics_objects.ConeLightSource;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class ConeLightSourceCreator extends LightSourceCreator {

	public ConeLightSourceCreator(OpticsView view) {
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
