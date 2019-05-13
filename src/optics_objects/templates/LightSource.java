package optics_objects.templates;

import java.util.ArrayList;
import java.util.List;

import gui.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import optics_logic.LightRay;
import optics_logic.GlobalOpticsSettings;
import util.Vector2d;

public abstract class LightSource extends OpticsObject {
	private static final long serialVersionUID = 1L;
	List<LightRay> light = new ArrayList<>();

	public LightSource(Vector2d origin, int rayCount) {
		super(origin);
		super.addProperty("LightRays", rayCount);
	}

	public void calculateRayPaths(List<Material> materials, int wavelength) {
		light.stream().forEach(l -> {
			l.calculatePath(materials, wavelength);
		});
	}

	@Override
	public void draw(GraphicsContext gc, GlobalOpticsSettings settings, boolean selected) {
		for(LightRay l : light) {
			l.draw(gc, settings.drawOnlyHitting());
		}
		if(selected) {
			gc.setFill(new Color(1, 1, 1, 0.3));
			gc.fillOval(origin.x - Main.WIDTH/30, origin.y - Main.WIDTH/30, Main.WIDTH/15, Main.WIDTH/15);
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
	
	protected void addLightRay(Vector2d offset, Vector2d ray) {
			light.add(new LightRay(origin, offset, ray));
	}
	
	protected void addLightRay(Vector2d ray) {
		addLightRay(new Vector2d(0, 0), ray);
	}
	
	protected void clear() {
		if(light == null) {
			light = new ArrayList<>();
		} else {
			light.clear();
		}
	}
	
	public static int lightWaveMax() {
		return Integer.parseInt(Main.properties.getProperty("maxwavelength"));
	}
	
	public static int lightWaveMin() {
		return Integer.parseInt(Main.properties.getProperty("minwavelength"));
	}
	
	public static int lightWaveDefault() {
		return Integer.parseInt(Main.properties.getProperty("defaultwavelength"));
	}
	
	public static int colorModeRayCount() {
		return Integer.parseInt(Main.properties.getProperty("colormoderaycount"));
	}
}
