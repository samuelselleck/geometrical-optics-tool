package optics_objects.materials;

import java.awt.geom.Rectangle2D;

import gui.Main;
import optics_objects.templates.Lens;
import util.Vector2d;

public class Prism extends Lens {
	private static final long serialVersionUID = 1L;

	public Prism(Vector2d origin, int edges, double r, double refractionindex, boolean fixedPosition) {
		super(origin, refractionindex, fixedPosition);
		for (int i = 0; i < edges; i++) {
			double angle = 2 * Math.PI / edges * i;
			Vector2d p = new Vector2d(r * Math.cos(angle), r * Math.sin(angle));
			points.add(p);
		}
		super.createBounds();
	}
	
	protected Rectangle2D getHitBox() {
		Vector2d botRight = getBottomRightBound();
		Vector2d topLeft = getTopLeftBound();
		double diffX = botRight.x - topLeft.x;
		double diffY = botRight.y - topLeft.y;
		
		//To make sure that mirrors and other really thin objects can be grabbed:
		if( diffX < Main.HEIGHT/10) {
			botRight.x += -diffX/2 + Main.HEIGHT/20;
			topLeft.x -= -diffX/2 + Main.HEIGHT/20;
		}
		if( diffY < Main.HEIGHT/10) {
			botRight.y += -diffY/2 + Main.HEIGHT/20;
			topLeft.y -= -diffY/2 + Main.HEIGHT/20;
		}
		
		return new Rectangle2D.Double(topLeft.x,topLeft.y,diffX,diffY);
	}

}
