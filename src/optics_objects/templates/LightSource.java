package optics_objects.templates;

import java.util.ArrayList;
import java.util.List;

import gui.Main;
import javafx.scene.canvas.GraphicsContext;
import optics_logic.LightRay;
import optics_logic.OpticsSettings;
import util.Vector2d;

public abstract class LightSource extends OpticsObject {
	private static final long serialVersionUID = 1L;
	public static final int LIGHTWAVEMAX = 700;
	public static final int LIGHTWAVEMIN = 380;
	public static final int DEFAULTWAVE = 700;
	ArrayList<LightRay> light;
	

	public LightSource(Vector2d origin, int rayCount, boolean fixedPosition) {
		super(origin, fixedPosition);
		light = new ArrayList<>();
	}

	public void calculateRayPaths(List<Material> materials, int wavelength) {
		light.stream().forEach(l -> {
			l.calculatePath(materials, wavelength);
		});
	}

	@Override
	public void draw(GraphicsContext gc, OpticsSettings settings) {
		for(LightRay l : light) {
			l.draw(gc, settings.drawOnlyHitting());
		}
	}
	
	@Override
	public void rotateOp(double angle) {
		for(LightRay l : light) {
			l.rotate(angle);
		}
	}
	
	public boolean withinTouchHitBox(Vector2d pos) {
		return pos.distSquared(this.getOrigin()) < (Main.HEIGHT/10)*(Main.HEIGHT/10);
	}
	
	public void addLightRay(Vector2d offset, Vector2d ray) {
			light.add(new LightRay(origin, offset, ray));
	}
	
	public void addLightRay(Vector2d ray) {
		addLightRay(new Vector2d(0, 0), ray);
	}
}
