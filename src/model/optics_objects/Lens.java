package model.optics_objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import model.GlobalOpticsSettings;
import util.Vector2d;

public abstract class Lens extends Material {
	private static final long serialVersionUID = 1L;
	
	public Lens(Vector2d origin, double refractionindex) {
		super(origin);
		super.addProperty("Refractionindex", refractionindex);
	}
	
	@Override
	public double getAngle(double angleIn, double wavelength, boolean into) {
		double angleOut;
		
		double var = Math.pow((get("Refractionindex") - 1)*
				(LightSource.lightWaveMax()/wavelength - 1), 1.5);
		
		double currRefrac = get("Refractionindex") + var;
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
