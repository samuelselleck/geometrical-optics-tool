package gui.optics_object_creators;

import gui.OpticsView;
import model.optics_objects.ConcaveLens;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class ConcaveLensCreator extends OpticsObjectCreator {

	public ConcaveLensCreator(OpticsView view) {
		super(view);
		addSlider("Diameter", 103, 800, 180);
		addSlider("Width", 11, 100, 10);
		addSlider("Radius 1", 55, 800, 300);
		addSlider("Radius 2", 55, 800, 300);
		addSlider("Refractionindex", 1, 3, 1.5);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		double d = getParam("Diameter");
		double r1 = getParam("Radius 1");
		double r2 = getParam("Radius 2");
		if(r1*2 >= d && r2*2 >= d) {
			return new ConcaveLens(origin, d, getParam("Width"), r1, r2,
					getParam("Refractionindex"));
		} else {
			return null;
		}
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj instanceof ConcaveLens;
	}
}