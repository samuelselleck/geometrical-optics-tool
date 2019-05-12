package optics_objects.templates;

import java.io.Serializable;
import java.util.ArrayList;
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
	private Map<String, DoubleProperty> editableProperties;
	protected double totalRotation;
	
	public OpticsObject(Vector2d origin) {
		this.origin = origin;
		editableProperties = new TreeMap<>();
		
	}

	protected final void addProperty(String name, double value) {
		DoubleProperty property = new SimpleDoubleProperty(value);
		property.addListener(e -> update());
		editableProperties.put(name, property);	
	}
	
	protected final double get(String name) {
		return editableProperties.get(name).get();
	}
	
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
	
	public static int getResolution() {
		return Integer.parseInt(Main.properties.getProperty("opticsobjectresolution"));
	}
}