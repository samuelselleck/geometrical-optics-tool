package gui.optics_object_creators;

import gui.Main;
import model.optics_objects.CrystalBall;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class CrystallBallCreator extends LensCreator {
	
	public CrystallBallCreator() {
		addSlider("Radius", 1, Main.getIntProperty("maxobjectsizecm")/2, 2);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new CrystalBall(getInitializationProperties(origin));
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj.getClass().equals(CrystalBall.class);
	}
}
