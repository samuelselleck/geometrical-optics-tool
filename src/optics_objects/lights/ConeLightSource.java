package optics_objects.lights;

import optics_objects.templates.LightSource;
import util.Vector2d;

public class ConeLightSource extends LightSource {
	private static final long serialVersionUID = 1L;
	
	public ConeLightSource(Vector2d origin, double coneAngle, int rayCount, boolean fixedPosition) {
		super(origin, rayCount, fixedPosition);
		double deltaAngle = coneAngle/(rayCount - 1);
		Vector2d ray = new Vector2d(1, 0).rotate(-coneAngle/2);
		for(int i = 0; i < rayCount; i++) {
			super.addLightRay(ray.copy());
			ray.rotate(deltaAngle);
		}
		
	}
}
