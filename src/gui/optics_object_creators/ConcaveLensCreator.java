package gui.optics_object_creators;

import gui.Main;
import gui.OpticsEnvironment;
import model.optics_objects.ConcaveLens;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class ConcaveLensCreator extends LensCreator {

	public ConcaveLensCreator(OpticsEnvironment environment) {
		super(environment);
		double max = Main.getIntProperty("maxobjectsizecm");
		
		addSlider("Diameter", 2, max, 4);
		addSlider("Width", 0.2, max, 0.2);
		addSlider("Radius 1", 1, 100, 10);
		addSlider("Radius 2", 1, 100, 10);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		double d = get("Diameter");
		double r1 = get("Radius 1");
		double r2 = get("Radius 2");
		if(r1*2 >= d && r2*2 >= d) {
			return new ConcaveLens(getInitializationProperties(origin));
		} else {
			return null;
		}
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj.getClass().equals(ConcaveLens.class);
	}
}
