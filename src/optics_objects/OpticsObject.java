package optics_objects;

import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import util.Vector2d;

public abstract class OpticsObject implements Serializable {
	private static final long serialVersionUID = 1L;
	Vector2d origin;
	
	public abstract void draw(GraphicsContext gc);

	public Vector2d getOrigin() {
		return origin;
	}

	public void setOrigin(Vector2d vec) {
		this.origin.setTo(vec);
	}
	
	public abstract void rotate(double angle);
	//TODO LightRay getResultingRay(LightRay ray);
}