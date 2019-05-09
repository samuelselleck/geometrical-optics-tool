package optics_objects.templates;

import util.Vector2d;

public abstract class Wall extends Material {

	public Wall(Vector2d origin) {
		super(origin);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public double getAngle(double angleIn, double wavelength, boolean dir) {
		return 0;
	}
	
	@Override
	public void createBounds() {
		points.add(points.get(0).copy()); //Close loop
		super.createBounds();
	}
}
