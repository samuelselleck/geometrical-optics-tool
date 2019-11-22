package gui.optics_object_creators;

import gui.Main;
import model.optics_objects.FlatMirror;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class FlatMirrorCreator extends OpticsObjectCreator {

	public FlatMirrorCreator() {
		addSlider("Diameter", 2, Main.getIntProperty("maxobjectsizecm"), 4);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new FlatMirror(getInitializationProperties(origin));
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj instanceof FlatMirror;
	}

}
