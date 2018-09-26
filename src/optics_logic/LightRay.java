package optics_logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gui.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import optics_objects.LightSource;
import optics_objects.Material;
import optics_objects.Wall;
import util.Utils;
import util.Vector2d;

public class LightRay implements Serializable {
	private static final long serialVersionUID = 1L;
	public static boolean DRAW_ONLY_HITTING = false;
	public static final int MAX_ITERATIONS = 20;
	
	ArrayList<Vector2d> path;
	Vector2d origin;
	Vector2d offset;
	Vector2d ray;
	double wavelength;

	public LightRay(Vector2d origin, Vector2d offset, Vector2d ray, double wavelength) {
		path = new ArrayList<>();
		this.origin = origin;
		this.offset = offset;
		this.ray = ray;
		this.wavelength = wavelength;
	}

	public LightRay(Vector2d origin, Vector2d ray, double wavelength) {
		this(origin, new Vector2d(0, 0), ray, wavelength);
	}
	public LightRay(Vector2d origin, Vector2d ray) {
		this(origin, ray, -1);
	}

	// Calculates lightray path and stores it in the variable path.
	// (This is bad code, I'm aware, just wanted to make it work)
	public void calculatePath(List<Material> materials) {
		path.clear();
		LightRay currRay = this;
		path.add(getPos());
		LightRay bestCandidateRay;
		
		int count = 0;
		do {
			count++;
			bestCandidateRay = null;
			//Find closest intersection, if it exists, add its point to path and set currRay to next ray.
			ArrayList<Vector2d> distanceList = getDistanceIndexList(materials, currRay);
			double closestIntersection = Double.MAX_VALUE;
			for(int i = 0; i < distanceList.size(); i++) {
				Material currentMaterial = materials.get((int)distanceList.get(i).x);
				
				LightRay candidateRay = getRayIntersection(currentMaterial, currRay);
				if(candidateRay != null) {
					if(candidateRay.ray.length() < closestIntersection) {
						closestIntersection = candidateRay.ray.length();
						if(currentMaterial instanceof Wall) {
							bestCandidateRay = new LightRay(candidateRay.getPos(), new Vector2d(0, 0));
						} else {
							bestCandidateRay = new LightRay(candidateRay.getPos(), candidateRay.ray.normalize());
						}
						//If the distance to the current light intersection is shorter than the
						//distance to the next material hitbox, we don't need to check the next one:
						if(i + 1 < distanceList.size() && closestIntersection < distanceList.get(i + 1).y) {
							break;
						}
					}
				}
			}
			if(bestCandidateRay != null) {
				path.add(bestCandidateRay.getPos());
				if(bestCandidateRay.ray.isZero()) {
					break; //Wall breaks it!
				} else {
					currRay = bestCandidateRay;
				}
			}
		} while (bestCandidateRay != null && count < MAX_ITERATIONS);

		if (bestCandidateRay == null && count != MAX_ITERATIONS) {
			path.add(currRay.getPos().add(currRay.ray.normalize().mult(Main.WIDTH)));
		}
	}

