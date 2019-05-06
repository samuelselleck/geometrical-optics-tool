package optics_objects.materials;

import optics_objects.templates.Wall;
import util.Vector2d;

public class DiffractionGrating extends Wall {
    private static final long serialVersionUID = 1L;
    private double slitsPerUnitDistance;

    public DiffractionGrating(Vector2d origin, double width, double height, double slitsPerUnitDistance, boolean fixedPosition) {
        super(origin, fixedPosition);
        this.slitsPerUnitDistance = slitsPerUnitDistance;
        setPoints(width, height);
    }

    @SuppressWarnings("Duplicates")
    public void setPoints(double width, double height) {
        clearPoints();
        points.add(new Vector2d(-width/2,-height/2));
        points.add(new Vector2d(-width/2, height/2));
        points.add(new Vector2d(width/2, height/2));
        points.add(new Vector2d(width/2, -height/2));

        super.restoreRotation();
        super.createBounds();
    }

    public void setSlitsPerUnitDistance(double slits){
        this.slitsPerUnitDistance = slits;
    }

    public void createRays(int wavelength){

    }
}
