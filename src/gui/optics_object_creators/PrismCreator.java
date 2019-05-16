package gui.optics_object_creators;

import gui.OpticsView;
import model.optics_objects.OpticsObject;
import model.optics_objects.Prism;
import util.Vector2d;

public class PrismCreator extends OpticsObjectCreator {

	public PrismCreator(OpticsView view) {
		super(view);
		addSlider("Edges", 3, 8, 3);
		addSlider("Radius", 11, 300, 80);
		addSlider("Refractionindex", 1, 3, 1.5);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new Prism(origin, getIntParam("Edges"), getParam("Radius"),
				getParam("Refractionindex"));
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj instanceof Prism;
	}
}
