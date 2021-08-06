package model.optics_objects;

import java.util.Map;

import gui.Main;
import javafx.beans.property.DoubleProperty;
import util.Vector2d;

public class AngledPrism extends Lens {
	private static final long serialVersionUID = 1L;
	
	public AngledPrism(Map<String, DoubleProperty> properties) {
		super(properties);
		update();
	}

	@Override
	protected void update() {
		super.clear();
		
		points.add(Vector2d.zero());
		double angle = get("Angle")/2;
		double height = get("Height");
		Vector2d down = new Vector2d(0, height*Main.DPCM);
		Vector2d side = new Vector2d(Math.tan(Math.toRadians(angle))*height*Main.DPCM, 0);
		points.add(down.copy().add(side));
		points.add(down.copy().sub(side));
		super.init();
	}
}
