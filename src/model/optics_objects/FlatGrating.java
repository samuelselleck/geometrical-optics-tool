package model.optics_objects;

import java.util.Map;

import gui.Main;
import javafx.beans.property.DoubleProperty;
import util.Vector2d;

public class FlatGrating extends Grating {
	private static final long serialVersionUID = 1L;

	public FlatGrating(Map<String, DoubleProperty> properties) {
		super(properties);
		update();
	}

	@Override
	protected void update() {
		super.clear();
		Vector2d unit = new Vector2d(1, 0).mult(get("Diameter")*Main.DPCM / 2);
		points.add(unit);
		points.add(unit.copy().mult(-1));
		super.init();
	}

}
