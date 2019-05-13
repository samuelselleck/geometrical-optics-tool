package optics_logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gui.OpticsController;
import optics_objects.lights.DiffractionGratingLightSource;
import optics_objects.materials.DiffractionGrating;
import optics_objects.templates.LightSource;
import optics_objects.templates.Material;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class OpticsModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	List<Material> materials;
	List<LightSource> lights;
	
	OpticsSettings settings;

	public OpticsModel(OpticsSettings settings) {
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
			if(newOpticsObject instanceof DiffractionGrating){
				lights.add(((DiffractionGrating) newOpticsObject).getLightSource());
			}
		} else {
			lights.add((LightSource)newOpticsObject);
		}
	}

	public void remove(OpticsObject draging) {
		if(draging instanceof Material) {
			materials.remove(draging);
			if(draging instanceof DiffractionGrating){
				lights.remove(((DiffractionGrating) draging).getLightSource());
			}
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
		List<LightSource> remove = new ArrayList<>();
		for(LightSource l : lights){
			if(l instanceof DiffractionGratingLightSource == false){
				remove.add(l);
			}
		}
		for (LightSource l : remove) {
			lights.remove(l);
		}
	}

	public void clearMaterials() {
		List<Material> remove = new ArrayList<>();
		for(Material m : materials){
			if(m instanceof DiffractionGrating == true){
				lights.remove(((DiffractionGrating) m).getLightSource());
			}
			remove.add(m);
		}
		for (Material m : remove) {
			materials.remove(m);
		}
	}

	public OpticsSettings getSettings() {
		return settings;
	}
}
