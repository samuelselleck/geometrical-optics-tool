package optics_objects.materials;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import optics_logic.OpticsSettings;
import optics_objects.templates.Lens;
import util.Vector2d;

public class OptimalConvexLens extends Lens {
	private static final long serialVersionUID = 1L;
	private double focalLength;
	private double d;
	private double n;
	public OptimalConvexLens(Vector2d origin, double d, double f, double refractionindex, boolean fixedPosition,
			boolean showAxis) {
		super(origin, refractionindex, fixedPosition);
		focalLength = f;
		this.d = d;
		this.n = refractionindex;
		showOpticalAxis(showAxis);
		setPoints(d, f);

	}

	@Override
	public void draw(GraphicsContext gc, OpticsSettings settings) {
		super.draw(gc, settings);

		if (showOpticalAxis) {
			double f = 1 / ((refractionindex - 1));

			Vector2d fPointR = getOrigin().copy().add(new Vector2d(f, 0).rotate(totalRotation));
			Vector2d fPointL = getOrigin().copy().sub(new Vector2d(f, 0).rotate(totalRotation));

			gc.strokeOval(fPointR.x - 5, fPointR.y - 5, 10, 10);
			gc.strokeOval(fPointL.x - 5, fPointL.y - 5, 10, 10);
		}
	}

	public double xVal(double y) {
		double L = (Math.hypot(d/2, focalLength));
		double x = (n * L - focalLength - Math.sqrt(Math.pow(L, 2) - 2*n*focalLength*L + Math.pow(n*focalLength, 2) + Math.pow(n * y, 2) - Math.pow(y, 2) / (Math.pow(n, 2) - 1)));
		return x;
	}

	public void setPoints(double d, double f) {
		clearPoints();
		int res = getResolution();
//		points.add(new Vector2d(50, 50));
//		points.add(new Vector2d(50, -50));
//		points.add(new Vector2d(-50, -50));
//		points.add(new Vector2d(-50, 50));
//		points.add(new Vector2d(50, 50));
//		points.add(new Vector2d(0,d/2));
		
		double y2 = -d/2;
		for (int i = 0; i < res; i++) {
			points.add(new Vector2d(xVal(y2), y2));
			y2 += d/ (res);
		}
		
		double y1 = d/2;
		for (int i = 0; i < res; i++) {
			
			points.add(new Vector2d(-xVal(y1), y1));
			y1 -= d / (res);
		}
		
		
		
//		points.add(new Vector2d(0,d/2));

		super.restoreRotation();
		super.createBounds();

//			points.add(pos.copy().add(vec.rotate(rightStep)));
//		}

//
//		double leftAngle = Math.acos(1 - d * d / (2 * r1 * r1)) / 2;
//		double rightAngle = Math.acos(1 - d * d / (2 * r2 * r2)) / 2;
//		double leftStep = leftAngle / quarterResolution;
//		double rightStep = rightAngle / quarterResolution;
//		double leftDist = Math.sqrt(r1 * r1 - d * d / 4);
//		double rightDist = Math.sqrt(r2 * r2 - d * d / 4);
//
//		Vector2d pos = new Vector2d(-leftDist, 0);
//		Vector2d vec = new Vector2d(r1, 0).rotate(-leftAngle);
//		for (int i = 0; i < quarterResolution * 2; i++) {
//			points.add(pos.copy().add(vec.rotate(leftStep)));
//		}
//		pos = new Vector2d(rightDist, 0);
//		vec = new Vector2d(-r2, 0).rotate(-rightAngle);
//		for (int i = 0; i < quarterResolution * 2; i++) {
//			points.add(pos.copy().add(vec.rotate(rightStep)));
//		}
//
//		points.add(points.get(0).copy()); // Close loop
//
//		super.restoreRotation();
//		super.createBounds();
//
//		this.r1 = r1;
//		this.r2 = r2;

	}
}