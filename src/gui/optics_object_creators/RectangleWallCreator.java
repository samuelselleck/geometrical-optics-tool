package gui.optics_object_creators;

import gui.OpticsView;
import model.optics_objects.OpticsObject;
import model.optics_objects.RectangleWall;
import util.Vector2d;

public class RectangleWallCreator extends OpticsObjectCreator {
	
	public RectangleWallCreator(OpticsView view) {
		super(view);
		addSlider("Width", 7, 800, 30);
		addSlider("Height", 7, 800, 200);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new RectangleWall(origin,
				getParam("Width"), getParam("Height"));
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj instanceof RectangleWall;
	}

}