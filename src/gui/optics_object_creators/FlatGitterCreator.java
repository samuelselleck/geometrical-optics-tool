package gui.optics_object_creators;

import gui.Main;
import model.optics_objects.FlatGitter;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class FlatGitterCreator extends OpticsObjectCreator {

	public FlatGitterCreator() {
		addSlider("Diameter", 2, Main.getIntProperty("maxobjectsizecm"), 4);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new FlatGitter(origin, getCreatorProperties());
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj instanceof FlatGitter;
	}

}
