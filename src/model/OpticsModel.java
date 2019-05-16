package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.optics_objects.LightSource;
import model.optics_objects.Material;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class OpticsModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	List<Material> materials;
	List<LightSource> lights;
	
	GlobalOpticsSettings settings;
	
	public OpticsModel(GlobalOpticsSettings settings) {
		this.settings = settings;
		init();
	}
	
	public void init() {
		materials = new ArrayList<Material>();
		lights = new ArrayList<LightSource>();
	}
	
	public OpticsObject getOpticsObjectAt(double x, double y) {
		OpticsObject draging = null;
		Vector2d pos = new Vector2d(x, y);
		List<OpticsObject> objList = new ArrayList<>();
		objList.addAll(materials);
		objList.addAll(lights);
		
		double closestSq = Double.MAX_VALUE;
		for (OpticsObject o : objList) {		
			if(o.withinTouchHitBox(pos)) {
				double distSq = o.getOrigin().distSquared(pos);
				if (closestSq > distSq) {
					closestSq = distSq;
					draging = o;
				}
			}
		}
		return draging;
	}
	
	public void addOpticsObject(OpticsObject newOpticsObject) {
		if(newOpticsObject == null) return;
		
		if(newOpticsObject instanceof Material) {
			materials.add((Material)newOpticsObject);
		} else {
			lights.add((LightSource)newOpticsObject);
		}
	}

	public void remove(OpticsObject draging) {
		if(draging instanceof Material) {
			materials.remove(draging);
		} else {
			lights.remove(draging);
		}
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public List<LightSource> getLights() {
		return lights;
	}
	
	public void clear() {
		lights.clear();
		clearMaterials();
	}

	public void clearLights() {
		lights.clear();
	}

	public void clearMaterials() {
		materials.clear();
	}

	public GlobalOpticsSettings getSettings() {
		return settings;
	}
}
