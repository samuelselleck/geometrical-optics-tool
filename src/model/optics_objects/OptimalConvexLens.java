package model.optics_objects;

import java.util.Map;

import gui.Main;
import javafx.beans.property.DoubleProperty;
import util.Vector2d;

//Made by students in Optics and Waves 2019
public class OptimalConvexLens extends Lens {
	private static final long serialVersionUID = 1L;
	
	public OptimalConvexLens(Map<String, DoubleProperty> properties) {
		super(properties);
		update();
	}

	@Override
	protected void update() {
		super.clear();
		
		int res = Main.getIntProperty("opticsobjectresolution")/4;

		double fel = xVal(-get("Diameter")*Main.DPCM/2);
		double y1 = 0;
		for (int i = 0; i < res; i++) {
			points.add(new Vector2d(xVal(y1) - fel, y1));
			y1 -= get("Diameter")*Main.DPCM/ (2 * res);
		}
		
		
		int temp = points.size() - 1;
		for (int i = 0; i < temp; i++) {
			points.add(new Vector2d(-points.get(temp - i).x ,points.get(temp - i).y));
		}
		
		int temp2 = points.size() - 1;
		for (int i = 0; i < temp2; i++) {
			points.add(new Vector2d(-points.get(i).x ,-points.get(i).y));
		}
		points.add(points.get(0).copy());
		super.init();
	}
	
	public double xVal(double y) {
		
		
		double n = MATERIALS.get((int)get("Material Index"))
				.refraction(get("Wavelength Optimum"), get("Refraction Multiplier"));
		
		double L = (Math.hypot(get("Diameter")*Main.DPCM/2, get("Focal Length")*Main.DPCM));
		double x = (n * L - get("Focal Length")*Main.DPCM + 
				Math.sqrt(Math.pow(L, 2) -
				2*n*get("Focal Length")*Main.DPCM*L +
				Math.pow(n*get("Focal Length")*Main.DPCM, 2) +
				Math.pow(n * y, 2) - Math.pow(y, 2)) / (Math.pow(n, 2) - 1));
		
		return x;
	}

}
