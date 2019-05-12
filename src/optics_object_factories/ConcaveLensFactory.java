package optics_object_factories;

import gui.OpticsView;
import optics_objects.materials.ConcaveLens;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class ConcaveLensFactory extends OpticsObjectFactory {

	public ConcaveLensFactory(OpticsView view) {
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
	public boolean setEditing(OpticsObject obj) {
		if(obj instanceof ConcaveLens) {
			this.requestFocus();
			return true;
		}
		return false;
	}
}
