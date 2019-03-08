package optics_objects.templates;

public class MotionPolicy {
	private boolean fixedPosition;
	private boolean fixedRotation;
	
	public MotionPolicy(boolean fixedPosition, boolean fixedRotation) {
		this.fixedPosition = fixedPosition;
		this.fixedRotation = fixedRotation;
	}
	
	public boolean fixedPosition() {
		return fixedPosition;
	}
	
	public boolean fixedRotation() {
		return fixedRotation;
	}
}
