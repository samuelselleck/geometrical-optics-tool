package optics_object_factories;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import optics_objects.materials.ConvexLens;
import optics_objects.materials.OptimalConvexLens;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class OptimalConvexLensFactory extends OpticsObjectFactory {

	public OptimalConvexLensFactory() {
		addSlider("Diameter", 103, 800, 180);
		addSlider("Refractionindex", 1.05, 3, 1.5);
		addSlider("Focal length", 100, 1000, 300);
		addCheckBox("Show optical axis");
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new OptimalConvexLens(origin, getParam("Diameter"), getParam("Focal length"),
				getParam("Refractionindex"), fixedPos(), getBoxParam("Show optical axis"));
	}

	@Override
	public void updateOpticsObject(OpticsObject object) {
		double d = getParam("Diameter");
		double r = getParam("Refractionindex");
		double f = getParam("Focal length");

		((OptimalConvexLens) object).setFocalLength(getParam("Focal length"));
		((OptimalConvexLens) object).setRefractionIndex(getParam("Refractionindex"));
		((OptimalConvexLens) object).showOpticalAxis(getBoxParam("Show optical axis"));
		((OptimalConvexLens) object).setPoints(d, f);
	}

	@Override
	public void setListeners(EventHandler e) {
		super.setListeners(e);
		String[] relSliders = { "Diameter", "Refractionindex", "Focal length" };

		for (String s : relSliders)
			sliders.get(s).setStyle("");

	}
}
