package optics_objects;

import util.Vector2d;

public class PointLightSource extends LightSource {
	private static final long serialVersionUID = 1L;

	public PointLightSource(Vector2d origin, int rayCount) {
		super(origin, rayCount);
		create();
	}

	@Override
	protected void create() {
		for (int i = 0; i < rayCount; i++) {
			double ang = (2 * Math.PI / rayCount) * i;
			double x = Math.cos(ang);
			double y = Math.sin(ang);
			super.addLightRay(new Vector2d(x, y));
		}
	}
}
