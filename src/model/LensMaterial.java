package model;

import java.io.Serializable;

import javafx.scene.paint.Color;

public class LensMaterial implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	
	private double b1, b2, b3, c1, c2, c3;
	private double[] color;
	
	public LensMaterial(String name, double[] color, double b1, double b2, double b3, double c1, double c2, double c3) {
		this(name, color, new double[] {b1, b2, b3, c1, c2, c3});
	}
	
	public LensMaterial(String name, double[] color, double[] coeffs) {
		if(coeffs.length != 6) {
			throw new IndexOutOfBoundsException("not right ammount of coefficients");
		}
		this.name = name;
		this.color = color;
		this.b1 = coeffs[0];
		this.b2 = coeffs[1];
		this.b3 = coeffs[2];
		this.c1 = coeffs[3];
		this.c2 = coeffs[4];
		this.c3 = coeffs[5];
	}
	
	public String getName() {
		return name;
	}
	
	public double refractionSellmeier(double wavelength) {
		//nano to micro
		double w = wavelength*1e-3;
		double n = Math.sqrt(1 + 
				b1*w*w/(w*w - c1) +
				b2*w*w/(w*w - c2) +
				b3*w*w/(w*w - c3));	
		return n;
		
	}
	
	public Color color(double alpha) {
		return new Color(color[0], color[1], color[2], alpha);
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
