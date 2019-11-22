
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.optics_objects.Material;
import util.Vector2d;

public class LightRay implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Vector2d origin;
	private Vector2d offset;
	private Vector2d ray;

	public LightRay(Vector2d origin, Vector2d offset, Vector2d ray) {
		this.origin = origin;
		this.offset = offset;
		this.ray = ray;
	}

	public LightRay(Vector2d origin, Vector2d ray) {
		this(origin, Vector2d.zero(), ray);
	}
	
	public LightPathNode calculatePath(List<Material> materials, int wavelength) {
		LightPathNode node = new LightPathNode(getPos());
		List<Vector2d> dirs = new ArrayList<>();
		dirs.add(ray);
		node.develop(dirs, materials, wavelength);
		return node;
	}

	private Vector2d getPos() {
		return origin.copy().add(offset);
	}

	public void rotate(double angle) {
		offset.rotate(angle);
		ray.rotate(angle);
	}

}
