package gui.optics_object_creators;

import gui.Main;
import gui.OpticsEnvironment;
import model.optics_objects.OpticsObject;
import model.optics_objects.PlanoConvexLens;
import util.Vector2d;

public class PlanoConvexLensCreator extends LensCreator {

	public PlanoConvexLensCreator(OpticsEnvironment environment) {
		super(environment);
		double max = Main.getIntProperty("maxobjectsizecm");
		
		addSlider("Diameter", 2, max, 4);
		addSlider("Radius", 1, 20, 10);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		double d = get("Diameter");
		double r = get("Radius");
		if(r*2 >= d) {
			return new PlanoConvexLens(getInitializationProperties(origin));
		} else {
			return null;
		}
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj.getClass().equals(PlanoConvexLens.class);
	}

}
