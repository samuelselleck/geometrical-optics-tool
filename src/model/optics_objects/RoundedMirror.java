package model.optics_objects;

import java.util.Map;

import gui.Main;
import javafx.beans.property.DoubleProperty;
import util.Vector2d;

public class RoundedMirror extends Mirror {
	private static final long serialVersionUID = 1L;

	public RoundedMirror(Vector2d origin, Map<String, DoubleProperty> editableProperties) {
		super(origin, editableProperties);
		update();
	}
	
	@Override
	protected void update() {
		super.clear();
		int resolution = Main.getIntProperty("opticsobjectresolution");
		
		for(int i = 0; i <= resolution; i++) {
			double x = (2.0*i/resolution - 1);
			double y = x*x;
			points.add(new Vector2d(x*get("Diameter")/2, get("Depth")*(y - 1.0/2)));
		}
		super.initObject();
	}
}
