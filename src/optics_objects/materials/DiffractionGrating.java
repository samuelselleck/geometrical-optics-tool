package optics_objects.materials;

import optics_objects.lights.DiffractionGratingLightSource;
import optics_objects.templates.Wall;
import util.Vector2d;

public class DiffractionGrating extends Wall {
    private static final long serialVersionUID = 1L;

    private DiffractionGratingLightSource lightSource;
    private double slitsPerUnitDistance;
    private int numMax, waveLength = -1;
    private double width, height;

    public DiffractionGrating(Vector2d origin, double width, double height, double slitsPerUnitDistance, int numMax, boolean fixedPosition) {
        super(origin, fixedPosition);
        setPoints(width, height);
        this.slitsPerUnitDistance = slitsPerUnitDistance;
        this.width = width;
        this.height = height;
        this.numMax = numMax;
        this.lightSource = new DiffractionGratingLightSource(origin, (int)width, fixedPosition, this);
    }

    public DiffractionGratingLightSource getLightSource(){
        return lightSource;
    }

    public void updateGrating(double width, double height, double slits, int numMax, int waveLength) {
        this.slitsPerUnitDistance = slits;
        this.numMax = numMax;
        this.waveLength = waveLength;
        setPoints(width, height);

        lightSource.setOrigin( this.origin );
        lightSource.rotate(this.getTotalRotation() - lightSource.getTotalRotation());

        lightSource.calculateLight();
    }

    public void setWaveLength(int waveLength){
        this.waveLength = waveLength;
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

    public int getWaveLength(){
        return waveLength;
    }

    public double getSlitsPerUnitDistance(){
        return slitsPerUnitDistance;
    }

    public int getNumMax(){
        return numMax;
    }

    @Override
    public void rotate(double angle) {
        totalRotation += angle;
        rotateOp(angle);
        lightSource.rotate(angle);
    }
}