package gui.optics_object_creators;

import model.optics_objects.FlatMirror;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class FlatMirrorCreator extends OpticsObjectCreator {

	public FlatMirrorCreator() {
		addSlider("Diameter", 103, 800, 200);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new FlatMirror(origin, getCreatorProperties());
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj instanceof FlatMirror;
	}

}
