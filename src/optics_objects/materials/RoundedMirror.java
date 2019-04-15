package optics_objects.materials;

import optics_objects.templates.Mirror;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class RoundedMirror extends Mirror {
	private static final long serialVersionUID = 1L;

	public RoundedMirror(Vector2d origin, double diameter, double depth, boolean fixedPosition) {
		super(origin, fixedPosition);

		setPoints(diameter, depth);
	}
	
	public void setPoints(double diameter, double depth) {
		clearPoints();
		for(int i = 0; i <= OpticsObject.getResolution(); i++) {
			double x = (2.0*i/OpticsObject.getResolution() - 1);
			double y = x*x;
			points.add(new Vector2d(x*diameter/2, depth*(y - 1.0/2)));
		}
		super.createBounds();
	}
}
