package optics_object_factories;

import gui.OpticsView;
import optics_objects.materials.FlatMirror;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class FlatMirrorFactory extends OpticsObjectFactory {

	public FlatMirrorFactory(OpticsView view) {
		super(view);
		addSlider("Diameter", 103, 800, 200);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new FlatMirror(origin, getParam("Diameter"));
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj instanceof FlatMirror;
	}

}
