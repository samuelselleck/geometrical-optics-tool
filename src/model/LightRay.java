
package model;

import java.io.Serializable;
import java.util.List;

import model.optics_objects.Apparatus;
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
	
	public LightPathNode calculatePath(List<Apparatus> apparatuses, int wavelength) {
		LightPathNode rootNode = new LightPathNode(getPos(), 1);
		rootNode.develop(ray, apparatuses, wavelength);
		return rootNode;
	}

	private Vector2d getPos() {
		return origin.copy().add(offset);
	}

	public void rotate(double angle) {
		offset.rotateDegrees(angle);
		ray.rotateDegrees(angle);
	}

}
