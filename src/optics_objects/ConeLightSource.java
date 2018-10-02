package optics_objects;

import util.Vector2d;

public class ConeLightSource extends LightSource {
	private static final long serialVersionUID = 1L;
	private double coneAngle;
	public ConeLightSource(Vector2d origin, double coneAngle, int rayCount) {
		super(origin, rayCount);
		this.coneAngle = coneAngle;
		create();
		
	}

	@Override
	protected void create() {
		double deltaAngle = coneAngle/(rayCount - 1);
		Vector2d ray = new Vector2d(1, 0).rotate(-coneAngle/2);
		for(int i = 0; i < rayCount; i++) {
			super.addLightRay(ray.copy());
			ray.rotate(deltaAngle);
		}
	}
}
