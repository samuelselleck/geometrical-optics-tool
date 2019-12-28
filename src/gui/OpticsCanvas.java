package gui;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.Color;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import model.OpticsModel;
import model.optics_objects.LightSource;
import model.optics_objects.Apparatus;
import model.optics_objects.OpticsObject;
import util.LightComposite;
import util.Vector2d;

public class OpticsCanvas {
	private OpticsModel model;
	private Canvas canvas;
	BufferedImage screenImage;
	AffineTransform defaultTransform;
	Graphics2D g;
	private double scale, xTranslation, yTranslation;
	private boolean grid;
	
	private OpticsObject selected;
	
	public OpticsCanvas() {
		//still need to add back grid and correct translate/scale
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
		        onSizeChange(width, height);
		        redraw();
		    }
		};
		this.scale = 1;
		this.xTranslation = 0;
		this.yTranslation = 0;
		selected = null;
		grid = false;
		onSizeChange(10, 10);
	}

	private void calculateRayPaths() {
		for(LightSource s : model.getLights()) {
			s.calculateRayPaths(model.getApparatuses());
		}
	}
	
	private void onSizeChange(double width, double height) {
		screenImage = new BufferedImage((int)Math.ceil(width), (int)Math.ceil(height),
				BufferedImage.TYPE_INT_ARGB);
		g = screenImage.createGraphics();
		defaultTransform = g.getTransform();
		g.setComposite(new LightComposite());
	}
	
	public void redraw() {
		calculateRayPaths();
		
		drawBackground();
		
		for(Apparatus a : model.getApparatuses()) {
			a.draw(g, selected == a);
		}
		
		for(LightSource s : model.getLights()) {
			s.draw(g,  selected == s);
		}
		
		Image fximg = SwingFXUtils.toFXImage(screenImage, null);
		canvas.getGraphicsContext2D().drawImage(fximg, 0, 0);
	}
	
	private void drawBackground() {
		g.setColor(Color.BLACK);
		g.clearRect(0, 0, screenImage.getWidth(), screenImage.getHeight());
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
		g.translate(x/scale, y/scale);
	}
	
	public void scale(double factor, double x, double y) {
		double old = scale;
		scale += factor;
		scale = Math.max(scale, 0.5);
		scale = Math.min(scale, 2);
		
		g.scale(scale/old, scale/old);
		translate(-x*(scale - old), -y*(scale - old));
	}
	
	public void resetView() {
		g.setTransform(defaultTransform);
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
		this.model = model;
	}
}
