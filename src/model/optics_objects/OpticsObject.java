package model.optics_objects;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import gui.Main;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import util.Vector2d;

public abstract class OpticsObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private static double ROT_ORIGIN_RADIUS = 10;
	public static final List<String> REQUIRED_PROPERTIES = new ArrayList<>();
	private transient Map<String, DoubleProperty> properties = new TreeMap<>();
	
	static {
		REQUIRED_PROPERTIES.add("X");
		REQUIRED_PROPERTIES.add("Y");
		REQUIRED_PROPERTIES.add("Rotation");
		REQUIRED_PROPERTIES.add("RotationX");
		REQUIRED_PROPERTIES.add("RotationY");
		REQUIRED_PROPERTIES.add("FixedPosition");
		REQUIRED_PROPERTIES.add("FixedRotation");
		REQUIRED_PROPERTIES.add("FixedProperties");
		REQUIRED_PROPERTIES.add("NoFocus");
	}
	
	public OpticsObject(Map<String, DoubleProperty> properties) {
		
		for(String propertyName : REQUIRED_PROPERTIES) {
			if(!properties.containsKey(propertyName)) {
				throw new IllegalArgumentException("Required property: " + propertyName + " missing");
			}
		}
		
		for(Map.Entry<String, DoubleProperty> entry : properties.entrySet()) {
			double val = entry.getValue().get();
			addProperty(entry.getKey(), val);
		}
	}

	protected final void addProperty(String name, double value) {
		DoubleProperty property = new SimpleDoubleProperty(value);
		property.addListener(e -> update());
		properties.put(name, property);	
	}
	
	public final double get(String name) {
		if(properties.containsKey(name)) {
			return properties.get(name).get();
		} else {
			throw new IllegalArgumentException("The optics Object did not contain the property: " + name);
		}
	}
	
	public final boolean getBool(String name) {
		return properties.get(name).isEqualTo(1).get();
	}
	
	protected abstract void clear();
	protected abstract void update();
	
	public void draw(GraphicsContext gc, boolean selected) {
		if(selected) {
			gc.setStroke(Paint.valueOf("white"));	
			Vector2d pos = getRotationOrigin();
			double ror = ROT_ORIGIN_RADIUS;
			gc.strokeOval(pos.x - ror, pos.y - ror, ror*2, ror*2);
		}
		
		System.out.println(get("RotationX"));
	}
	
	public final Map<String, DoubleProperty> getProperties() {
		return properties;
	}
	
	public final void undbind() {
		for(Map.Entry<String, DoubleProperty> p : properties.entrySet()) {
			p.getValue().unbind();
		}
	}
	
	protected abstract void rotateOp(double angle);
	public abstract boolean withinTouchHitBox(Vector2d pos);
	
	public final boolean withinRotationPoint(Vector2d pos) {
		if(getBool("FixedRotation")) return false;
		
		return getRotationOrigin().distSquared(pos) <= ROT_ORIGIN_RADIUS*ROT_ORIGIN_RADIUS*4;
	}
	
	public void rotate(double angle) {
		if(!getBool("FixedRotation")) {
			properties.get("Rotation").set(angle + get("Rotation"));
			Vector2d rotOrg = getOrigin().sub(getRotationOrigin()).rotateDegrees(angle);
			Vector2d newPos = getRotationOrigin().add(rotOrg);
			setOrigin(newPos.x, newPos.y);
			Vector2d rot = new Vector2d(get("RotationX"), get("RotationY"));
			rot.rotateDegrees(angle);
			properties.get("RotationX").set(rot.x);
			properties.get("RotationY").set(rot.y);
		}
	}
	
	protected void init() {
		rotateOp(get("Rotation"));
	}
	
	public Vector2d getOrigin() {
		return new Vector2d(get("X")*Main.DPCM, get("Y")*Main.DPCM);
	}
	
	public void setOrigin(double x, double y) {
		if(!getBool("FixedPosition")) {
			properties.get("X").set(x/Main.DPCM);
			properties.get("Y").set(y/Main.DPCM);
		}
	}

	public Vector2d getRotationOrigin() {
		return new Vector2d(get("RotationX")*Main.DPCM, get("RotationY")*Main.DPCM).add(getOrigin());
	}
	
	public void setRotationOrigin(double x, double y) {
		if(!getBool("FixedRotation")) {
			properties.get("RotationX").set(x/Main.DPCM - get("X"));
			properties.get("RotationY").set(y/Main.DPCM - get("Y"));
		}
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		Map<String, Double> propertiesSave = new TreeMap<>();
		for(Map.Entry<String, DoubleProperty> p : properties.entrySet()) {
			propertiesSave.put(p.getKey(), p.getValue().get());
		}
		out.writeObject(propertiesSave);
		out.defaultWriteObject();
	}
	
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		@SuppressWarnings("unchecked")
		Map<String, Double> propertiesLoad = (Map<String, Double>)in.readObject();
		properties = new TreeMap<>();
		for(Map.Entry<String, Double> p : propertiesLoad.entrySet()) {
			addProperty(p.getKey(), p.getValue());
		}
		in.defaultReadObject();
		update();
	}
	
	public boolean isProp() {
		return getBool("FixedPosition") || getBool("FixedRotation") || getBool("FixedProperties") || getBool("NoFocus");
	}
}