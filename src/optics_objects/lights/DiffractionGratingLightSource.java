package optics_objects.lights;

import optics_objects.materials.DiffractionGrating;
import optics_objects.templates.LightSource;
import util.Vector2d;

public class DiffractionGratingLightSource extends LightSource {

	private int width;
	private DiffractionGrating myDiffractionGrating;
	private double latestAngleIn = 0;

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

		//Från https://en.wikipedia.org/wiki/Diffraction_grating
		for(int m = 0; m <= myDiffractionGrating.getNumMax(); m++){

			double angleOut = Math.asin(Math.sin(latestAngleIn)- m * myDiffractionGrating.getWaveLength() / myDiffractionGrating.getSlitsPerUnitDistance());

			if(Double.isNaN(angleOut)){
				continue;
			}

			System.out.println("Wavelength: " + myDiffractionGrating.getWaveLength() + " AngleOut: " + Math.toDegrees(angleOut));

			double dy = Math.tan(angleOut);
			super.addLightRay(new Vector2d(width/2 + 2, 0), new Vector2d(1, dy));
			super.addLightRay(new Vector2d(width/2 + 2, 0), new Vector2d(1, -dy));
		}


		super.setWaveLength(myDiffractionGrating.getWaveLength());
		super.restoreRotation();

		myDiffractionGrating.setWaveLength(-1);
	}

	public void calculateInAngle(Vector2d ray){
		Vector2d lineSegment = myDiffractionGrating.getSegment(0);
		double cross = lineSegment.crossSign(ray);
		//convert the line to a normal:
		lineSegment.rotate(cross*Math.PI / 2).normalize();
		latestAngleIn = ray.angleTo(lineSegment);
		//System.out.println("InAngle:" + Math.toDegrees(latestAngleIn) + " source rotation: " + Math.toDegrees(this.getTotalRotation()) % 360);
	}
}
