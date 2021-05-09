package gui.optics_object_creators;

import gui.Main;
import model.optics_objects.OpticsObject;
import model.optics_objects.ThinLens;
import util.Vector2d;

public class ThinLensCreator extends OpticsObjectCreator {

	public ThinLensCreator() {
		addSlider("Diameter", 2, Main.getIntProperty("maxobjectsizecm"), 4);
		addSlider("Focal Length", -Main.getIntProperty("maxobjectsizecm"), Main.getIntProperty("maxobjectsizecm"), 4);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new ThinLens(getInitializationProperties(origin));
	}
	
	@Override
	public boolean editsOpticsObject(OpticsObject obj) {
		return obj.getClass().equals(ThinLens.class);
	}

}
