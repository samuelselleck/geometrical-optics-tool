package model.optics_objects;

import java.util.Map;

import javafx.beans.property.DoubleProperty;
import util.Vector2d;

public class FlatMirror extends Mirror {
	private static final long serialVersionUID = 1L;
	
	public FlatMirror(Vector2d origin, Map<String, DoubleProperty> editableProperties) {
		super(origin, editableProperties);
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
