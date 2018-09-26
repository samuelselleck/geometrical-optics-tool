package optics_objects;

import util.Vector2d;

public class RoundedMirror extends Mirror {
	private static final long serialVersionUID = 1L;

	public RoundedMirror(Vector2d origin, double diameter, double depth) {
		super();
		this.origin = origin;
		for(int i = 0; i < Mirror.MIRRORRESOLUTION; i++) {
			double x = (2.0*i/Mirror.MIRRORRESOLUTION - 1);
			double y = x*x;
			points.add(new Vector2d(x*diameter/2, depth*(y - 1.0/2)));
		}
		super.createBounds();
	}
}
