package gui.optics_object_creators;

import gui.Main;
import model.optics_objects.OpticsObject;
import model.optics_objects.RectangleWall;
import util.Vector2d;

public class RectangleWallCreator extends OpticsObjectCreator {
	
	public RectangleWallCreator() {
		addSlider("Width", 0.2, Main.getIntProperty("maxobjectsizecm"), 0.2);
		addSlider("Height", 0.2, Main.getIntProperty("maxobjectsizecm"), 4);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new RectangleWall(getInitializationProperties(origin));
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj.getClass().equals(RectangleWall.class);
	}

}
