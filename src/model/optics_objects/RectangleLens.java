package model.optics_objects;

import java.util.Map;

import javafx.beans.property.DoubleProperty;
import util.Vector2d;

public class RectangleLens extends Lens {
	private static final long serialVersionUID = 1L;
	
	public RectangleLens(Vector2d origin, Map<String, DoubleProperty> editableProperties) {
		super(origin, editableProperties);
		update();
	}
	
	@Override
	protected void update()  {
		super.clear();
		points.add(new Vector2d(-get("Width")/2,-get("Height")/2));
		points.add(new Vector2d(get("Width")/2, -get("Height")/2));
		points.add(new Vector2d(get("Width")/2, get("Height")/2));
		points.add(new Vector2d(-get("Width")/2, get("Height")/2));
		super.initObject();
	}
}
