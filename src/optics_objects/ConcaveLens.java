package optics_objects;

import util.Vector2d;

public class ConcaveLens extends Lens {
	private static final long serialVersionUID = 1L;

	public ConcaveLens(Vector2d origin, double d, double w, double r1, double r2, double refractionindex) {
		super(origin, refractionindex);
		int quarterResolution = Lens.LENSRESOLUTION /4;
		
		double leftAngle = Math.acos(1 - d * d / (2 * r1 * r1)) / 2;
		double rightAngle = Math.acos(1 - d * d / (2 * r2 * r2)) / 2;
		double leftStep = leftAngle / quarterResolution;
		double rightStep = rightAngle / quarterResolution;
		double leftDist = r1 + w/2;
		double rightDist = r2 + w/2;

		
		Vector2d pos = new Vector2d(rightDist, 0);
		Vector2d vec = new Vector2d(-r2, 0).rotate(rightAngle);
		for (int i = 0; i <= quarterResolution * 2; i++) {
			points.add(pos.copy().add(vec));
			vec.rotate(-rightStep);
		}
		pos = new Vector2d(-leftDist, 0);
		vec = new Vector2d(r1, 0).rotate(leftAngle);
		for (int i = 0; i <= quarterResolution * 2; i++) {
			points.add(pos.copy().add(vec));
			vec.rotate(-leftStep);
		}
		super.createBounds();
	}
}
