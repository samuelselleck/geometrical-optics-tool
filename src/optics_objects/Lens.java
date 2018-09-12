package optics_objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import util.Vector2d;

public abstract class Lens extends Material {
	public static final int LENSRESOLUTION = 500;
	protected double refractionindex;

	public double getAngle(double angleIn, double wavelength, boolean into) {
		double angleOut;
		
		double var;
		if(LightSource.WHITE) var = Math.pow((refractionindex - 1)*
				(LightSource.LIGHTWAVEMAX/wavelength - 1), 1.5);
		else var = 0;
		
		double currRefrac = refractionindex + var;
		double invrefrac = 1/currRefrac;
		if (into) {
			// Från luft till lens:
			angleOut = Math.asin(invrefrac * Math.sin(angleIn));
		} else if (Math.abs(angleIn) <= Math.asin(invrefrac)) {
			// Från lens till luft:
			angleOut = Math.asin(currRefrac * Math.sin(angleIn));
		} else {
			// Totalreflektion:
			angleOut = Math.PI - angleIn;
		}
		return angleOut;
	}
	
	public void draw(GraphicsContext gc) {
		gc.setStroke(Paint.valueOf("WHITE"));
		Vector2d p1;
		Vector2d p2 = getPoint(0);
		for (int i = 0; i <= getPointCount(); i++) {
			p1 = p2;
			p2 = getPoint(i);
			gc.strokeLine(p1.x, p1.y, p2.x, p2.y);
		}
	}
}
