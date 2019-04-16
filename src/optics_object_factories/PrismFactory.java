package optics_object_factories;

import optics_objects.materials.Prism;
import optics_objects.templates.OpticsObject;
import settings.BigSettingsBox;
import util.Vector2d;

public class PrismFactory extends OpticsObjectFactory {

	public PrismFactory() {
		addSlider("Edges", 3, 8, 3);
		addSlider("Radius", 11, 300, 80);
		addSlider("Refractionindex", 1, 3, 1.5);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new Prism(origin, getIntParam("Edges"), getParam("Radius"),
				getParam("Refractionindex"), fixedPos());
	}
	
	@Override
	public void updateOpticsObject(OpticsObject object) {
		((Prism) object).setPoints(getIntParam("Edges"), getParam("Radius"));
		((Prism) object).setRefractionIndex(getParam("Refractionindex"));
	}
	
}
