package optics_object_factories;

import gui.OpticsView;
import optics_objects.materials.CrystalBall;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class CrystallBallFactory extends OpticsObjectFactory {
	
	public CrystallBallFactory(OpticsView view) {
		super(view);
		addSlider("Radius", 11, 500, 80);
		addSlider("Refractionindex", 1, 3, 1.5);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new CrystalBall(origin, getParam("Radius"),
				getParam("Refractionindex"));
	}
	
	@Override
	public boolean setEditing(OpticsObject obj) {
		if(obj instanceof CrystalBall) {
			return true;
		}
		return false;
	}
}
