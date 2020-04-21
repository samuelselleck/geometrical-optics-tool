package gui;

import java.util.Locale;

import controls.ResizableCanvas;
import javafx.beans.binding.DoubleExpression;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import model.OpticsModel;
import model.optics_objects.LightSource;
import model.optics_objects.Material;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class OpticsCanvas {
	private OpticsModel model;
	private GraphicsContext gc;
	private Canvas canvas;
	private double scale, xTranslation, yTranslation;
	private boolean grid;
	
	private OpticsObject selected;
	
	public OpticsCanvas() {
		
		this.canvas = new ResizableCanvas();
		
		canvas.widthProperty().addListener(e -> redraw());
		canvas.heightProperty().addListener(e -> redraw());
		
		this.gc = canvas.getGraphicsContext2D();
		
		gc.setLineJoin(StrokeLineJoin.ROUND);
		gc.save();
		this.scale = 1;
		this.xTranslation = 0;
		this.yTranslation = 0;
		selected = null;
		grid = false;
	}
	
	//TODO Do this in a better way for color mode
	public void redraw() {
		
		drawBackground();
		
		gc.setGlobalBlendMode(BlendMode.SCREEN);
		
		for(LightSource s : model.getLights()) {
			s.calculateRayPaths(model.getMaterials());
			s.draw(gc, selected == s);
		}
		
		gc.setGlobalBlendMode(BlendMode.SRC_OVER);
		
		for(Material m : model.getMaterials()) {
			m.draw(gc, selected == m);	
		}
	}
	
	private void drawBackground() {
		
		gc.setGlobalBlendMode(BlendMode.SRC_OVER);
		gc.setFill(Paint.valueOf("BLACK"));
		Vector2d p1 = getTablePos(0, 0);
		Vector2d p2 = getTablePos(canvas.getWidth(), canvas.getHeight());
		gc.fillRect(p1.x, p1.y, p2.x - p1.x, p2.y - p1.y);
		gc.setLineWidth(1);
		
		double gridSize = Main.DPCM;
		
		gc.setStroke(new Color(1, 1, 1, 0.3));
		if(grid) {
			for(int i = 0; i < (canvas.getWidth()/gridSize + 2)/scale; i++) {
				double xStart = p1.x + (xTranslation/scale)%gridSize - gridSize;
				double yStart = p1.y + (yTranslation/scale)%gridSize - gridSize;
				gc.strokeLine(xStart + i*gridSize, yStart, xStart + i*gridSize, yStart + canvas.getHeight()/scale + 2*gridSize);
				gc.strokeLine(xStart, yStart + i*gridSize, xStart + canvas.getWidth()/scale + 2*gridSize, yStart + i*gridSize);
			}
		}
	}
	
	public void select(OpticsObject obj) {
		this.selected = obj;
	}
	
	public void deselect() {
		this.selected = null;
	}
	
	public void translate(double x, double y) {
		xTranslation += x;
		yTranslation += y;
		gc.translate(x/scale, y/scale);
	}
	
	public void scale(double factor, double x, double y) {
		double old = scale;
		scale += factor;
		scale = Math.max(scale, 0.5);
		scale = Math.min(scale, 2);
		
		gc.scale(scale/old, scale/old);
		translate(-x*(scale - old), -y*(scale - old));
	}
	
	public void resetView() {
		gc.restore();
		gc.save();
		xTranslation = 0;
		yTranslation = 0;
		scale = 1;
	}
	
	public void bindXYLabel(Label label) {
		canvas.setOnMouseMoved(e -> {
			Vector2d pos  = getTablePos(e.getX(), e.getY());
			String text = String.format("x = %.2f   y = %.2f     ", pos.x/Main.DPCM, pos.y/Main.DPCM);
			label.setText(text);
			label.setFont(new Font(10));
		});
		canvas.setOnMouseExited(e -> {
			label.setText("");
		});
	}
	
	public Vector2d getTablePos(double screenX, double screenY) {
		return new Vector2d((-xTranslation + screenX)/scale,
				(-yTranslation + screenY)/scale);
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public void setGrid(boolean val) {
		this.grid = val;
	}

	public void setOpticsModel(OpticsModel model) {
		this.model = model;
	}
}
