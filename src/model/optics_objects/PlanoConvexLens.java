package model.optics_objects;

import java.util.Map;

import gui.Main;
import javafx.beans.property.DoubleProperty;
import util.Vector2d;

public class PlanoConvexLens extends Lens {
	private static final long serialVersionUID = 1L;
	
	public PlanoConvexLens(Map<String, DoubleProperty> properties) {
		super(properties);
		update();
	}

	@Override
	protected void update() {
		super.clear();
		int quarterResolution = Main.getIntProperty("opticsobjectresolution")/4;
		double d = get("Diameter")*Main.DPCM;
		double r1 = get("Radius")*Main.DPCM;
		
		double leftAngle = Math.acos(1 - d * d / (2 * r1 * r1)) / 2;
		double leftStep = leftAngle / quarterResolution;
		double leftDist = Math.sqrt(r1 * r1 - d * d / 4);

		Vector2d pos = new Vector2d(-leftDist, 0);
		Vector2d vec = new Vector2d(r1, 0).rotate(-leftAngle);
		points.add(pos.copy().add(vec));
		for (int i = 0; i < quarterResolution * 2; i++) {
			points.add(pos.copy().add(vec.rotate(leftStep)));
		}
		super.init();
	}
}
