package optics_objects.templates;

import java.io.Serializable;

import gui.Main;
import javafx.scene.canvas.GraphicsContext;
import optics_logic.OpticsSettings;
import util.Vector2d;

public abstract class OpticsObject implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Vector2d origin;
	protected double totalRotation;
	
	public OpticsObject(Vector2d origin) {
		this.origin = origin;
	}

	public abstract void draw(GraphicsContext gc, OpticsSettings settings);

	public Vector2d getOrigin() {
		return origin;
	}

	public void setOrigin(Vector2d vec) {
		setOrigin(vec.x, vec.y);
	}
	
	public void setOrigin(double x, double y) {
		this.origin.setTo(x, y);
	}
	
	protected abstract void rotateOp(double angle);
	public abstract boolean withinTouchHitBox(Vector2d pos);
	
	public void rotate(double angle) {
		totalRotation += angle;
		rotateOp(angle);
	}
	
	public static int getResolution() {
		return Integer.parseInt(Main.properties.getProperty("opticsobjectresolution"));
	}
}