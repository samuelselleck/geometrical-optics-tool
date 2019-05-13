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

		for(int i = 0; i <= myDiffractionGrating.getNumMax(); i++){

			double dy=METER_TO_NANOMETER*Math.tan(Math.asin((i*myDiffractionGrating.getWaveLength()*NANOMETER_TO_METER)/myDiffractionGrating.getSlitsPerUnitDistance()));

			super.addLightRay(new Vector2d(width/2 + 2, 0), new Vector2d(1, -dy));
			super.addLightRay(new Vector2d(width/2 + 2, 0), new Vector2d(1, dy));
		}


		super.setWaveLength(myDiffractionGrating.getWaveLength());
		super.restoreRotation();

		myDiffractionGrating.setWaveLength(-1);
	}

	//Från https://en.wikipedia.org/wiki/Diffraction_grating
	private double calculateAngle(LightRay ray, double numSlits, int waveLength){
		return 0;
	}
}
