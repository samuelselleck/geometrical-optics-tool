package gui.optics_object_creators;

import gui.Main;
import javafx.beans.property.SimpleDoubleProperty;
import model.optics_objects.DetectorWall;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class DetectorWallCreator extends OpticsObjectCreator {
	
	public DetectorWallCreator() {
		addSlider("Height", 0.2, Main.getIntProperty("maxobjectsizecm"), 8);
		addProperty("Width", new SimpleDoubleProperty(0.3));
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new DetectorWall(getInitializationProperties(origin));
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj.getClass().equals(DetectorWall.class);
	}

}
