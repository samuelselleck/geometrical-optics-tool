package gui.optics_object_creators;

import gui.OpticsView;
import model.optics_objects.OpticsObject;
import model.optics_objects.PointLightSource;
import util.Vector2d;

public class PointLightSourceCreator extends LightSourceCreator {

	public PointLightSourceCreator(OpticsView view) {
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
