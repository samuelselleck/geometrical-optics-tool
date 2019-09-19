package gui.optics_object_creators;

import model.optics_objects.OpticsObject;
import model.optics_objects.RectangleLens;
import util.Vector2d;

public class RectangleLensCreator extends LensCreator {

	public RectangleLensCreator() {
		addSlider("Width", 7, 800, 500);
		addSlider("Height", 7, 800, 100);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new RectangleLens(origin, getCreatorProperties());
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj instanceof RectangleLens;
	}

}
