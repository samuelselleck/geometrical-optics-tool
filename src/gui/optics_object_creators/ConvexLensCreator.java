package gui.optics_object_creators;

import gui.Main;
import model.optics_objects.ConvexLens;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class ConvexLensCreator extends LensCreator {

	public ConvexLensCreator() {
		double max = Main.getIntProperty("maxobjectsizecm");
		
		addSlider("Diameter", 2, max, 4);
		addSlider("Radius 1", 1, 100, 10);
		addSlider("Radius 2", 1, 100, 10);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		double d = get("Diameter");
		double r1 = get("Radius 1");
		double r2 = get("Radius 2");
		if(r1*2 >= d && r2*2 >= d) {
			return new ConvexLens(getInitializationProperties(origin));
		} else {
			return null;
		}
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj instanceof ConvexLens;
	}

}
