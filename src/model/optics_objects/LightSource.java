package model.optics_objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import gui.Main;
import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.GlobalOpticsSettings;
import model.LightRay;
import util.Utils;
import util.Vector2d;

public abstract class LightSource extends OpticsObject {
	
	private static final long serialVersionUID = 1L;
	List<LightRay> light = new ArrayList<>();
	
	//Colors, Paths, Positions
	Map<Integer, List<List<Vector2d>>> paths = new TreeMap<>();
	
	public LightSource(Vector2d origin, Map<String, DoubleProperty> editableProperties) {
		super(origin, editableProperties);
	}

	public void calculateRayPaths(List<Material> materials) {
		paths.clear();
		
		double wavelength = get("Wavelength");
		double bandwidth = get("Bandwidth");
		int maxRayCount = maxColorRayCount();
		List<Integer> wavelengths = new ArrayList<>();
		
		double step = (lightWaveMax() - lightWaveMin())/maxRayCount;
		
		for(double w = wavelength; w <= lightWaveMax(); w+= step) {	
			if(w <= wavelength + bandwidth/2) {
				wavelengths.add((int)w);
			}
		}
		
		for(double w = wavelength - step; w >= lightWaveMin(); w -= step) {	
			if(w >= wavelength - bandwidth/2) {
				wavelengths.add((int)w);
			}
		}
		
		wavelengths.stream().forEach(w -> {	
			List<List<Vector2d>> pathsAtWavelength = new ArrayList<>();
			light.stream().forEach(l -> {
				List<Vector2d> path = l.calculatePath(materials, w);
				pathsAtWavelength.add(path);
			});
			paths.put(w, pathsAtWavelength);
		});
	}

	@Override
	public void draw(GraphicsContext gc, GlobalOpticsSettings settings, boolean selected) {
		
		for(Map.Entry<Integer, List<List<Vector2d>>> entry : paths.entrySet()) {
			int[] rgb = Utils.waveLengthToRGB(entry.getKey());	
			gc.setStroke(new Color(rgb[0]/255.0, rgb[1]/255.0, rgb[2]/255.0, 1));
			
			gc.beginPath();
			for(List<Vector2d> path : entry.getValue()) {
				gc.moveTo(path.get(0).x, path.get(0).y);
				for(int i = 1; i < path.size(); i++) {
					Vector2d pos = path.get(i);
					gc.lineTo(pos.x, pos.y);
				}
			}	
			gc.stroke();
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
	
	@Override
	public boolean withinTouchHitBox(Vector2d pos) {
		return pos.distSquared(this.getOrigin()) < (Main.HEIGHT/10)*(Main.HEIGHT/10);
	}
	
	protected void addLightRay(Vector2d offset, Vector2d ray) {
			light.add(new LightRay(origin, offset, ray));
	}
	
	protected void addLightRay(Vector2d ray) {
		addLightRay(new Vector2d(0, 0), ray);
	}
	
	@Override
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
	
	public static int maxColorRayCount() {
		return Integer.parseInt(Main.properties.getProperty("maxcolorraycount"));
	}
}
