package model.optics_objects;

import java.awt.Graphics2D;
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
import util.Vector2d;

public abstract class OpticsObject implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final List<String> REQUIRED_PROPERTIES = new ArrayList<>();
	private transient Map<String, DoubleProperty> properties = new TreeMap<>();
	
	static {
		REQUIRED_PROPERTIES.add("X");
		REQUIRED_PROPERTIES.add("Y");
		REQUIRED_PROPERTIES.add("Rotation");
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
	
	protected final double get(String name) {
		return properties.get(name).get();
	}
	
	protected abstract void clear();
	protected abstract void update();
	
	public abstract void draw(GraphicsContext gc, boolean selected);
	public abstract void draw(Graphics2D g, boolean selected);
	
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
	
	public void rotate(double angle) {
		properties.get("Rotation").set(angle + get("Rotation"));
	}
	
	protected void init() {		
		rotateOp(get("Rotation"));
	}
	
	public Vector2d getOrigin() {
		return new Vector2d(get("X")*Main.DPCM, get("Y")*Main.DPCM);
	}
	
	public void setOrigin(double x, double y) {
		properties.get("X").set(x/Main.DPCM);
		properties.get("Y").set(y/Main.DPCM);
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
}