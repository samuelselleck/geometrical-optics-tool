package model;

import java.util.ArrayList;
import java.util.List;
import gui.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.optics_objects.Apparatus;
import util.Vector2d;

public class LightPathNode {
	private Vector2d origin;
	private double intensity;
	private List<LightPathNode> nexts;
	
	public LightPathNode(Vector2d origin, double intensity) {
		this.origin = origin;
		this.intensity = intensity;
	}
	
	public void develop(Vector2d dir, List<Apparatus> apparatuses, int wavelength) {
		List<RayIntensityTuple> dirs = new ArrayList<>();
		RayIntensityTuple root = new RayIntensityTuple(dir, 1);
		dirs.add(root);
		develop(dirs, apparatuses, wavelength, 0);
	}
	
	private void develop(List<RayIntensityTuple> incomingScatteredLight, List<Apparatus> apparatuses, int wavelength, int iterr) {
		
		if(iterr > Main.getIntProperty("maxraybounce")) {
			return;
		}
		
		if(incomingScatteredLight != null) {
			
			nexts = new ArrayList<>();
			for(RayIntensityTuple rit : incomingScatteredLight) {
				
				RayIntersectionData closestHitData = new RayIntersectionData();
				
				List<ApparatusDistanceTuple> approximateDistances = getDistanceList(apparatuses, origin, rit.ray);
				
				for(int i = 0; i < approximateDistances.size(); i++) {
					ApparatusDistanceTuple current = approximateDistances.get(i);
					RayIntersectionData data = calculateIntersection(current.apparatus, origin, rit.ray);
					if(data == null) continue;
					
					if(data.distance < closestHitData.distance) {
						closestHitData = data;
					}
					if(i + 1 < approximateDistances.size()) {
						if(closestHitData.distance < approximateDistances.get(i + 1).distance) {
							break;
						}
					}
				}
				LightPathNode newNode;
				if(closestHitData.distance != Double.MAX_VALUE) {
					newNode = new LightPathNode(closestHitData.position, rit.intensity);
					List<RayIntensityTuple> scatteredLight = closestHitData.calculateScatteredLight(wavelength, rit.intensity);
					newNode.develop(scatteredLight, apparatuses, wavelength, iterr + 1);
				} else {
					newNode = new LightPathNode(rit.ray.copy().mult(10000).add(origin), rit.intensity);
				}
				nexts.add(newNode);
			}
		}
	}
	
	private RayIntersectionData calculateIntersection(Apparatus apparatus, Vector2d origin, Vector2d ray) {
		RayIntersectionData data = new RayIntersectionData();
		data.ray = ray;
	    data.ray.normalize();
	    
		for (int i = 0; i < apparatus.getPointCount() - 1; i++) {
			Vector2d lineStartTemp = apparatus.getPoint(i);
			Vector2d lineVecTemp = apparatus.getSegment(i);
			Vector2d res = Vector2d.getIntersectionParameters(origin, ray, lineStartTemp, lineVecTemp);

			if (res.x >= 0 && res.x <= 1) {
				if (res.y > 1e-9 && res.y < data.distance) {
					data.distance = res.y;
					data.surface = lineVecTemp;
				}
			}
		}
		// If an intersection was found:
		if (data.distance == Double.MAX_VALUE) {
			return null;
		}
		data.apparatus = apparatus;
		data.position = ray.copy().mult(data.distance).add(origin);
		return data;
	}
	
	private List<ApparatusDistanceTuple> getDistanceList(List<Apparatus> apparatuses, Vector2d origin, Vector2d dir) {
		List<ApparatusDistanceTuple> distanceList = new ArrayList<>();
		
		for (Apparatus a : apparatuses) {
			
			Vector2d botRight = a.getBottomRightBound();
			Vector2d topLeft = a.getTopLeftBound();
					
			boolean[] sides = new boolean[] {
					topLeft.x >= origin.x, //Origin to the left
					topLeft.y >= origin.y, //Origin over
					origin.x >= botRight.x, //Origin To the right
					origin.y >= botRight.y, //Origin under
			};
			
			if(!(sides[0] || sides[1] || sides[2] || sides[3])) {
				distanceList.add(new ApparatusDistanceTuple(a, 0));
			}
			
			Vector2d[] intersectionPositions = new Vector2d[] {
					topLeft,
					topLeft,
					botRight, 
					botRight,
			};
			
			double width = botRight.x - topLeft.x;
			double height = botRight.y - topLeft.y;
			Vector2d[] intersectionSegments = new Vector2d[] {
					new Vector2d(0, height),
					new Vector2d(width, 0),
					new Vector2d(0, -height),
					new Vector2d(-width, 0)
			};

			for(int i = 0; i < 4; i++) {
				if(sides[i]) {
					 double dist = getDistance(origin, dir, 
							 intersectionPositions[i], intersectionSegments[i]);
					if(dist > 0) {
						distanceList.add(new ApparatusDistanceTuple(a, dist));
						break;
					}
				}
			}		
		}
		
		distanceList.sort((e1, e2) -> {
			return Double.compare(e1.distance, e2.distance);
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
	
	private class ApparatusDistanceTuple {
		private Apparatus apparatus;
		double distance;
		
		public ApparatusDistanceTuple(Apparatus apparatus, double distance) {
			this.apparatus = apparatus;
			this.distance = distance;
		}
	}
	
	private class RayIntersectionData {
		private Apparatus apparatus;
		private double distance = Double.MAX_VALUE;
		private Vector2d surface, ray;
		private Vector2d position;
		
		public List<RayIntensityTuple> calculateScatteredLight(int wavelength, double intensity) {
			
			List<RayIntensityTuple> scatterDirections = apparatus.getScatteredLight(ray, surface, intensity, wavelength);
			return scatterDirections;
		}
	}
	
	public static class RayIntensityTuple {
		public Vector2d ray;
		public double intensity;
		
		public RayIntensityTuple(Vector2d ray, double intensity) {
			this.ray = ray;
			this.intensity = intensity;
		}
	}

	public Vector2d getOrigin() {
		return origin;
	}

	public void stroke(GraphicsContext gc, Color color) {
		gc.lineTo(origin.x, origin.y);
		Color strokeColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), intensity);
		gc.setStroke(strokeColor);
		gc.stroke();
		
		if(nexts == null) return;
		
		for(LightPathNode p : nexts) {
			gc.beginPath();
			gc.moveTo(origin.x, origin.y);
			p.stroke(gc, color);		
		}
	}
}
