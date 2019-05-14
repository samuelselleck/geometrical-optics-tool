package optics_objects.lights;

import optics_objects.materials.DiffractionGrating;
import optics_objects.templates.LightSource;
import util.Vector2d;

public class DiffractionGratingLightSource extends LightSource {

	private int width;
	private DiffractionGrating myDiffractionGrating;
	private double latestAngleIn = 0;
	private int lastRayInSign = 1;

	public DiffractionGratingLightSource(Vector2d origin, int width, boolean fixedPosition, DiffractionGrating diffractionGrating) {
		super(origin, fixedPosition);
		this.width = width;
		this.myDiffractionGrating = diffractionGrating;
		calculateLight();
	}

	public void calculateLight(){
		super.clearLightRays();

		if(myDiffractionGrating.getWaveLength() == -1){
			return;
		}

		boolean rightFacing = Math.toDegrees(totalRotation) % 360 > -90 && Math.toDegrees(totalRotation) % 360 < 90;

		//Från https://en.wikipedia.org/wiki/Diffraction_grating
		for(int m = 0; m <= myDiffractionGrating.getNumMax(); m++){

			double angleOut = Math.asin(Math.sin(latestAngleIn)- m * myDiffractionGrating.getWaveLength() / myDiffractionGrating.getSlitsPerUnitDistance());

			if(Double.isNaN(angleOut)){
				continue;
			}
			double dy = Math.tan(angleOut);


			if((lastRayInSign == 1 &&  rightFacing) || (lastRayInSign == -1) && !rightFacing) {
				super.addLightRay(new Vector2d(width/2 + 2, 0), new Vector2d(1, dy));
				super.addLightRay(new Vector2d(width/2 + 2, 0), new Vector2d(1, -dy));
			}else {
				super.addLightRay(new Vector2d(-width/2 - 2, 0), new Vector2d(-1, dy));
				super.addLightRay(new Vector2d(-width/2 - 2, 0), new Vector2d(-1, -dy));
			}
		}

		super.setWaveLength(myDiffractionGrating.getWaveLength());
		super.restoreRotation();

		myDiffractionGrating.setWaveLength(-1);
	}

	@Override
	public void clearLightRays(){
		super.clearLightRays();
	}

	public void calculateInAngle(Vector2d ray){
		Vector2d lineSegment = myDiffractionGrating.getSegment(0);
		double cross = lineSegment.crossSign(ray);
		//convert the line to a normal:
		lineSegment.rotate(cross*Math.PI / 2).normalize();
		latestAngleIn = ray.angleTo(lineSegment);
		if(ray.x >= 0){
			lastRayInSign = 1;
		}else {
			lastRayInSign = -1;
		}
	}
}
