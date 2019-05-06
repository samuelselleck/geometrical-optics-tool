package optics_object_factories;

import optics_objects.templates.OpticsObject;
import optics_objects.materials.DiffractionGrating;
import util.Vector2d;

public class DiffractionGratingFactory extends OpticsObjectFactory {

    private static final int WIDTH = 10;


    public DiffractionGratingFactory() {
        addSlider("Slits per unit distance", 100, 10000, 500);
        addSlider("Height", 100, 400, 200);
    }

    @Override
    public OpticsObject getOpticsObject(Vector2d origin) {
        return new DiffractionGrating(origin,
                WIDTH, getParam("Height"), getParam("Slits per unit distance"), fixedPos());
    }

    @Override
    public void updateOpticsObject(OpticsObject object) {
        ((DiffractionGrating)object).setPoints(WIDTH, getParam("Height"));
        ((DiffractionGrating)object).setSlitsPerUnitDistance(getParam("Slits per unit distance"));
    }
}
