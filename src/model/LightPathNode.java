package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gui.Main;
import javafx.scene.canvas.GraphicsContext;
import model.optics_objects.Material;
import util.Vector2d;

public class LightPathNode implements Iterable<LightPathNode> {
	private Vector2d origin;
	private List<LightPathNode> nexts;
	
	public LightPathNode(Vector2d origin) {
		this.origin = origin;
	}
	
	public void develop(List<Vector2d> dirs, List<Material> materials, int wavelength) {
		develop(dirs, materials, wavelength, 0);
	}
	
	private void develop(List<Vector2d> dirs, List<Material> materials, int wavelength, int iterr) {
		if(iterr > Main.getIntProperty("maxraybounce")) {
			return;
		}
		
		if(dirs.size() != 0) {
			nexts = new ArrayList<>();
		}
		for(Vector2d dir : dirs) {
			
			RayIntersectionData closestHitData = new RayIntersectionData();
			
			List<MaterialDistanceTuple> approximateDistances = getDistanceList(materials, origin, dir);
			
			for(int i = 0; i < approximateDistances.size(); i++) {
				MaterialDistanceTuple current = approximateDistances.get(i);
				RayIntersectionData data = current.material.calculateIntersection(origin, dir);
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
				newNode = new LightPathNode(closestHitData.position);
				List<Vector2d> newDirs = closestHitData.getScatteredLight(wavelength);
				newNode.develop(newDirs, materials, wavelength, iterr + 1);
			} else {
				newNode = new LightPathNode(dir.copy().mult(10000).add(origin));
			}
			nexts.add(newNode);
		}
	}
	
	private List<MaterialDistanceTuple> getDistanceList(List<Material> materials, Vector2d origin, Vector2d dir) {
		List<MaterialDistanceTuple> distanceList = new ArrayList<>();
		
		for (Material m : materials) {
			Vector2d botRight = m.getBottomRightBound();
			Vector2d topLeft = m.getTopLeftBound();
			
			
			boolean left = topLeft.x >= origin.x;
			boolean right = origin.x >= botRight.x;
			boolean top = topLeft.y >= origin.y;
			boolean bot = origin.y >= botRight.y;
			
			double dist;
			if(left) {
				dist = getDistance(origin, dir, topLeft,
					new Vector2d(0, botRight.y - topLeft.y));
				if(dist > 0) {
					distanceList.add(new MaterialDistanceTuple(m, dist));
					continue;
				}
			}
			if(top) {
				 dist = getDistance(origin, dir, topLeft,
							new Vector2d(botRight.x - topLeft.x, 0));
				if(dist > 0) {
					distanceList.add(new MaterialDistanceTuple(m, dist));
					continue;
				}
			}
			if(right) {
				 dist = getDistance(origin, dir, botRight,
							new Vector2d(0, topLeft.y - botRight.y));
				if(dist > 0) {
					distanceList.add(new MaterialDistanceTuple(m, dist));
					continue;
				}
			}
			if(bot) {
				 dist = getDistance(origin, dir, botRight,
							new Vector2d(topLeft.x - botRight.x, 0));
				if(dist > 0) {
					distanceList.add(new MaterialDistanceTuple(m, dist));
					continue;
				}
			}
			if(!(left || top || right || bot)) {
				distanceList.add(new MaterialDistanceTuple(m, 0));
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
	
	private class MaterialDistanceTuple {
		Material material;
		double distance;
		
		public MaterialDistanceTuple(Material material, double distance) {
			this.material = material;
			this.distance = distance;
		}
	}

	public Vector2d getOrigin() {
		return origin;
	}

	@Override
	public Iterator<LightPathNode> iterator() {
		return nexts.iterator();
	}

	public void strokeWith(GraphicsContext gc) {
		gc.lineTo(origin.x, origin.y);
		if(nexts == null) return;
		
		for(LightPathNode p : nexts) {
			p.strokeWith(gc);
			gc.moveTo(origin.x, origin.y);
		}
	}
}
