package optics_objects.templates;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import gui.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import optics_logic.LightRay;
import optics_logic.OpticsSettings;
import util.Vector2d;

public abstract class Material extends OpticsObject {
	private static final long serialVersionUID = 1L;
	private Vector2d botRig, topLef;
	protected List<Vector2d> points;

	public Material(Vector2d origin, boolean fixedPosition) {
		super(origin, fixedPosition);
		points = new ArrayList<>();
	}

	public void createBounds() {
		botRig = Vector2d.zero();
		topLef = Vector2d.zero();
		for (int i = 0; i < points.size(); i++) {
			Vector2d p1 = points.get(i);
			if (p1.x < topLef.x)
				topLef.x = p1.x;
			if (p1.y < topLef.y)
				topLef.y = p1.y;
			if (p1.x > botRig.x)
				botRig.x = p1.x;
			if (p1.y > botRig.y)
				botRig.y = p1.y;
		}
		// Safety margins:
		topLef.add(new Vector2d(-1, -1));
		botRig.add(new Vector2d(1, 1));
	}

	public abstract double getAngle(double angleIn, double wavelength, boolean dir);

	@Override
	public void rotateOp(double angle) {
		for(Vector2d p : points) {
			p.rotate(angle);
		}
		this.createBounds();
	}
	
	public Vector2d getBottomRightBound() {
		return origin.copy().add(botRig);
	}

	public Vector2d getTopLeftBound() {
		return origin.copy().add(topLef);
	}
	
	public boolean withinTouchHitBox(Vector2d pos) {
		return getHitBox().contains(pos.x, pos.y);
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
	
	public Vector2d getPoint(int index) {
		return origin.copy().add(points.get(index % points.size()));
	}
	
	public Vector2d getSegment(int index) {
		return points.get((index + 1) % points.size()).copy().sub(points.get(index));
	}

	public int getPointCount() {
		return points.size();
	}
	
	public void draw(GraphicsContext gc, OpticsSettings settings) {
		
		gc.setStroke(Paint.valueOf("WHITE"));
		Vector2d p1;
		Vector2d p2 = getPoint(0);
		for (int i = 0; i < getPointCount(); i++) {
			p1 = p2;
			p2 = getPoint(i);
			gc.strokeLine(p1.x, p1.y, p2.x, p2.y);
		}
		
	}
	
	@Override
	public void drawSelection(GraphicsContext gc, OpticsSettings settings) {
			gc.setStroke(Paint.valueOf("GRAY"));
			Rectangle2D box = getHitBox();
			gc.strokeRect(box.getX(), box.getMinY(), box.getWidth(), box.getHeight());
	}
}
