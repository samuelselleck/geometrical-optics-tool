package model.optics_objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import gui.Main;
import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.LightRay;
import util.Utils;
import util.Vector2d;

public abstract class LightSource extends OpticsObject {
	
	private static final long serialVersionUID = 1L;
	List<LightRay> light = new ArrayList<>();
	
	Map<Integer, List<List<Vector2d>>> paths = new TreeMap<>();
	
	public LightSource(Vector2d origin, Map<String, DoubleProperty> properties) {
		super(origin, properties);
	}

	public void calculateRayPaths(List<Material> materials) {
		
		paths.clear();
		
		int min = Main.getIntProperty("minwavelength");
		int max = Main.getIntProperty("maxwavelength");
		double wavelength = get("Wavelength");
		double bandwidth = get("Bandwidth");
		int maxRayCount = Main.getIntProperty("maxcolorraycount");
		
		List<Integer> wavelengths = new ArrayList<>();
		
		double step = (max - min)/maxRayCount;
		
		for(double w = wavelength; w <= max; w+= step) {	
			if(w <= wavelength + bandwidth/2) {
				wavelengths.add((int)w);
			}
		}
		
		for(double w = wavelength - step; w >= min; w -= step) {	
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
	public void draw(GraphicsContext gc, boolean selected) {
		
		for(Map.Entry<Integer, List<List<Vector2d>>> entry : paths.entrySet()) {
			
			int[] rgb = Utils.waveLengthToRGB(entry.getKey());	
			gc.setStroke(new Color(rgb[0]/255.0, rgb[1]/255.0, rgb[2]/255.0, 0.7));
			
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
			gc.fillOval(origin.x - Main.DPCM, origin.y - Main.DPCM, 2*Main.DPCM, 2*Main.DPCM);
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
		return pos.distSquared(this.getOrigin()) < Main.DPCM*Main.DPCM;
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
}
