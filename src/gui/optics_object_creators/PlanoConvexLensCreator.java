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
		addSlider("Refractive Optimum", 1, 3, 2);
		addSlider("Focal Length", 1, 10, 3);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new PlanoConvexLens(getInitializationProperties(origin));
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj.getClass().equals(PlanoConvexLens.class);
	}

}
