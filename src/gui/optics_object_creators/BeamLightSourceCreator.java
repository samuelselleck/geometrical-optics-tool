package gui.optics_object_creators;

import gui.OpticsView;
import model.optics_objects.BeamLightSource;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class BeamLightSourceCreator extends OpticsObjectCreator {

	public BeamLightSourceCreator(OpticsView view) {
		super(view);
		addSlider("Diameter", 23, 400, 80);
		addSlider("LightRays", 1, 100, 20);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new BeamLightSource(origin, getParam("Diameter"),
				getIntParam("LightRays"));
	}

	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj instanceof BeamLightSource;
	}

}
