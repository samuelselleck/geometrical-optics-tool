package optics_object_factories;


import optics_objects.materials.OptimalConvexLens;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class OptimalConvexLensFactory extends OpticsObjectFactory {

	public OptimalConvexLensFactory() {
		addSlider("Diameter", 103, 800, 180);
		addSlider("Refractionindex", 1, 3, 1.5);
		addSlider("Focal length", 100 , 1000, 300);
		addCheckBox("Show optical axis");
	}


	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		return new OptimalConvexLens(origin, getParam("Diameter"), getParam("Focal length"),
				getParam("Refractionindex"), fixedPos(), getBoxParam("Show optical axis"));
	}
	
	@Override
	public void updateOpticsObject(OpticsObject object) {
		//Vi funderar kommer tillbaka sen
	}
}
