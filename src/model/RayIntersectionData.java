package model;

import java.util.List;

import model.optics_objects.Material;
import util.Vector2d;

public class RayIntersectionData {
	public Material material;
	public double distance = Double.MAX_VALUE;
	public Vector2d ray;
	public int surfaceId;
	public Vector2d position;
	
	public List<Vector2d> getScatteredLight(ModelMetadata metadata, int wavelength) {
		material.onHit(position, surfaceId);
		List<Vector2d> scatterDirections = material.getScatteredLight(this, metadata, wavelength);
		return scatterDirections;
	}
}
