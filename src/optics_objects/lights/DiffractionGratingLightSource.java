package optics_objects.lights;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import optics_logic.LightRay;
import optics_logic.OpticsModel;
import optics_logic.OpticsSettings;
import optics_objects.materials.DiffractionGrating;
import optics_objects.templates.LightSource;
import util.Vector2d;

public class DiffractionGratingLightSource extends LightSource {

	private int waveLength = -1;
	private int width;
	private DiffractionGrating myDiffractionGrating;

	public DiffractionGratingLightSource(Vector2d origin, int width, double slitsPerUnitDistance, int numMax, boolean fixedPosition, DiffractionGrating rootGrating) {
		super(origin, fixedPosition);
		this.width = width;
		myDiffractionGrating = rootGrating;
		updateLightSource(slitsPerUnitDistance, numMax, waveLength);
	}

	public void updateLightSource(double slits, int numMax, int waveLength){
		super.clearLightRays();

		for(int i = 0; i < numMax; i++){

		}
		super.addLightRay(new Vector2d(50, 10), new Vector2d(1, 1));
		super.addLightRay(new Vector2d(50, 20), new Vector2d(1, 1));

		System.out.println(super.light.size());

		super.setWaveLength(waveLength);
		super.restoreRotation();
	}

	//Från https://en.wikipedia.org/wiki/Diffraction_grating
	private double calculateAngle(LightRay ray, double numSlits, int waveLength){
		return 0;
	}
}
