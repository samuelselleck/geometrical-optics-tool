package model.optics_objects;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import util.Vector2d;

public abstract class OpticsObject implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Vector2d origin;
	private transient Map<String, DoubleProperty> properties = new TreeMap<>();
	protected double totalRotation;
	
	public OpticsObject(Vector2d origin, Map<String, DoubleProperty> properties) {
		
		for(Map.Entry<String, DoubleProperty> entry : properties.entrySet()) {
			double val = entry.getValue().get();
			addProperty(entry.getKey(), val);
		}
		this.origin = origin;		
		
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
	
	public final Map<String, DoubleProperty> getProperties() {
		return properties;
	}
	
	public final void undbind() {
		for(Map.Entry<String, DoubleProperty> p : properties.entrySet()) {
			p.getValue().unbind();
		}
	}
	
	public Vector2d getOrigin() {
		return origin;
	}

	public void setOrigin(Vector2d vec) {
		setOrigin(vec.x, vec.y);
	}
	
	public void setOrigin(double x, double y) {
		this.origin.setTo(x, y);
	}
	
	protected abstract void rotateOp(double angle);
	public abstract boolean withinTouchHitBox(Vector2d pos);
	
	public void rotate(double angle) {
		totalRotation += angle;
		rotateOp(angle);
	}
	
	protected void initObject() {
		rotateOp(totalRotation);
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