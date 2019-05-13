package optics_objects.lights;

import optics_objects.templates.LightSource;
import util.Vector2d;

public class BeamLightSource extends LightSource {
	private static final long serialVersionUID = 1L;
	
	public BeamLightSource(Vector2d origin, double diameter, int rayCount) {
		super(origin, rayCount);
		super.addProperty("Diameter", diameter);
		update();
	}

	@Override
	protected void update() {
		super.clear();
		Vector2d posVec = new Vector2d(0, 1).mult(get("Diameter")).div(get("LightRays"));
		Vector2d rayVec = posVec.copy().normalize().rotate(-Math.PI / 2);
		for (int i = (int) (get("LightRays") / 2); i >= -get("LightRays") / 2; i--) {
			super.addLightRay(posVec.copy().mult(i), rayVec.copy());
		}
		super.initObject();
	}
}
