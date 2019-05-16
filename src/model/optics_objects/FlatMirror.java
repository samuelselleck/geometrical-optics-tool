package model.optics_objects;

import util.Vector2d;

public class FlatMirror extends Mirror {
	private static final long serialVersionUID = 1L;
	
	public FlatMirror(Vector2d origin, double diameter) {
		super(origin);
		super.addProperty("Diameter", diameter);
		update();
	}

	@Override
	protected void update() {
		super.clear();
		Vector2d unit = new Vector2d(1, 0).mult(get("Diameter") / 2);
		points.add(unit);
		points.add(unit.copy().mult(-1));
		super.initObject();
	}
}
