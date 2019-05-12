package optics_objects.templates;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import optics_logic.GlobalOpticsSettings;
import util.Vector2d;

public abstract class Mirror extends Material {
	
	private static final long serialVersionUID = 1L;
	
	public Mirror(Vector2d origin) {
		super(origin);
	}
	
	@Override
	public double getAngle(double angleIn, double wavelength, boolean dir) {
		return Math.PI - angleIn;
	}
	
	public void draw(GraphicsContext gc, GlobalOpticsSettings settings, boolean selected) {
		
		gc.setStroke(new Color(0.7, 0.7, 0.7, 1));
		if(selected) {
			gc.setLineWidth(3);
		} else {
			gc.setLineWidth(2);
		}
		gc.beginPath();
		for (int i = 0; i < getPointCount(); i++) {
			Vector2d p = getPoint(i);
			gc.lineTo(p.x, p.y);
		}
		gc.stroke();
	}
}
