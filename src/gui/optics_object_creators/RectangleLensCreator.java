package gui.optics_object_creators;

import gui.OpticsView;
import model.optics_objects.OpticsObject;
import model.optics_objects.RectangleLens;
import util.Vector2d;

public class RectangleLensCreator extends OpticsObjectCreator {

	public RectangleLensCreator() {
		addSlider("Width", 7, 800, 500);
		addSlider("Height", 7, 800, 100);
		addSlider("Refractionindex", 1, 3, 1.5);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new RectangleLens(origin, getSliderProperties());
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj instanceof RectangleLens;
	}

}
