package optics_objects.materials;

import optics_objects.lights.DiffractionGratingLightSource;
import optics_objects.templates.Wall;
import util.Vector2d;

public class DiffractionGrating extends Wall {
    private static final long serialVersionUID = 1L;

    private DiffractionGratingLightSource lightSource;
    private double slitsPerUnitDistance;
    private int numMax, waveLength = -1;

    public DiffractionGrating(Vector2d origin, double width, double height, double slitsPerUnitDistance, int numMax, boolean fixedPosition) {
        super(origin, fixedPosition);
        setPoints(width, height);
        this.slitsPerUnitDistance = slitsPerUnitDistance;
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

        lightSource.calculateLight();
    }

    public void setWaveLength(int waveLength){
        this.waveLength = waveLength;
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

    @Override
    public void setOrigin(double x, double y) {
        lightSource.setOrigin(x, y);
        if(!fixedPosition) {
            this.origin.setTo(x, y);
        }
    }

    @SuppressWarnings("Duplicates")
    private void setPoints(double width, double height) {
        clearPoints();
        points.add(new Vector2d(-width / 2, -height / 2));
        points.add(new Vector2d(-width / 2, height / 2));
        points.add(new Vector2d(width / 2, height / 2));
        points.add(new Vector2d(width / 2, -height / 2));
        points.add(new Vector2d(-width / 2, -height / 2));
        points.add(new Vector2d(-width / 2, height / 2));
        points.add(new Vector2d(width / 2, height / 2));
        points.add(new Vector2d(width / 2, -height / 2));


        int nrofSlits=(int) slitsPerUnitDistance/2000;
        if(nrofSlits<2) {
            nrofSlits=2;
        }

        int spacing=15/nrofSlits;
        int negative=1;
        for(int i=0;i<nrofSlits;i++) {
            if(i==0) {
                points.add(new Vector2d(width / 2, 0));
                points.add(new Vector2d(-width / 2, 0));
            }else {
                points.add(new Vector2d(-width / 2, i*spacing*negative));
                points.add(new Vector2d(width / 2, i*spacing*negative));
                if(negative==1) {
                    negative=-1;
                }else {
                    negative=1;
                }
                points.add(new Vector2d(width / 2, i*spacing*negative));
                points.add(new Vector2d(-width / 2, i*spacing*negative));
            }


        }
        super.restoreRotation();
        super.createBounds();
    }
}