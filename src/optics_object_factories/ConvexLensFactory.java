package optics_object_factories;

import optics_objects.materials.ConvexLens;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class ConvexLensFactory extends OpticsObjectFactory {

	public ConvexLensFactory() {
		String[] names = new String[] { "Diameter", "Radius 1", "Radius 2", "Refractionindex" };
		double[] values = new double[] { 180, 300, 300, 1.5};
		Vector2d[] bounds = new Vector2d[values.length];
		bounds[0] = new Vector2d(103, 800);
		bounds[1] = new Vector2d(55, 800);
		bounds[2] = new Vector2d(55, 800);
		bounds[3] = new Vector2d(1, 10);

		super.setSliders(names, bounds, values);
	}

	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		double d = super.getSliderValue(0);
		double r1 = super.getSliderValue(1);
		double r2 = super.getSliderValue(2);
		if(r1*2 >= d && r2*2 >= d) {
			return new ConvexLens(origin, d, r1, r2,
					super.getSliderValue(3), super.positionFixed.selectedProperty().getValue());
		} else {
			return null;
		}
	}

}
