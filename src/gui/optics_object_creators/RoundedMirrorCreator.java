package gui.optics_object_creators;

import model.optics_objects.OpticsObject;
import model.optics_objects.RoundedMirror;
import util.Vector2d;

public class RoundedMirrorCreator extends OpticsObjectCreator {
	
	public RoundedMirrorCreator() {
		addSlider("Diameter", 103, 800, 200);
		addSlider("Depth", 11, 500, 30);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new RoundedMirror(origin, getCreatorProperties());
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj instanceof RoundedMirror;
	}
}
