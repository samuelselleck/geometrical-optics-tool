package optics_objects;

import util.Vector2d;

public class Prism extends Lens {

	public Prism(Vector2d origin, int edges, double r, double refractionindex) {
		super();
		this.origin = origin.copy();
		this.refractionindex = refractionindex;

		for (int i = 0; i < edges; i++) {
			double angle = 2 * Math.PI / edges * i;
			Vector2d p = new Vector2d(r * Math.cos(angle), r * Math.sin(angle));
			points.add(p);
		}
		super.createBounds();
	}
}
