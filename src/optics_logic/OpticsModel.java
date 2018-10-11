package optics_logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gui.Main;
import optics_object_factories.OpticsObjectFactory;
import optics_objects.templates.LightSource;
import optics_objects.templates.Material;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class OpticsModel implements Serializable {

	private static final long serialVersionUID = 1L;
	List<Material> materials;
	List<LightSource> lights;
	
	public OpticsModel() {
		init();
	}
	
	public void init() {
		materials = new ArrayList<Material>();
		lights = new ArrayList<LightSource>();
	}
	
	public OpticsObject getOpticsObjectAt(double x, double y) {
		OpticsObject draging = null;
		Vector2d pos = new Vector2d(x, y);
		
		double closestSq = Double.MAX_VALUE;
		for (LightSource l : lights) {
			double distSq = l.getOrigin().distSquared(pos);
			if (distSq < (Main.HEIGHT / 10)*(Main.HEIGHT /10) && closestSq > distSq) {
				closestSq = distSq;
				draging = l;
			}
		}
		for (Material l : materials) {		
			if(l.withinTouchHitBox(pos)) {
				double distSq = l.getOrigin().distSquared(pos);
				if (closestSq > distSq) {
					closestSq = distSq;
					draging = l;
				}
			}
		}
		//TODO make lights move with: withinTouchHitbox
		return draging;
	}
	
	public void addOpticsObject(OpticsObjectFactory oof, double x, double y) {
		if(oof == null) return;
		
		OpticsObject newOpticsObject = oof.getOpticsObject(new Vector2d(x, y));
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
}
