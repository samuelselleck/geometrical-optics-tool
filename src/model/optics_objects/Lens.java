package model.optics_objects;

import java.util.Map;

import gui.Main;
import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import util.Vector2d;

public abstract class Lens extends Material {
	private static final long serialVersionUID = 1L;
	
	public enum LensMaterial {
		
		GLASS (new double[] {0.8, 0.8, 0.9}, 1.03961212, 0.231792344, 1.01046945, 6.00069867e-3, 2.00179144e-2, 103.560653),
		SAPHIRE (new double[] {1, 0.2, 0.5}, 1.43134930, 0.65054713, 5.3414021, 5.2799261e-3, 1.42382647e-2, 325.017834);
		
		private double b1, b2, b3, c1, c2, c3;
		private double[] color;
		
		LensMaterial(double[] color, double b1, double b2, double b3, double c1, double c2, double c3) {
			this.color = color;
			this.b1 = b1;
			this.b2 = b2;
			this.b3 = b3;
			this.c1 = c1;
			this.c2 = c2;
			this.c3 = c3;
		}
		
		private double refraction(double wavelength) {
			double w = wavelength;
			return Math.sqrt(1 + 
					b1*w*w/(w*w - c1) +
					b2*w*w/(w*w - c2) +
					b3*w*w/(w*w - c3));		
		}
		
		public double refraction(double wavelength, double offsetMultiplier) {
			double minRefrac = refraction(Main.getIntProperty("minwavelength"));
			double refrac = minRefrac + offsetMultiplier*(refraction(wavelength) - minRefrac);
			return refrac;
		}
		
		public Color color(double alpha) {
			return new Color(color[0], color[1], color[2], alpha);
		}
	}

	public Lens(Vector2d origin, Map<String, DoubleProperty> editableProperties) {
		super(origin, editableProperties);
	}
	
	@Override
	public double getAngle(double angleIn, double wavelength, boolean into) {
		double angleOut;
		
		double currRefrac = LensMaterial.values()[(int)get("Material Index")]
				.refraction(wavelength, get("Refraction Multiplier"));
		
		double invrefrac = 1/currRefrac;
		if (into) {
			// Från luft till lens:
			angleOut = Math.asin(invrefrac * Math.sin(angleIn));
		} else if (Math.abs(angleIn) <= Math.asin(invrefrac)) {
			// Från lens till luft:
			angleOut = Math.asin(currRefrac * Math.sin(angleIn));
		} else {
			// Totalreflektion:
			angleOut = Math.PI - angleIn;
		}
		return angleOut;
	}
	
	@Override
	public void createBounds() {
		points.add(points.get(0).copy()); //Close loop
		super.createBounds();
	}
	
	@Override
	public void draw(GraphicsContext gc, boolean selected) {
		
		Stop[] stops;
		LensMaterial lm = LensMaterial.values()[(int)get("Material Index")];
		if(selected) {
			stops = new Stop[] { new Stop(0, lm.color(0.45)), new Stop(1, lm.color(0.8))};	        
		} else {
			stops = new Stop[] { new Stop(0, lm.color(0.25)), new Stop(1, lm.color(0.6))};
		}
		LinearGradient fillGradient = new LinearGradient(0, 0.5, 1, 0, true, CycleMethod.NO_CYCLE, stops);
		gc.setFill(fillGradient);
		
		gc.beginPath();
		for (int i = 0; i < getPointCount(); i++) {
			Vector2d p = getPoint(i);
			gc.lineTo(p.x, p.y);
		}
		gc.fill();
	}
}
