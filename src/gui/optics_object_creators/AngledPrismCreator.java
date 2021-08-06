package gui.optics_object_creators;

import gui.Main;
import gui.OpticsEnvironment;
import model.optics_objects.AngledPrism;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class AngledPrismCreator extends LensCreator {

	public AngledPrismCreator(OpticsEnvironment environment) {
		super(environment);
		addSlider("Angle", 5, 90, 30);
		addSlider("Height", 1, Main.getIntProperty("maxobjectsizecm")/2, 2);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new AngledPrism(getInitializationProperties(origin));
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj.getClass().equals(AngledPrism.class);
	}
}
