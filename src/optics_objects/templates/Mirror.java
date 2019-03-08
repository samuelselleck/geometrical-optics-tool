package optics_objects.templates;

import util.Vector2d;

public abstract class Mirror extends Material {
	
	private static final long serialVersionUID = 1L;
	
	public Mirror(Vector2d origin, boolean fixedPosition) {
		super(origin, fixedPosition);
	}
	
	@Override
	public double getAngle(double angleIn, double wavelength, boolean dir) {
		return Math.PI - angleIn;
	}
}
