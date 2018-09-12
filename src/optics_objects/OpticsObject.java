package optics_objects;

import javafx.scene.canvas.GraphicsContext;
import util.Vector2d;

public interface OpticsObject {
	void draw(GraphicsContext gc);

	Vector2d getOrigin();

	void setOrigin(Vector2d vec);
	void rotate(double angle);
}
