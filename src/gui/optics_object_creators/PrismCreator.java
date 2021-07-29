package gui.optics_object_creators;

import gui.Main;
import gui.OpticsEnvironment;
import model.optics_objects.OpticsObject;
import model.optics_objects.Prism;
import util.Vector2d;

public class PrismCreator extends LensCreator {

	public PrismCreator(OpticsEnvironment environment) {
		super(environment);
		addSlider("Edges", 3, 8, 3, true);
		addSlider("Radius", 1, Main.getIntProperty("maxobjectsizecm")/2, 2);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new Prism(getInitializationProperties(origin));
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj.getClass().equals(Prism.class);
	}
}
