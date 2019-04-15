package optics_objects.lights;

import optics_objects.templates.LightSource;
import util.Vector2d;

public class BeamLightSource extends LightSource {
	private static final long serialVersionUID = 1L;
	
	public BeamLightSource(Vector2d origin, double diameter, int rayCount, boolean fixedPosition) {
		super(origin, rayCount, fixedPosition);

		setBeamRays(diameter, rayCount);
	}
	
	public void setBeamRays(double diameter, int rayCount) {
		super.clearLightRays();
		
		Vector2d posVec = new Vector2d(0, 1).mult(diameter).div(rayCount);
		Vector2d rayVec = posVec.copy().normalize().rotate(-Math.PI / 2);
		for (int i = rayCount / 2; i >= -rayCount / 2; i--) {
			super.addLightRay(posVec.copy().mult(i), rayVec.copy());
		}
	}
}
