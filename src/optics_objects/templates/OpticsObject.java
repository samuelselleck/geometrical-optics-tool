package optics_objects.templates;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import gui.Main;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import optics_logic.GlobalOpticsSettings;
import util.Vector2d;

public abstract class OpticsObject implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Vector2d origin;
	private transient Map<String, DoubleProperty> editableProperties = new TreeMap<>();
	protected double totalRotation;
	
	public OpticsObject(Vector2d origin) {
		this.origin = origin;		
	}

	protected final void addProperty(String name, double value) {
		DoubleProperty property = new SimpleDoubleProperty(value);
		property.addListener(e -> update());
		editableProperties.put(name, property);	
	}
	
	protected final double get(String name) {
		return editableProperties.get(name).get();
	}
	
	protected abstract void clear();
	protected abstract void update();
	public abstract void draw(GraphicsContext gc, GlobalOpticsSettings settings, boolean selected);
	
	public final Map<String, DoubleProperty> getProperties() {
		return editableProperties;
	}
	
	public final void undbind() {
		for(Map.Entry<String, DoubleProperty> p : editableProperties.entrySet()) {
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
		Map<String, Double> properties = new TreeMap<>();
		for(Map.Entry<String, DoubleProperty> p : editableProperties.entrySet()) {
			properties.put(p.getKey(), p.getValue().get());
		}
		out.writeObject(properties);
		out.defaultWriteObject();
	}
	
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		@SuppressWarnings("unchecked")
		Map<String, Double> properties = (Map<String, Double>)in.readObject();
		editableProperties = new TreeMap<>();
		for(Map.Entry<String, Double> p : properties.entrySet()) {
			editableProperties.put(p.getKey(), new SimpleDoubleProperty(p.getValue()));
		}
		in.defaultReadObject();
		update();
	}
	
	public static int getResolution() {
		return Integer.parseInt(Main.properties.getProperty("opticsobjectresolution"));
	}
}