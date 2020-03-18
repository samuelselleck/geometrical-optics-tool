package gui.optics_object_creators;

import gui.Main;
import model.optics_objects.BeamLightSource;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class BeamLightSourceCreator extends LightSourceCreator {

	public BeamLightSourceCreator() {
		addSlider("Diameter", 0.5, Main.getIntProperty("maxobjectsizecm"), 3);
		addSlider("LightRays", 1, 100, 20, true);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new BeamLightSource(getInitializationProperties(origin));
	}

	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj.getClass().equals(BeamLightSource.class);
	}

}
