package optics_objects.templates;

import util.Vector2d;

public abstract class Lens extends Material {
	
	private static final long serialVersionUID = 1L;
	private double refractionindex;
	
	public Lens(Vector2d origin, double refractionindex, boolean fixedPosition) {
		super(origin, fixedPosition);
		this.refractionindex = refractionindex;
	}
	
	public void setRefractionIndex(double index) {
		this.refractionindex = index;
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
		points.add(points.get(0).copy()); //Close loop
		super.createBounds();
	}
}
