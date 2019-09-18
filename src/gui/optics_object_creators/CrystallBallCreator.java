package gui.optics_object_creators;

import model.optics_objects.CrystalBall;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class CrystallBallCreator extends OpticsObjectCreator {
	
	public CrystallBallCreator() {
		addSlider("Radius", 11, 500, 80);
		addSlider("Refractionindex", 1, 3, 1.5);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new CrystalBall(origin, getSliderProperties());
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj instanceof CrystalBall;
	}
}
