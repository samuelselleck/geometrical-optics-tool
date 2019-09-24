package model;

import gui.Main;
import javafx.scene.paint.Color;

public class LensMaterial {
	
	private String name;
	
	private double b1, b2, b3, c1, c2, c3;
	private double[] color;
	
	public LensMaterial(String name, double[] color, double b1, double b2, double b3, double c1, double c2, double c3) {
		this.name = name;
		this.color = color;
		this.b1 = b1;
		this.b2 = b2;
		this.b3 = b3;
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
	}
	
	public String getName() {
		return name;
	}
	
	private double refraction(double wavelength) {
		double w = wavelength;
		return Math.sqrt(1 + 
				b1*w*w/(w*w - c1) +
				b2*w*w/(w*w - c2) +
				b3*w*w/(w*w - c3));		
	}
	
	public double refraction(double wavelength, double offsetMultiplier) {
		double minRefrac = refraction(Main.getIntProperty("minwavelength"));
		double refrac = minRefrac + offsetMultiplier*(refraction(wavelength) - minRefrac);
		return refrac;
	}
	
	public Color color(double alpha) {
		return new Color(color[0], color[1], color[2], alpha);
	}
	
	public String toString() {
		return getName();
	}
}
