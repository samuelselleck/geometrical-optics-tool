package optics_objects;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import optics_logic.LightRay;
import util.Vector2d;

public abstract class LightSource extends OpticsObject {
	private static final long serialVersionUID = 1L;
	public static final int LIGHTWAVEMAX = 700;
	public static final int LIGHTWAVEMIN = 380;
	public static final int DEFAULTWAVE = 700;
	public static boolean WHITE = false;
	ArrayList<LightRay> light;
	protected int rayCount;
	

	public LightSource(Vector2d origin, int rayCount) {
		super(origin);
		light = new ArrayList<>();
		this.rayCount = rayCount;
	}

	public void calculateRayPaths(List<Material> materials, int wavelength) {
		light.parallelStream().forEach(l -> {
			l.calculatePath(materials, wavelength);
		});
	}

	public void draw(GraphicsContext gc) {
		for(LightRay l : light) {
			l.draw(gc);
			//Fix: make every color one path (somehow)
		}
	}

	public Object getRays() {
		return light;
	}
	
	public void rotateOp(double angle) {
		for(LightRay l : light) {
			l.rotate(angle);
		}
	}
	
	public void createRays() {
		light.clear();
		create();
		rotateOp(totalRotation);
	}
	
	protected abstract void create();
	
	public void addLightRay(Vector2d offset, Vector2d ray) {
			light.add(new LightRay(origin, offset, ray, DEFAULTWAVE));
	}
	
	public void addLightRay(Vector2d ray) {
		addLightRay(new Vector2d(0, 0), ray);
	}
}
