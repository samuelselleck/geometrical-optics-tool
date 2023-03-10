package model.optics_objects;

import java.util.Map;

import gui.Main;
import javafx.beans.property.DoubleProperty;
import util.Vector2d;

public class RectangleWall extends Wall {
	private static final long serialVersionUID = 1L;
	
	public RectangleWall(Map<String, DoubleProperty> properties) {
		super(properties);
		update();
	}
	
	@Override
	protected void update()  {
		super.clear();
		double halfWidth = get("Width")*Main.DPCM/2;
		double halfHeight = get("Height")*Main.DPCM/2;
		
		points.add(new Vector2d(-halfWidth,-halfHeight));
		points.add(new Vector2d(halfWidth, -halfHeight));
		points.add(new Vector2d(halfWidth, halfHeight));
		points.add(new Vector2d(-halfWidth, halfHeight));
		super.init();
	}
}
