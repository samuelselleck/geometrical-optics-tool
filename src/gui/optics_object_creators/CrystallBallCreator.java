package gui.optics_object_creators;

import model.optics_objects.CrystalBall;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class CrystallBallCreator extends LensCreator {
	
	public CrystallBallCreator() {
		addSlider("Radius", 11, 500, 80);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new CrystalBall(origin, getCreatorProperties());
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj instanceof CrystalBall;
	}
}
