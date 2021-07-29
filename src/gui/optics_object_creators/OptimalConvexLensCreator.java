package gui.optics_object_creators;

import gui.Main;
import gui.OpticsEnvironment;
import model.optics_objects.OpticsObject;
import model.optics_objects.OptimalConvexLens;
import util.Vector2d;

public class OptimalConvexLensCreator extends LensCreator {

	public OptimalConvexLensCreator(OpticsEnvironment environment) {
		super(environment);
		double max = Main.getIntProperty("maxobjectsizecm");
		
		addSlider("Diameter", 2, max, 4);
		addSlider("Wavelength Optimum", 
				Main.getIntProperty("minwavelength"),
				Main.getIntProperty("maxwavelength"),
				Main.getIntProperty("defaultwavelength"));
		addSlider("Focal Length", 5, 15, 10);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new OptimalConvexLens(getInitializationProperties(origin));
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj.getClass().equals(OptimalConvexLens.class);
	}

}
