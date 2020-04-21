package gui.optics_object_creators;

import gui.Main;
import model.optics_objects.OpticsObject;
import model.optics_objects.RoundedMirror;
import util.Vector2d;

public class RoundedMirrorCreator extends OpticsObjectCreator {
	
	public RoundedMirrorCreator() {
		addSlider("Diameter", 2, Main.getIntProperty("maxobjectsizecm"), 4);
		addSlider("Radius", 1, 20, 5);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new RoundedMirror(getInitializationProperties(origin));
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj.getClass().equals(RoundedMirror.class);
	}
}
