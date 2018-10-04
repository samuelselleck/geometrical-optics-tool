package optics_objects.templates;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import util.Vector2d;

public abstract class Lens extends Material {
	
	private static final long serialVersionUID = 1L;
	public static final int LENSRESOLUTION = 500;
	private double refractionindex;
	
	public Lens(Vector2d origin, double refractionindex, boolean fixedPosition) {
		super(origin, fixedPosition);
		this.refractionindex = refractionindex;
	}
	
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
	
	public void createBounds() {
		points.add(points.get(0).copy()); //Close loop
		super.createBounds();
	}
	
	public void draw(GraphicsContext gc) {
		gc.beginPath();
		Vector2d p = getPoint(0);
		gc.moveTo(p.x, p.y);
		for (int i = 1; i < getPointCount(); i++) {
			p = getPoint(i);
			gc.lineTo(p.x, p.y);
		}
		gc.closePath();
		gc.setStroke(Paint.valueOf("WHITE"));
		gc.stroke();
	}
}
