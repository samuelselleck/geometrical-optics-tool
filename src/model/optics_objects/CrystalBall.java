package model.optics_objects;

import java.util.Map;

import javafx.beans.property.DoubleProperty;
import util.Vector2d;

public class CrystalBall extends Lens {
	private static final long serialVersionUID = 1L;
	
	public CrystalBall(Vector2d origin, Map<String, DoubleProperty> editableProperties) {
		super(origin, editableProperties);
		update();
	}

	@Override
	protected void update() {
		super.clear();
		for (int i = 0; i < OpticsObject.getResolution(); i++) {
			double angle = 2 * Math.PI / OpticsObject.getResolution() * i;
			Vector2d p = new Vector2d(get("Radius")* Math.cos(angle), get("Radius") * Math.sin(angle));
			points.add(p);
		}
		super.initObject();
	}

}