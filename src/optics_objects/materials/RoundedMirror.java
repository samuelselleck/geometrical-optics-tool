package optics_objects.materials;

import optics_objects.templates.Mirror;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class RoundedMirror extends Mirror {
	private static final long serialVersionUID = 1L;

	public RoundedMirror(Vector2d origin, double diameter, double depth) {
		super(origin);
		super.addProperty("Diameter", diameter);
		super.addProperty("Depth", depth);
		update();
	}
	
	protected void update() {
		super.clear();
		for(int i = 0; i <= OpticsObject.getResolution(); i++) {
			double x = (2.0*i/OpticsObject.getResolution() - 1);
			double y = x*x;
			points.add(new Vector2d(x*get("Diameter")/2, get("Depth")*(y - 1.0/2)));
		}
		super.initObject();
	}
}
