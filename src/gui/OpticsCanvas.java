package gui;

import java.awt.image.BufferedImage;

import javafx.beans.InvalidationListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineJoin;
import model.OpticsModel;
import model.optics_objects.LightSource;
import model.optics_objects.Apparatus;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public class OpticsCanvas {
	private OpticsModel model;
	private GraphicsContext gc;
	private Canvas canvas;
	private OpticsRenderer renderer;
	private double scale, xTranslation, yTranslation;
	private boolean grid;
	
	private OpticsObject selected;
	
	public OpticsCanvas() {
		
		this.canvas = new Canvas(10, 10) {
			@Override
		    public boolean isResizable() {
		        return true;
		    }

		    @Override
		    public double maxHeight(double width) {
		        return Double.POSITIVE_INFINITY;
		    }

		    @Override
		    public double maxWidth(double height) {
		        return Double.POSITIVE_INFINITY;
		    }

		    @Override
		    public double minWidth(double height) {
		        return 1D;
		    }

		    @Override
		    public double minHeight(double width) {
		        return 1D;
		    }

		    @Override
		    public void resize(double width, double height) {
		        this.setWidth(width);
		        this.setHeight(height);
		    }
		};
		
		InvalidationListener sizeChange = e -> {
			renderer = new OpticsRenderer(model, (int)Math.ceil(canvas.getWidth()), (int)Math.ceil(canvas.getHeight()));
			redraw();
		};
		canvas.widthProperty().addListener(sizeChange);
		canvas.heightProperty().addListener(sizeChange);
		
		this.gc = canvas.getGraphicsContext2D();
		
		gc.setLineJoin(StrokeLineJoin.ROUND);
		gc.save();
		this.scale = 1;
		this.xTranslation = 0;
		this.yTranslation = 0;
		selected = null;
		grid = false;
	}
	
	
	public void redrawOld() {
	  
		drawBackground();
		  
		//gc.setGlobalBlendMode(BlendMode.SCREEN);
		  
		for(LightSource s : model.getLights()) {
			s.calculateRayPaths(model.getApparatuses()); 
			s.draw(gc, selected == s);
		}
		
		gc.setGlobalBlendMode(BlendMode.SRC_OVER);
		  
		for(Apparatus a : model.getApparatuses()) {
			a.draw(gc, selected == a);
		} 
	}
	 
	
	public void redraw() {
		redrawNew();
	}
	
	private void calculateRayPaths() {
		for(LightSource s : model.getLights()) {
			s.calculateRayPaths(model.getApparatuses());
		}
	}
	
	public void redrawNew() {
		calculateRayPaths();
		
		if(renderer == null) {
			renderer = new OpticsRenderer(model, (int)Math.ceil(canvas.getWidth()), (int)Math.ceil(canvas.getHeight()));
		}
		BufferedImage img = renderer.getRender();
		Image fximg = SwingFXUtils.toFXImage(img, null);
		gc.drawImage(fximg, 0, 0);
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
		renderer = new OpticsRenderer(model, (int)Math.ceil(canvas.getWidth()), (int)Math.ceil(canvas.getHeight()));
		this.model = model;
	}
}
