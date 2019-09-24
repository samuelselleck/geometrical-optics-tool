package gui.optics_object_creators;

import model.optics_objects.ConcaveLens;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class ConcaveLensCreator extends LensCreator {

	public ConcaveLensCreator() {
		addSlider("Diameter", 103, 800, 180);
		addSlider("Width", 11, 100, 10);
		addSlider("Radius 1", 55, 800, 300);
		addSlider("Radius 2", 55, 800, 300);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		double d = get("Diameter");
		double r1 = get("Radius 1");
		double r2 = get("Radius 2");
		if(r1*2 >= d && r2*2 >= d) {
			return new ConcaveLens(origin, getCreatorProperties());
		} else {
			return null;
		}
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj instanceof ConcaveLens;
	}
}
