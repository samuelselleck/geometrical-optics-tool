package model.optics_objects;

import java.util.Map;

import javafx.beans.property.DoubleProperty;
import util.Vector2d;

public class ConeLightSource extends LightSource {
	private static final long serialVersionUID = 1L;
	
	public ConeLightSource(Map<String, DoubleProperty> properties) {
		super(properties);
	}

	@Override
	protected void update() {
		super.clear();
		double deltaAngle = get("Cone Angle")/180*Math.PI/(get("LightRays") - 1);
		Vector2d ray = new Vector2d(1, 0).rotate(-get("Cone Angle")/180*Math.PI/2);
		for(int i = 0; i < get("LightRays"); i++) {
			super.addLightRay(ray.copy());
			ray.rotate(deltaAngle);
		}
		super.init();
	}
}
