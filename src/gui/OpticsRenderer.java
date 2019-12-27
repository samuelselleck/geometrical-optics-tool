package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import model.OpticsModel;
import model.optics_objects.Apparatus;
import model.optics_objects.LightSource;
import util.LightComposite;

public class OpticsRenderer {
	int width, height;
	OpticsModel model;
	BufferedImage image;
	Graphics2D g;
	
	public OpticsRenderer(OpticsModel model, int width, int height) {
		this.model = model;
		this.width = width;
		this.height = height;
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);		
		g = image.createGraphics();
		g.setBackground(Color.BLACK);
		LightComposite lightComposite = new LightComposite();
		g.setComposite(lightComposite);
	}
	
	public BufferedImage getRender() {
		
		g.clearRect(0, 0, width, height);
		for(Apparatus a : model.getApparatuses()) {
			a.draw(g, false);
		}
		
		for(LightSource s : model.getLights()) {
			s.draw(g,  false);
		}
		return image;
	}
}
