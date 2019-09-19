package gui.optics_object_creators;

import model.optics_objects.OpticsObject;
import model.optics_objects.Prism;
import util.Vector2d;

public class PrismCreator extends LensCreator {

	public PrismCreator() {
		addSlider("Edges", 3, 8, 3, true);
		addSlider("Radius", 11, 300, 80);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new Prism(origin, getCreatorProperties());
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj instanceof Prism;
	}
}
