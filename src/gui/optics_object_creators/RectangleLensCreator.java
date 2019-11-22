package gui.optics_object_creators;

import gui.Main;
import model.optics_objects.OpticsObject;
import model.optics_objects.RectangleLens;
import util.Vector2d;

public class RectangleLensCreator extends LensCreator {

	public RectangleLensCreator() {
		addSlider("Width", 0.2, Main.getIntProperty("maxobjectsizecm"), 1);
		addSlider("Height", 0.2, Main.getIntProperty("maxobjectsizecm"), 4);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new RectangleLens(getInitializationProperties(origin));
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj instanceof RectangleLens;
	}

}
