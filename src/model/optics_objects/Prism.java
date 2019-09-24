package model.optics_objects;

import java.util.Map;

import gui.Main;
import javafx.beans.property.DoubleProperty;
import util.Vector2d;

public class Prism extends Lens {
	private static final long serialVersionUID = 1L;
	
	public Prism(Vector2d origin, Map<String, DoubleProperty> properties) {
		super(origin, properties);
		update();
	}

	@Override
	protected void update() {
		super.clear();
		for (int i = 0; i < get("Edges"); i++) {
			double angle = 2 * Math.PI / get("Edges") * i;
			Vector2d p = new Vector2d(
					get("Radius")*Main.DPCM* Math.cos(angle),
					get("Radius")*Main.DPCM * Math.sin(angle));
			points.add(p);
		}
		super.initObject();
	}
}
