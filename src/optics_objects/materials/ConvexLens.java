package optics_objects.materials;

import optics_objects.templates.Lens;
import util.Vector2d;

public class ConvexLens extends Lens {
	private static final long serialVersionUID = 1L;
	
	public ConvexLens(Vector2d origin, double d, double r1, double r2, double refractionindex) {
		super(origin, refractionindex);
		super.addProperty("Diameter", d);
		super.addProperty("Radius 1", r1);
		super.addProperty("Radius 2", r2);
		update();
	}

	@Override
	protected void update() {
		super.clear();
		int quarterResolution = getResolution() /4;
		double d = get("Diameter");
		double r1 = get("Radius 1");
		double r2 = get("Radius 2");
		
		double leftAngle = Math.acos(1 - d * d / (2 * r1 * r1)) / 2;
		double rightAngle = Math.acos(1 - d * d / (2 * r2 * r2)) / 2;
		double leftStep = leftAngle / quarterResolution;
		double rightStep = rightAngle / quarterResolution;
		double leftDist = Math.sqrt(r1 * r1 - d * d / 4);
		double rightDist = Math.sqrt(r2 * r2 - d * d / 4);

		Vector2d pos = new Vector2d(-leftDist, 0);
		Vector2d vec = new Vector2d(r1, 0).rotate(-leftAngle);
		for (int i = 0; i < quarterResolution * 2; i++) {
			points.add(pos.copy().add(vec.rotate(leftStep)));
		}
		pos = new Vector2d(rightDist, 0);
		vec = new Vector2d(-r2, 0).rotate(-rightAngle);
		for (int i = 0; i < quarterResolution * 2; i++) {
			points.add(pos.copy().add(vec.rotate(rightStep)));
		}
		super.initObject();
	}
}
