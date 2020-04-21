package gui.optics_object_creators;

import gui.Main;
import model.optics_objects.OpticsObject;
import model.optics_objects.ParabolicMirror;
import util.Vector2d;

public class ParabolicMirrorCreator extends OpticsObjectCreator {
	
	public ParabolicMirrorCreator() {
		addSlider("Diameter", 2, Main.getIntProperty("maxobjectsizecm"), 4);
		addSlider("Depth", 0.5, Main.getIntProperty("maxobjectsizecm"), 1);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new ParabolicMirror(getInitializationProperties(origin));
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj.getClass().equals(ParabolicMirror.class);
	}
}
