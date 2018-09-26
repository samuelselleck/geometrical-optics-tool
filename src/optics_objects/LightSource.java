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
	

	public LightSource(Vector2d origin) {
		light = new ArrayList<>();
		this.origin = origin;
	}

	public void calculateRayPaths(List<Material> materials) {
		light.parallelStream().forEach(l -> {
			l.calculatePath(materials);
		});
	}

	public void draw(GraphicsContext gc) {
		for(LightRay l : light) {
			l.draw(gc);
		}
	}

	public Object getRays() {
		return light;
	}
	
	public void rotate(double angle) {
		for(LightRay l : light) {
			l.rotate(angle);
		}
	}
	
	public void addLightRay(Vector2d offset, Vector2d ray) {
		if(WHITE) {
			for(int i = 5; i >= 0; i--) {
				light.add(new LightRay(origin, offset.copy(), ray.copy(),
						(LIGHTWAVEMAX - LIGHTWAVEMIN)*i/5 + LIGHTWAVEMIN));
			}
		} else {
			light.add(new LightRay(origin, offset, ray, DEFAULTWAVE));
		}
	}
	
	public void addLightRay(Vector2d ray) {
		addLightRay(new Vector2d(0, 0), ray);
	}
}