	// Calculates and returns a list of lens indexes and their distances,
	// ordered by which hit boxes where intersected first together with their distance from ray source.
	private ArrayList<Vector2d> getDistanceIndexList(List<Material> materials, LightRay currRay) {
		ArrayList<Vector2d> distanceList = new ArrayList<>();
		
		for (int i = 0; i < materials.size(); i++) {
			Vector2d botRight = materials.get(i).getBottomRightBound();
			Vector2d topLeft = materials.get(i).getTopLeftBound();
			
			boolean left = topLeft.x >= currRay.getPos().x;
			boolean right = currRay.getPos().x >= botRight.x;
			boolean top = topLeft.y >= currRay.getPos().y;
			boolean bot = currRay.getPos().y >= botRight.y;
			
			double dist;
			if(left) {
				dist = getDistance(currRay.getPos(), currRay.ray, topLeft,
					new Vector2d(0, botRight.y - topLeft.y));
				if(dist > 0) {
					distanceList.add(new Vector2d(i, dist));
					continue;
				}
			}
			if(top) {
				 dist = getDistance(currRay.getPos(), currRay.ray, topLeft,
							new Vector2d(botRight.x - topLeft.x, 0));
				if(dist > 0) {
					distanceList.add(new Vector2d(i, dist));
					continue;
				}
			}
			if(right) {
				 dist = getDistance(currRay.getPos(), currRay.ray, botRight,
							new Vector2d(0, topLeft.y - botRight.y));
				if(dist > 0) {
					distanceList.add(new Vector2d(i, dist));
					continue;
				}
			}
			if(bot) {
				 dist = getDistance(currRay.getPos(), currRay.ray, botRight,
							new Vector2d(topLeft.x - botRight.x, 0));
				if(dist > 0) {
					distanceList.add(new Vector2d(i, dist));
					continue;
				}
			}
			if(!(left || top || right || bot)) {
				distanceList.add(new Vector2d(i, 0));
			}
		}
		
		distanceList.sort((e1, e2) -> {
			return Double.compare(e1.y, e2.y);
		});
		return distanceList;
	}
		
	private double getDistance(Vector2d rayPos, Vector2d rayLine, Vector2d pos, Vector2d line) {
		Vector2d v = Vector2d.getIntersectionParameters(rayPos, rayLine, pos, line);
		if (v.x > 0 && v.x <= 1) {
			if (v.y > 0) {
				return v.y;
			}
		}	
		return -1;
	}

	// Calculates the first intersection with the material, returns null
	// if there was none and the new ray if there was a hit, where the ray length represents the distance to the hit point.
	private LightRay getRayIntersection(Material material, LightRay currRay) {
		double closest = Double.MAX_VALUE;
		Vector2d posHit = null;
	    Vector2d lineVec = null;
	    
		for (int i = 0; i < material.getPointCount(); i++) {
			Vector2d lineStartTemp = material.getPoint(i);
			Vector2d lineVecTemp = material.getSegment(i);
			Vector2d res = Vector2d.getIntersectionParameters(currRay.getPos(), currRay.ray, lineStartTemp, lineVecTemp);

			if (res.x > 0 && res.x <= 1) {
				if (res.y > 1e-9 && res.y < closest) {
					closest = res.y;
					lineVec = lineVecTemp;
					posHit = currRay.getPos().add(currRay.ray.copy().mult(res.y));
				}
			}
		}
		// If an intersection was found:
		if (closest != Double.MAX_VALUE) {
			Vector2d newRay = getRay(currRay.ray, lineVec, material, closest);
			return new LightRay(posHit, newRay);
		}
		return null;
	}

	// Gets the new ray.
	private Vector2d getRay(Vector2d ray, Vector2d line, Material material, double distance) {
		double cross = line.crossSign(ray);
		//convert the line to a normal:
		line.rotate(cross*Math.PI / 2).normalize();
		double angleIn = ray.angleTo(line);
		double angleOut = material.getAngle(angleIn, wavelength, cross > 0);
		return line.rotate(angleOut).mult(ray.length()*distance);
	}

	public void draw(GraphicsContext gc) {
		if (!DRAW_ONLY_HITTING || path.size() > 2) {
			int[] c = Utils.waveLengthToRGB(wavelength);
			double alpha = LightSource.WHITE ? 0.4 : 1;
			gc.setStroke(Color.rgb(c[0], c[1], c[2], alpha));
			for(int i = 0; i < path.size() - 1; i++) {
				Vector2d p1 = path.get(i);
				Vector2d p2 = path.get(i + 1);
				gc.strokeLine(p1.x, p1.y, p2.x, p2.y);
			}
		}
	}

	private Vector2d getPos() {
		return origin.copy().add(offset);
	}

	public void rotate(double angle) {
		offset.rotate(angle);
		ray.rotate(angle);
	}

}
