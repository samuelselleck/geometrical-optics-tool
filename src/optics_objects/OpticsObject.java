package optics_objects;

import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import util.Vector2d;

public abstract class OpticsObject implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Vector2d origin;
	protected double totalRotation;
	
	public OpticsObject(Vector2d origin) {
		this.origin = origin;
	}

	public abstract void draw(GraphicsContext gc);

	public Vector2d getOrigin() {
		return origin;
	}

	public void setOrigin(Vector2d vec) {
		this.origin.setTo(vec);
	}
	
	protected abstract void rotateOp(double angle);
	
	public void rotate(double angle) {
		totalRotation += angle;
		rotateOp(angle);
	}
}