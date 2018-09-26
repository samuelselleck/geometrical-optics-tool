package util;

import java.io.Serializable;

public class Vector2d implements Serializable {
	private static final long serialVersionUID = 1L;
	public double x, y;
	
	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
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
	
	public Vector2d mult(double sc) {
		this.x *= sc;
		this.y *= sc;
		return this;
	}
	
	public Vector2d div(double dn) {
		this.x /= dn;
		this.y /= dn;
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
	    x = x * Math.cos(angle) - y * Math.sin(angle);
	    y = oldX * Math.sin(angle) + y * Math.cos(angle);
		return this;
	}
	
	public Vector2d copy() {
		return new Vector2d(this.x, this.y);
	}
	
	public void setTo(Vector2d vec) {
		this.x = vec.x;
		this.y = vec.y;
	}
	
	public static Vector2d getIntersectionParameters(
			Vector2d point1, Vector2d vec1, Vector2d point2, Vector2d vec2) {
		double den = vec1.x*vec2.y - vec1.y*vec2.x;
		double yTerm = point2.x - point1.x;
		double xTerm = point2.y - point1.y;
		double s = (vec1.y*yTerm - vec1.x*xTerm)/den;
		double t = (vec2.y*yTerm - vec2.x*xTerm)/den;
		return new Vector2d(s, t);
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
	
	public String toString() {
		return "x: " + x + " y: " + y;
	}
}
