package optics_objects.templates;

import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import util.Vector2d;

public abstract class OpticsObject implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Vector2d origin;
	protected double totalRotation;
	private boolean fixedPosition;
	
	public OpticsObject(Vector2d origin, boolean fixedPosition) {
		this.origin = origin;
		this.fixedPosition = fixedPosition;
	}

	public abstract void draw(GraphicsContext gc);

	public Vector2d getOrigin() {
		return origin;
	}

	public void setOrigin(Vector2d vec) {
		setOrigin(vec.x, vec.y);
	}
	
	public void setOrigin(double x, double y) {
		if(!fixedPosition) {
			this.origin.setTo(x, y);
		}
	}
	
	protected abstract void rotateOp(double angle);
	
	public void rotate(double angle) {
		totalRotation += angle;
		rotateOp(angle);
	}
}