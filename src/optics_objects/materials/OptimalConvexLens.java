package optics_objects.materials;


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
		double x = (n * L - focalLength + Math.sqrt(Math.pow(L, 2) - 2*n*focalLength*L + Math.pow(n*focalLength, 2) + Math.pow(n * y, 2) - Math.pow(y, 2)) / (Math.pow(n, 2) - 1));
		return x;
	}

	public void setPoints(double d, double f) {
		clearPoints();
		int res = getResolution();

		double fel = xVal(-d/2);
		double y1 = 0;
		for (int i = 0; i < res; i++) {
			points.add(new Vector2d(xVal(y1) - fel, y1));
			y1 -= d/ (2 * res);
		}
		
		
		int temp = points.size() - 1;
		for (int i = 0; i < temp; i++) {
			points.add(new Vector2d(-points.get(temp - i).x ,points.get(temp - i).y));
		}
		
		int temp2 = points.size() - 1;
		for (int i = 0; i < temp2; i++) {
			points.add(new Vector2d(-points.get(i).x ,-points.get(i).y));
		}
		points.add(points.get(0).copy());

		


		super.restoreRotation();
		super.createBounds();



	}
}