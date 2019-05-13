package optics_object_factories;

import gui.OpticsView;
import optics_objects.materials.RectangleWall;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class RectangleWallFactory extends OpticsObjectFactory {
	
	public RectangleWallFactory(OpticsView view) {
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
