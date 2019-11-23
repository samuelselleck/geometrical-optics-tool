package model.optics_objects;

import java.util.Map;

import gui.Main;
import javafx.beans.property.DoubleProperty;
import util.Vector2d;

public class CrystalBall extends Lens {
	private static final long serialVersionUID = 1L;
	
	public CrystalBall(Map<String, DoubleProperty> properties) {
		super(properties);
		update();
	}

	@Override
	protected void update() {
		super.clear();
		int resolution = Main.getIntProperty("opticsobjectresolution");
		
		for (int i = 0; i < resolution; i++) {
			double angle = 2 * Math.PI / resolution * i;
			Vector2d p = new Vector2d(
					get("Radius")*Main.DPCM * Math.cos(angle),
					get("Radius")*Main.DPCM * Math.sin(angle));
			points.add(p);
		}
		super.init();
	}

}
