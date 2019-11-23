package util;

import java.io.Serializable;

public class Vector2d implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public double x, y;
	
	public Vector2d(double x, double y) {
		setTo(x, y);
	}
	
	public double distSquared(Vector2d vec) {
		return (x - vec.x)*(x - vec.x) + (y - vec.y)*(y - vec.y);
	}
	
	public double dist(Vector2d vec) {
		return Math.sqrt(distSquared(vec));
	}
	
	public Vector2d add(Vector2d vec) {
		this.x += vec.x;
		this.y += vec.y;
		return this;
	}
	
	public Vector2d sub(Vector2d vec) {
		this.x -= vec.x;
		this.y -= vec.y;
		return this;
	}
	
	public Vector2d mult(double a) {
		this.x *= a;
		this.y *= a;
		return this;
	}
	
	public Vector2d div(double a) {
		this.x /= a;
		this.y /= a;
		return this;
	}
	
	public double dot(Vector2d vec) {
		return this.x*vec.x + this.y*vec.y;
	}
	
	public double cross(Vector2d vec) {
		return this.x*vec.y - this.y*vec.x;
	}
	
	public double crossSign(Vector2d vec) {
		return Math.signum(this.cross(vec));
	}
	
	public Vector2d normalize() {
		double l = length();
		this.x /= l;
		this.y /= l;
		return this;
	}
	
	public double length() {
		return Math.sqrt(x*x + y*y);
	}
	
	//Returns the angle WITH sign.
	public double angleTo(Vector2d vec) {	
		int cross = -this.cross(vec) > 0 ? 1 : -1;
		double val = this.dot(vec)
				/(this.length()*vec.length());
		double angle = Math.acos(val)*cross;
		return angle;
	}
	
	public Vector2d rotate(double angle) {
		
		double oldX = x;
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		
	    x = x * cos - y * sin;
	    y = oldX * sin + y * cos;
	    
		return this;
	}
	
	public double angleToInDegrees(Vector2d vec) {
		return this.angleTo(vec)*180/Math.PI;
	}
	
	public Vector2d rotateDegrees(double angle) {
		return this.rotate(angle*Math.PI/180);
	}
	
	public Vector2d copy() {
		return new Vector2d(this.x, this.y);
	}
	
	public void setTo(Vector2d vec) {
		setTo(vec.x, vec.y);
	}
	
	public void setTo(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public static Vector2d getIntersectionParameters(
			Vector2d p1, Vector2d v1, Vector2d p2, Vector2d v2) {
		return getIntersectionParameters(p1.x, p1.y, v1.x, v1.y, p2.x, p2.y, v2.x, v2.y, Vector2d.zero());
	}
	
	public static Vector2d getIntersectionParameters(
			double p1x, double p1y,
			double v1x, double v1y,
			double p2x, double p2y,
			double v2x, double v2y,
			Vector2d params) {
		double den = v1x*v2y - v1y*v2x;
		double yTerm = p2x - p1x;
		double xTerm = p2y - p1y;
		double s = (v1y*yTerm - v1x*xTerm)/den;
		double t = (v2y*yTerm - v2x*xTerm)/den;
		params.setTo(s, t);
		return params;
	}

	public static Vector2d zero() {
		return new Vector2d(0, 0);
	}
	
	public boolean isZero() {
		return this.x == 0 && this.y == 0;
	}

	public Vector2d neg() {
		this.x = -this.x;
		this.y = -this.y;
		return this;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
