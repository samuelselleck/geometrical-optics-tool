package optics_object_factories;

import optics_objects.materials.ConvexLens;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class ConvexLensFactory extends OpticsObjectFactory {

	public ConvexLensFactory() {
		addSlider("Diameter", 103, 800, 180);
		addSlider("Radius 1", 55, 800, 300);
		addSlider("Radius 2", 55, 800, 300);
		addSlider("Refractionindex", 1, 3, 1.5);
		
		addCheckBox("Show optical axis");
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		double d = getParam("Diameter");
		double r1 = getParam("Radius 1");
		double r2 = getParam("Radius 2");
		if(r1*2 >= d && r2*2 >= d) {
			return new ConvexLens(origin, d, r1, r2,
					getParam("Refractionindex"), fixedPos(), getBoxParam("Show optical axis"));
		} else {
			return null;
		}
	}

	@Override
	public void updateOpticsObject(OpticsObject object) {
		double d = getParam("Diameter");
		double r1 = getParam("Radius 1");
		double r2 = getParam("Radius 2");
		if(r1*2 >= d && r2*2 >= d) {
			((ConvexLens)object).setPoints(d, r1, r2);
		}
		
		((ConvexLens)object).setRefractionIndex(getParam("Refractionindex"));
		((ConvexLens)object).showOpticalAxis(getBoxParam("Show optical axis"));
	}

}
