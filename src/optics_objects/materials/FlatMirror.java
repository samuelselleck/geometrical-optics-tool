package optics_objects.materials;

import optics_objects.templates.Mirror;
import util.Vector2d;

public class FlatMirror extends Mirror {
	private static final long serialVersionUID = 1L;

	public FlatMirror(Vector2d origin, double diameter) {
		super(origin);
		Vector2d unit = new Vector2d(1, 0).mult(diameter / 2);
		points.add(unit);
		points.add(unit.copy().mult(-1));
		super.createBounds();
	}
}
