package optics_objects.materials;

import optics_objects.templates.Lens;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class CrystalBall extends Lens {
	private static final long serialVersionUID = 1L;
	
	public CrystalBall(Vector2d origin, double r, double refractionindex) {
		super(origin, refractionindex);
		super.addProperty("Radius", r);
		update();
	}

	@Override
	protected void update() {
		super.clear();
		for (int i = 0; i < OpticsObject.getResolution(); i++) {
			double angle = 2 * Math.PI / OpticsObject.getResolution() * i;
			Vector2d p = new Vector2d(get("Radius")* Math.cos(angle), get("Radius") * Math.sin(angle));
			points.add(p);
		}
		super.initObject();
	}

}
