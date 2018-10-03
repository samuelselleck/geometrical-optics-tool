package optics_objects.materials;

import optics_objects.templates.Lens;
import util.Vector2d;

public class Prism extends Lens {
	private static final long serialVersionUID = 1L;

	public Prism(Vector2d origin, int edges, double r, double refractionindex) {
		super(origin, refractionindex);

		for (int i = 0; i < edges; i++) {
			double angle = 2 * Math.PI / edges * i;
			Vector2d p = new Vector2d(r * Math.cos(angle), r * Math.sin(angle));
			points.add(p);
		}
		super.createBounds();
	}
}
