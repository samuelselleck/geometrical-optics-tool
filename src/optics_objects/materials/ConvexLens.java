package optics_objects.materials;

import javafx.scene.canvas.GraphicsContext;
import optics_logic.OpticsSettings;
import optics_objects.templates.Lens;
import util.Vector2d;

public class ConvexLens extends Lens {
	private static final long serialVersionUID = 1L;
	private double r1;
	private double r2;

	public ConvexLens(Vector2d origin, double d, double r1, double r2, double refractionindex, boolean fixedPosition, boolean showAxis) {
		super(origin, refractionindex, fixedPosition);
		
		showOpticalAxis(showAxis);
		setPoints(d, r1, r2);
		
		this.r1 = r1;
		this.r2 = r2;
		
	}
	
	@Override
	public void draw(GraphicsContext gc, OpticsSettings settings) {
		super.draw(gc, settings);
		
		if(showOpticalAxis) {
			double f = 1/((refractionindex-1)*(1/r1+1/r2));

			Vector2d fPointR = getOrigin().copy().add(new Vector2d(f,0).rotate(totalRotation));
			Vector2d fPointL = getOrigin().copy().sub(new Vector2d(f,0).rotate(totalRotation));
			
			gc.strokeOval(fPointR.x-5, fPointR.y-5, 10, 10);
			gc.strokeOval(fPointL.x-5, fPointL.y-5, 10, 10);
		}
	}
		
	
	public void setPoints(double d, double r1, double r2) {
		clearPoints();
		int quarterResolution = getResolution() /4;
		
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
		
		points.add(points.get(0).copy()); //Close loop
		
		super.restoreRotation();
		super.createBounds();
		
		this.r1 = r1;
		this.r2 = r2;
	}

}
