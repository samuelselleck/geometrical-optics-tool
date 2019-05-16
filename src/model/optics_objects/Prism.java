package model.optics_objects;

import util.Vector2d;

public class Prism extends Lens {
	private static final long serialVersionUID = 1L;
	
	public Prism(Vector2d origin, int edges, double r, double refractionindex) {
		super(origin, refractionindex);
		super.addProperty("Edges", edges);
		super.addProperty("Radius", r);
		update();
	}

	@Override
	protected void update() {
		super.clear();
		for (int i = 0; i < get("Edges"); i++) {
			double angle = 2 * Math.PI / get("Edges") * i;
			Vector2d p = new Vector2d(get("Radius")* Math.cos(angle), get("Radius") * Math.sin(angle));
			points.add(p);
		}
		super.initObject();
	}
}
