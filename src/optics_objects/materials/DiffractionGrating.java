package optics_objects.materials;

import optics_objects.lights.DiffractionGratingLightSource;
import optics_objects.templates.Wall;
import util.Vector2d;

public class DiffractionGrating extends Wall {
    private static final long serialVersionUID = 1L;

    private DiffractionGratingLightSource lightSource;
    private double slitsPerUnitDistance;
    private int numMax;

    public DiffractionGrating(Vector2d origin, double width, double height, double slitsPerUnitDistance, int numMax, boolean fixedPosition) {
        super(origin, fixedPosition);
        setPoints(width, height);
        this.slitsPerUnitDistance = slitsPerUnitDistance;
        this.numMax = numMax;
        this.lightSource = new DiffractionGratingLightSource(origin, (int)width, slitsPerUnitDistance, numMax, fixedPosition, this);
    }

    public void calculateRays(int waveLength){
        lightSource.updateLightSource(slitsPerUnitDistance, numMax, waveLength);
    }

    public DiffractionGratingLightSource getLightSource(){
        return lightSource;
    }

    public void updateGrating(double width, double height, double slits, int numMax, int wavelength) {
        this.slitsPerUnitDistance = slits;
        this.numMax = numMax;
        setPoints(width, height);
        calculateRays(wavelength);
    }

    @SuppressWarnings("Duplicates")
    private void setPoints(double width, double height) {
        clearPoints();
        points.add(new Vector2d(-width / 2, -height / 2));
        points.add(new Vector2d(-width / 2, height / 2));
        points.add(new Vector2d(width / 2, height / 2));
        points.add(new Vector2d(width / 2, -height / 2));

        super.restoreRotation();
        super.createBounds();
    }
}