package gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineJoin;
import model.OpticsModel;
import model.optics_objects.LightSource;
import model.optics_objects.Material;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class OpticsView {
	private OpticsModel model;
	private GraphicsContext gc;
	private Canvas canvas;
	private double scale, xTranslation, yTranslation;
	private boolean grid;
	
	private OpticsObject selected;
	
	public OpticsView(double width, double height) {
		this.canvas = new Canvas(width, height);
		this.gc = canvas.getGraphicsContext2D();
		gc.setLineJoin(StrokeLineJoin.ROUND);
		gc.save();
		this.scale = 1;
		this.xTranslation = 0;
		this.yTranslation = 0;
		selected = null;
		grid = true;
	}
	
	//TODO Do this in a better way for color mode
	public void redraw() {
		
		gc.setGlobalBlendMode(BlendMode.SRC_OVER);
		gc.setFill(Paint.valueOf("BLACK"));
		Vector2d p1 = getTablePos(0, 0);
		Vector2d p2 = getTablePos(canvas.getWidth(), canvas.getHeight());
		gc.fillRect(p1.x, p1.y, p2.x - p1.x, p2.y - p1.y);
		gc.setLineWidth(1);
		
		double gridSize = Main.WIDTH/20;
		
		gc.setStroke(new Color(1, 1, 1, 0.12));
		if(grid) {
			for(int i = 0; i < (canvas.getWidth()/gridSize + 2)/scale; i++) {
				double xStart = p1.x + (xTranslation/scale)%gridSize - gridSize;
				double yStart = p1.y + (yTranslation/scale)%gridSize - gridSize;
				gc.strokeLine(xStart + i*gridSize, yStart, xStart + i*gridSize, yStart + canvas.getHeight()/scale + 2*gridSize);
				gc.strokeLine(xStart, yStart + i*gridSize, xStart + canvas.getWidth()/scale + 2*gridSize, yStart + i*gridSize);
			}
		}
		
		gc.setGlobalBlendMode(BlendMode.SCREEN);
		
		for(LightSource s : model.getLights()) {
			s.calculateRayPaths(model.getMaterials());
			s.draw(gc, model.getSettings(), selected == s);
		}
		
		gc.setGlobalBlendMode(BlendMode.SRC_OVER);
		
		for(Material m : model.getMaterials()) {
			m.draw(gc, model.getSettings(), selected == m);	
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
	
	public Vector2d getTablePos(double screenX, double screenY) {
		return new Vector2d((-xTranslation + screenX)/scale, (-yTranslation + screenY)/scale);
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
