package optics_objects.lights;

import optics_logic.LightRay;
import optics_objects.materials.DiffractionGrating;
import optics_objects.templates.LightSource;
import util.Vector2d;

public class DiffractionGratingLightSource extends LightSource {

	private static final double METER_TO_NANOMETER = Math.pow(10, 9);
	private static final double NANOMETER_TO_METER = Math.pow(10, -9);

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
		for(int m = 0; m < myDiffractionGrating.getNumMax(); m++){

			double dy = Math.atan(Math.sin(latestAngleIn) - m * myDiffractionGrating.getWaveLength() / myDiffractionGrating.getSlitsPerUnitDistance());

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
		System.out.println("Angle:" + Math.toDegrees(latestAngleIn));
	}
}
