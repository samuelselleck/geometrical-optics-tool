package optics_object_factories;

import optics_objects.materials.OptimalConvexLens;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class OptimalConvexLensFactory extends OpticsObjectFactory {

	public OptimalConvexLensFactory() {
		addSlider("Diameter", 103, 800, 180);
		addSlider("Radius 1", 55, 800, 300);
		addSlider("Radius 2", 55, 800, 300);
		addSlider("Refractionindex", 1, 3, 1.5);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		double d = getParam("Diameter");
		double r1 = getParam("Radius 1");
		double r2 = getParam("Radius 2");
		if(r1*2 >= d && r2*2 >= d) {
			return new OptimalConvexLens(origin, d, r1, r2,
					getParam("Refractionindex"), fixedPos());
		} else {
			return null;
		}
	}

	@Override
	public void updateOpticsObject(OpticsObject object) {
		// TODO Auto-generated method stub
		
	}

}
