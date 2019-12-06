package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.optics_objects.LightSource;
import model.optics_objects.Apparatus;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class OpticsModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Apparatus> apparatuses;
	private List<LightSource> lights;

	public OpticsModel() {
		init();
	}
	
	public void init() {
		apparatuses = new ArrayList<>();
		lights = new ArrayList<>();
	}
	
	public OpticsObject getOpticsObjectAt(double x, double y) {
		OpticsObject obj = null;
		
		Vector2d pos = new Vector2d(x, y);
		
		List<OpticsObject> objList = new ArrayList<>();
		objList.addAll(apparatuses);
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
		
		if(newOpticsObject instanceof Apparatus) {
			apparatuses.add((Apparatus)newOpticsObject);
		} else {
			lights.add((LightSource)newOpticsObject);
		}
	}

	public void remove(OpticsObject obj) {
		if(obj instanceof Apparatus) {
			apparatuses.remove(obj);
		} else {
			lights.remove(obj);
		}
	}

	public List<Apparatus> getApparatuses() {
		return apparatuses;
	}

	public List<LightSource> getLights() {
		return lights;
	}
	
	public void clear() {
		clearLights();
		clearApparatuses();
	}

	public void clearLights() {
		lights.clear();
	}

	public void clearApparatuses() {
		apparatuses.clear();
	}
}
