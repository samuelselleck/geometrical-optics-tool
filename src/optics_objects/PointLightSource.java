package optics_objects;

import util.Vector2d;

public class PointLightSource extends LightSource {
	private static final long serialVersionUID = 1L;

	public PointLightSource(Vector2d origin, int density) {
		super(origin);
		for (int i = 0; i < density; i++) {
			double ang = (2 * Math.PI / density) * i;
			double x = Math.cos(ang);
			double y = Math.sin(ang);
			super.addLightRay(new Vector2d(x, y));
		}
	}
}
