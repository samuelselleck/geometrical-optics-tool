package optics_objects.materials;

import java.awt.List;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import optics_objects.templates.Lens;
import util.Vector2d;

public class Prism extends Lens {
	private static final long serialVersionUID = 1L;

	public Prism(Vector2d origin, int edges, double r, double refractionindex, boolean fixedPosition) {
		super(origin, refractionindex, fixedPosition);
		setPoints(edges,r);
		super.createBounds();
	}
	
	public void setPoints(int edges, double r) {
		clearPoints();
		
		for (int i = 0; i < edges; i++) {
			double angle = 2 * Math.PI / edges * i;
			Vector2d p = new Vector2d(r * Math.cos(angle), r * Math.sin(angle));
			points.add(p);
		}
		
		points.add(points.get(0).copy()); //Close loop

		super.restoreRotation();
		super.createBounds();
	}
	
	protected Rectangle2D getHitBox() {
		Vector2d botRight = getBottomRightBound();
		Vector2d topLeft = getTopLeftBound();
		double diffX = botRight.x - topLeft.x;
		double diffY = botRight.y - topLeft.y;
		
		return new Rectangle2D.Double(topLeft.x,topLeft.y,diffX,diffY);
	}

}
