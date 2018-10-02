package optics_objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import util.Vector2d;

public abstract class Mirror extends Material {
	public Mirror(Vector2d origin) {
		super(origin);
	}

	private static final long serialVersionUID = 1L;
	public static final int MIRRORRESOLUTION = 500;
	
	public double getAngle(double angleIn, double wavelength, boolean dir) {
		return Math.PI - angleIn;
	}
	
	public void draw(GraphicsContext gc) {
		gc.setStroke(Paint.valueOf("WHITE"));
		Vector2d p1;
		Vector2d p2 = getPoint(0);
		for (int i = 0; i < getPointCount(); i++) {
			p1 = p2;
			p2 = getPoint(i);
			gc.strokeLine(p1.x, p1.y, p2.x, p2.y);
		}
	}
}
