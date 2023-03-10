package model.optics_objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gui.Main;
import javafx.beans.property.DoubleProperty;
import model.ModelMetadata;
import model.RayIntersectionData;
import util.Vector2d;

public abstract class Material extends OpticsObject {
	private static final long serialVersionUID = 1L;
	private transient Vector2d botRig, topLef;
	protected transient List<Vector2d> points = new ArrayList<>();

	public Material(Map<String, DoubleProperty> properties) {
		super(properties);
	}

	@Override
	protected void init() {
		super.init();
		createBounds();
	}
	
	@Override
	protected void clear() {
		if(points == null) {
			points = new ArrayList<>();
		} else {
			points.clear();
		}
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
	
	@Override
	public void rotateOp(double angle) {
		for(Vector2d p : points) {
			p.rotateDegrees(angle);
		}
		this.createBounds();
	}
	
	public Vector2d getBottomRightBound() {
		return getOrigin().add(botRig);
	}

	public Vector2d getTopLeftBound() {
		return getOrigin().add(topLef);
	}
	
	@Override
	public boolean withinTouchHitBox(Vector2d pos) {
		
		Vector2d botRight = getBottomRightBound();
		Vector2d topLeft = getTopLeftBound();
		double diffX = botRight.x - topLeft.x;
		double diffY = botRight.y - topLeft.y;
		
		//To make sure that mirrors and other really thin objects can be grabbed:
		if( diffX < Main.DPCM) {
			botRight.x += -diffX/2 + Main.DPCM;
			topLeft.x -= -diffX/2 + Main.DPCM;
		}
		if( diffY < Main.DPCM) {
			botRight.y += -diffY/2 + Main.DPCM;
			topLeft.y -= -diffY/2 + Main.DPCM;
		}
		boolean left = topLeft.x >= pos.x;
		boolean right = pos.x >= botRight.x;
		boolean top = topLeft.y >= pos.y;
		boolean bot = pos.y >= botRight.y;
		return !(left || top || right || bot);
	}
	
	public Vector2d getPoint(int index) {
		return getOrigin().add(points.get(index % points.size()));
	}
	
	public Vector2d getSegment(int index) {
		return points.get((index + 1) % points.size()).copy().sub(points.get(index));
	}

	public int getPointCount() {
		return points.size();
	}

	public RayIntersectionData calculateIntersection(Vector2d origin, Vector2d dir) {
		RayIntersectionData data = new RayIntersectionData();
		data.ray = dir;
	    dir.normalize();
	    
		for (int i = 0; i < this.getPointCount() - 1; i++) {
			Vector2d lineStartTemp = this.getPoint(i);
			Vector2d lineVecTemp = this.getSegment(i);
			Vector2d res = Vector2d.getIntersectionParameters(origin, dir, lineStartTemp, lineVecTemp);

			if (res.x >= 0 && res.x <= 1) {
				if (res.y > 1e-9 && res.y < data.distance) {
					data.distance = res.y;
					data.surfaceId = i;
				}
			}
		}
		// If an intersection was found:
		if (data.distance == Double.MAX_VALUE) {
			return null;
		}
		data.material = this;
		data.position = dir.copy().mult(data.distance).add(origin);
		return data;
	}
	
	public abstract List<Vector2d> getScatteredLight(RayIntersectionData data, ModelMetadata metadata, int wavelength);

	public void onHit(Vector2d position, int surfaceId) { };
}
