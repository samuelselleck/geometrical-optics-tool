package model;

import java.util.List;

import model.optics_objects.Material;
import util.Vector2d;

public class RayIntersectionData {
	public Material material;
	public double distance = Double.MAX_VALUE;
	public Vector2d surface, ray;
	public Vector2d position;
	
	public List<Vector2d> getScatteredLight(int wavelength) {
		material.onHit(position, surface);
		List<Vector2d> scatterDirections = material.getScatteredLight(ray, surface, wavelength);
		return scatterDirections;
	}
}
