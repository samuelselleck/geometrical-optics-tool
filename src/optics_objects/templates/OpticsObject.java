package optics_objects.templates;

import java.io.Serializable;

import gui.Main;
import javafx.scene.canvas.GraphicsContext;
import optics_logic.OpticsSettings;
import util.Vector2d;

public abstract class OpticsObject implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Vector2d origin;
	protected double totalRotation;
	protected boolean fixedPosition;
	
	private int settingsId, creatorId;
	
	public OpticsObject(Vector2d origin, boolean fixedPosition) {
		this.origin = origin;
		this.fixedPosition = fixedPosition;
	}
	
	public OpticsObject setId(int settings, int creator) {
		this.settingsId = settings;
		this.creatorId  = creator;
		return this;
	}
	
	public int getSettingId() {
		return settingsId;
	}
	public int getCreatorId() {
		return creatorId;
	}

	public abstract void draw(GraphicsContext gc, OpticsSettings settings);
	public abstract void drawSelection(GraphicsContext gc, OpticsSettings settings);

	public Vector2d getOrigin() {
		return origin;
	}

	public void setOrigin(Vector2d vec) {
		setOrigin(vec.x, vec.y);
	}
	
	public void setOrigin(double x, double y) {
		if(!fixedPosition) {
			this.origin.setTo(x, y);
		}
	}
	
	protected abstract void rotateOp(double angle);
	public abstract boolean withinTouchHitBox(Vector2d pos);
	
	public void rotate(double angle) {
		totalRotation += angle;
		rotateOp(angle);
	}
	
	protected void restoreRotation() {
		rotateOp(totalRotation);
	}

	public boolean isFixed() {
		return fixedPosition;
	}
	
	public static int getResolution() {
		return Integer.parseInt(Main.properties.getProperty("opticsobjectresolution"));
	}

	public double getTotalRotation() { return totalRotation; }

}