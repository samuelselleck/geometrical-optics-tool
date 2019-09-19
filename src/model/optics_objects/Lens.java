package model.optics_objects;

import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import model.GlobalOpticsSettings;
import util.Vector2d;

public abstract class Lens extends Material {
	private static final long serialVersionUID = 1L;
	
	public enum SellmeierCoefficients {
		
		GLASS (1.03961212, 0.231792344, 1.01046945, 6.00069867e-3, 2.00179144e-2, 103.560653),
		SAPHIRE (1.43134930, 0.65054713, 5.3414021, 5.2799261e-3, 1.42382647e-2, 325.017834);
		
		private double b1, b2, b3, c1, c2, c3;
		
		SellmeierCoefficients(double b1, double b2, double b3, double c1, double c2, double c3) {
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
			double minRefrac = refraction(LightSource.lightWaveMin());
			double refrac = minRefrac + offsetMultiplier*(refraction(wavelength) - minRefrac);
			return refrac;
		}
	}

	public Lens(Vector2d origin, Map<String, DoubleProperty> editableProperties) {
		super(origin, editableProperties);
	}
	
	@Override
	public double getAngle(double angleIn, double wavelength, boolean into) {
		double angleOut;
		
		double currRefrac = SellmeierCoefficients.values()[(int)get("Material Index")]
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
	public void draw(GraphicsContext gc, GlobalOpticsSettings settings, boolean selected) {
		if(selected) {
			Stop[] stops = new Stop[] { new Stop(0, new Color(1, 1, 1, 0.35)), new Stop(1, new Color(1, 1, 1, 0.7))};
	        LinearGradient fillGradient = new LinearGradient(0, 0.5, 1, 0, true, CycleMethod.NO_CYCLE, stops);
			gc.setFill(fillGradient);
		} else {
			Stop[] stops = new Stop[] { new Stop(0, new Color(1, 1, 1, 0.15)), new Stop(1, new Color(1, 1, 1, 0.5))};
			LinearGradient fillGradient = new LinearGradient(0, 0.5, 1, 0, true, CycleMethod.NO_CYCLE, stops);
			gc.setFill(fillGradient);
		}
		gc.beginPath();
		for (int i = 0; i < getPointCount(); i++) {
			Vector2d p = getPoint(i);
			gc.lineTo(p.x, p.y);
		}
		gc.fill();
	}
}
