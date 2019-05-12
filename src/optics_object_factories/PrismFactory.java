package optics_object_factories;

import gui.OpticsView;
import optics_objects.materials.Prism;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class PrismFactory extends OpticsObjectFactory {

	public PrismFactory(OpticsView view) {
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
	public boolean setEditing(OpticsObject obj) {
		if(obj instanceof Prism) {
			this.requestFocus();
			return true;
		}
		return false;
	}
}
