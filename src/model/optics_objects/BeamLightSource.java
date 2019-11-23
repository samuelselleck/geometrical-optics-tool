package model.optics_objects;

import java.util.Map;

import gui.Main;
import javafx.beans.property.DoubleProperty;
import util.Vector2d;

public class BeamLightSource extends LightSource {
	private static final long serialVersionUID = 1L;
	
	public BeamLightSource(Map<String, DoubleProperty> properties) {
		super(properties);
		update();
	}

	@Override
	protected void update() {
		super.clear();
		Vector2d posVec = new Vector2d(0, 1).mult(get("Diameter")*Main.DPCM).div(get("LightRays"));
		Vector2d rayVec = posVec.copy().normalize().rotate(-Math.PI / 2);
		for (int i = (int) (get("LightRays") / 2); i >= -get("LightRays") / 2; i--) {
			super.addLightRay(posVec.copy().mult(i), rayVec.copy());
		}
		super.init();
	}
}
