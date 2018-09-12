package optics_objects;

import util.Vector2d;

public class FlatMirror extends Mirror {

	public FlatMirror(Vector2d origin, double diameter) {
		super();
		this.origin = origin;
		Vector2d unit = new Vector2d(1, 0).mult(diameter / 2);
		points.add(unit);
		points.add(unit.copy().mult(-1));
		super.createBounds();
	}
}
