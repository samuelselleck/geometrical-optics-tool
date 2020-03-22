package model.optics_objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import gui.Main;
import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.LightPathNode;
import model.LightRay;
import util.Utils;
import util.Vector2d;

public abstract class LightSource extends OpticsObject {
	
	private static final long serialVersionUID = 1L;
	private transient List<LightRay> light = new ArrayList<>();
	private transient Map<Integer, List<LightPathNode>> paths;
	
	public LightSource(Map<String, DoubleProperty> properties) {
		super(properties);
	}

	public void calculateRayPaths(List<Material> materials) {
		
		if(paths == null) {
			paths = new TreeMap<>();
		} else {
			paths.clear();
		}
		
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
			List<LightPathNode> pathsAtWavelength = new ArrayList<>();
			light.stream().forEach(l -> {
				LightPathNode path = l.calculatePath(materials, w);
				pathsAtWavelength.add(path);
			});
			paths.put(w, pathsAtWavelength);
		});
	}

	@Override
	public void draw(GraphicsContext gc, boolean selected) {
		
		for(Map.Entry<Integer, List<LightPathNode>> entry : paths.entrySet()) {
			
			int[] rgb = Utils.waveLengthToRGB(entry.getKey());	
			gc.setStroke(new Color(rgb[0]/255.0, rgb[1]/255.0, rgb[2]/255.0, 0.7));
			
			gc.beginPath();
			for(LightPathNode path : entry.getValue()) {
				gc.moveTo(path.getOrigin().x, path.getOrigin().y);
				path.strokeWith(gc);
			}	
			gc.stroke();
		}
		
		if(selected) {
			gc.setFill(new Color(1, 1, 1, 0.3));
			gc.fillOval(getOrigin().x - Main.DPCM, getOrigin().y - Main.DPCM, 2*Main.DPCM, 2*Main.DPCM);
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
		return pos.distSquared(getOrigin()) < Main.DPCM*Main.DPCM;
	}
	
	protected void addLightRay(Vector2d offset, Vector2d ray) {
			light.add(new LightRay(getOrigin(), offset, ray));
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
