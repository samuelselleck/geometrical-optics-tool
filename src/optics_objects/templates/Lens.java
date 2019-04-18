package optics_objects.templates;

import gui.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import optics_logic.OpticsSettings;
import settings.BigSettingsBox;
import util.Vector2d;

public abstract class Lens extends Material {
	
	private static final long serialVersionUID = 1L;
	protected double refractionindex;
	protected boolean showOpticalAxis = false;
	
	public Lens(Vector2d origin, double refractionindex, boolean fixedPosition) {
		super(origin, fixedPosition);
		this.refractionindex = refractionindex;
	}
	
	public void showOpticalAxis(boolean show) {
		showOpticalAxis = show;
	}
	
	public void setRefractionIndex(double index) {
		this.refractionindex = index;
	}
	
	@Override
	public void draw(GraphicsContext gc, OpticsSettings settings) {
		
		super.draw(gc, settings);
		
		if(showOpticalAxis) {
			gc.setStroke(Paint.valueOf("GRAY"));
		
			Vector2d start = this.getOrigin().copy().sub(new Vector2d(Main.WIDTH,0).rotate(totalRotation));
			Vector2d end   = this.getOrigin().copy().add(new Vector2d(Main.WIDTH,0).rotate(totalRotation));
					
			gc.strokeLine(start.x, start.y, end.x, end.y);
			
		}
		
	}
	
	@Override
	public double getAngle(double angleIn, double wavelength, boolean into) {
		double angleOut;
		
		double var = Math.pow((refractionindex - 1)*
				(LightSource.lightWaveMax()/wavelength - 1), 1.5);
		
		double currRefrac = refractionindex + var;
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
		super.createBounds();
	}
}
