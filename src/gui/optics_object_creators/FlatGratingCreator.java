package gui.optics_object_creators;

import gui.Main;
import model.optics_objects.FlatGitter;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class FlatGratingCreator extends OpticsObjectCreator {

	public FlatGratingCreator() {
		addSlider("Grating Constant", 800, 5000, 3000);
		addSlider("Diameter", 2, Main.getIntProperty("maxobjectsizecm"), 4);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new FlatGitter(getInitializationProperties(origin));
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj instanceof FlatGitter;
	}

}
