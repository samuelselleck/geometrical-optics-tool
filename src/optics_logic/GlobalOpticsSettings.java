package optics_logic;

import java.io.Serializable;

public class GlobalOpticsSettings implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean drawOnlyHittingRays;
	private boolean colorMode;
	
	public void setColorMode(boolean val) {
		colorMode = val;
	}
	
	public void drawOnlyHitting(boolean val) {
		drawOnlyHittingRays = val;
	}
	
	public boolean drawOnlyHitting() {
		return drawOnlyHittingRays;
	}
	
	public boolean colorMode() {
		return colorMode;
	}

	public boolean toggleDrawOnlyHitting() {
		drawOnlyHittingRays = !drawOnlyHittingRays;
		return drawOnlyHittingRays;
	}

	public boolean toggleColorMode() {
		colorMode = !colorMode;
		return colorMode;
	}
}
