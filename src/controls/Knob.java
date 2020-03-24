package controls;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import util.Vector2d;

public class Knob extends ResizableCanvas {
	
	private DoubleProperty value = new SimpleDoubleProperty();
	private DoubleProperty factor = new SimpleDoubleProperty(1/360.0);
	private Vector2d lastPos;
	
	public Knob() {
		heightProperty().bind(widthProperty());
		widthProperty().addListener(e -> {
			redraw();
		});
		
		setOnMousePressed(e -> {
			lastPos = new Vector2d(e.getX() - getWidth()/2, e.getY() - getHeight()/2);
		});
		
		setOnMouseDragged(e -> {
			Vector2d currPos = new Vector2d(e.getX() - getWidth()/2, e.getY() - getHeight()/2);
			double angle = currPos.angleToInDegrees(lastPos);
			if(!Double.isNaN(angle)) {
				value.set(value.get() + angle*factor.get());
			}
			lastPos = currPos;
		});
		
		value.addListener(r -> {
			redraw();
		});
		
		redraw();
	}
	
	private void redraw() {
		GraphicsContext gc = getGraphicsContext2D();
		gc.clearRect(0, 0, getWidth(), getHeight());
		gc.setFill(Paint.valueOf("black"));
		gc.fillOval(getWidth()/6, getHeight()/6, getWidth()*2/3, getHeight()*2/3);
		Vector2d pos = new Vector2d(getWidth()/5, 0).rotateDegrees(value.get()/factor.get());
		pos.add(new Vector2d(getWidth()/2, getHeight()/2));
		gc.setFill(Paint.valueOf("gray"));
		gc.fillOval(pos.x - getWidth()/20, pos.y - getWidth()/20, getWidth()/10, getWidth()/10);
	}
	
	public DoubleProperty valueProperty() {
		return value;
	}
	
	public DoubleProperty factorProperty() {
		return factor;
	}
}
