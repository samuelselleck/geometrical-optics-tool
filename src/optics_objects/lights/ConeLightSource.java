package optics_objects.lights;

import optics_objects.templates.LightSource;
import util.Vector2d;

public class ConeLightSource extends LightSource {
	private static final long serialVersionUID = 1L;
	
	public ConeLightSource(Vector2d origin, double coneAngle, int rayCount) {
		super(origin, rayCount);
		super.addProperty("Cone Angle", coneAngle);
		update();
	}

	@Override
	protected void update() {
		super.clearRays();
		double deltaAngle = get("Cone Angle")/180*Math.PI/(get("LightRays") - 1);
		Vector2d ray = new Vector2d(1, 0).rotate(-get("Cone Angle")/180*Math.PI/2);
		for(int i = 0; i < get("LightRays"); i++) {
			super.addLightRay(ray.copy());
			ray.rotate(deltaAngle);
		}
		super.initObject();
	}
}
