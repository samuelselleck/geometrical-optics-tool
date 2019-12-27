package model.optics_objects;

import java.awt.Graphics2D;
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
	private List<LightRay> light = new ArrayList<>();
	
	private transient Map<Integer, List<LightPathNode>> paths;
	
	public LightSource(Map<String, DoubleProperty> properties) {
		super(properties);
	}

	public void calculateRayPaths(List<Apparatus> apparatuses) {
		
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
				LightPathNode path = l.calculatePath(apparatuses, w);
				pathsAtWavelength.add(path);
			});
			paths.put(w, pathsAtWavelength);
		});
	}

	@Override
	public void draw(GraphicsContext gc, boolean selected) {
		
		for(Map.Entry<Integer, List<LightPathNode>> entry : paths.entrySet()) {
			
			int[] rgb = Utils.waveLengthToRGB(entry.getKey());	
			Color color = new Color(rgb[0]/255.0, rgb[1]/255.0, rgb[2]/255.0, 1);
			
			for(LightPathNode path : entry.getValue()) {
				gc.beginPath();
				gc.moveTo(path.getOrigin().x, path.getOrigin().y);
				path.stroke(gc, color);
			}
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
	public void draw(Graphics2D g, boolean selected) {
		g.setColor(java.awt.Color.RED);
		
		for(Map.Entry<Integer, List<LightPathNode>> entry : paths.entrySet()) {
			
			int[] rgb = Utils.waveLengthToRGB(entry.getKey());	
			java.awt.Color color = new java.awt.Color(rgb[0], rgb[1], rgb[2], 255);
			
			for(LightPathNode path : entry.getValue()) {
				Vector2d curr = getOrigin();
				int currX = (int)Math.round(curr.x);
				int currY = (int)Math.round(curr.y);
				path.stroke(g, color, currX, currY);
			}
		}
		
		if(selected) {
			java.awt.Color c = new java.awt.Color(255, 255, 255, 90);
			g.setColor(c);
			Vector2d origin = getOrigin();
			int x = (int)Math.round(origin.x - Main.DPCM);
			int y = (int)Math.round(origin.y - Main.DPCM);
			int w = (int)Math.round(2*Main.DPCM);
			g.fillOval(x, y, w, w);
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
