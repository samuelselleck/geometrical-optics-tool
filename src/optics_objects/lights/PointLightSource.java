package optics_objects.lights;

import optics_objects.templates.LightSource;
import util.Vector2d;

public class PointLightSource extends LightSource {
	private static final long serialVersionUID = 1L;
	
	public PointLightSource(Vector2d origin, int rayCount) {
		super(origin, rayCount);
		update();
	}

	@Override
	protected void update() {
		super.clear();
		for (int i = 0; i < get("LightRays"); i++) {
			double ang = (2 * Math.PI / get("LightRays")) * i;
			double x = Math.cos(ang);
			double y = Math.sin(ang);
			super.addLightRay(new Vector2d(x, y));
		}
		super.initObject();
	}
}
