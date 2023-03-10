package gui.optics_object_creators;

import gui.Main;
import model.optics_objects.FlatGitter;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class FlatGitterCreator extends OpticsObjectCreator {

	public FlatGitterCreator() {
		addSlider("Grating Constant", 1000, 5000, 3000);
		addSlider("Diameter", 2, Main.getIntProperty("maxobjectsizecm"), 4);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new FlatGitter(getInitializationProperties(origin));
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj.getClass().equals(FlatGitter.class);
	}

}
