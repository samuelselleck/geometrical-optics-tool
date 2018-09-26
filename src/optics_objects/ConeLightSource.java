package optics_objects;

import util.Vector2d;

public class ConeLightSource extends LightSource {
	private static final long serialVersionUID = 1L;

	public ConeLightSource(Vector2d origin, double coneAngle, int density) {
		super(origin);
		double deltaAngle = coneAngle/(density - 1);
		Vector2d ray = new Vector2d(1, 0).rotate(-coneAngle/2);
		for(int i = 0; i < density; i++) {
			super.addLightRay(ray.copy());
			ray.rotate(deltaAngle);
		}
	}
}
