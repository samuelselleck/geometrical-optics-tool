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
	
	//Optics Objects
	private List<Material> materials;
	private List<LightSource> lights;
	
	private ModelMetadata metadata;
	
	public OpticsModel() {
		init();
	}
	
	public void init() {
		materials = new ArrayList<>();
		lights = new ArrayList<>();
		metadata = new ModelMetadata();
	}
	
	public OpticsObject getOpticsObjectAt(double x, double y) {
		OpticsObject obj = null;
		
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
					obj = o;
				}
			}
		}
		return obj;
	}
	
	public void addOpticsObject(OpticsObject newOpticsObject) {
		if(newOpticsObject == null) return;
		
		if(newOpticsObject instanceof Material) {
			materials.add((Material)newOpticsObject);
		} else {
			lights.add((LightSource)newOpticsObject);
		}
	}

	public void remove(OpticsObject obj) {
		if(obj.isProp()) return;
		
		if(obj instanceof Material) {
			materials.remove(obj);
		} else {
			lights.remove(obj);
		}
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public List<LightSource> getLights() {
		return lights;
	}
	
	public void clear() {
		clearLights();
		clearMaterials();
	}

	public void clearLights() {
		lights.removeIf(l -> !l.isProp());
	}

	public void clearMaterials() {
		materials.removeIf(m -> !m.isProp());
	}

	public ModelMetadata getMetadata() {
		return metadata;
	}
}
