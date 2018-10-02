package optics_objects;

import util.Vector2d;

public class BeamLightSource extends LightSource {
	private static final long serialVersionUID = 1L;
	private double diameter;
	
	public BeamLightSource(Vector2d origin, double diameter, int rayCount) {
		super(origin, rayCount);
		this.diameter = diameter;
		create();
	}
	
	protected void create() {
		Vector2d posVec = new Vector2d(0, 1).mult(diameter).div(rayCount);
		Vector2d rayVec = posVec.copy().normalize().rotate(-Math.PI / 2);
		for (int i = rayCount / 2; i >= -rayCount / 2; i--) {
			super.addLightRay(posVec.copy().mult(i), rayVec.copy());
		}
	}
}
