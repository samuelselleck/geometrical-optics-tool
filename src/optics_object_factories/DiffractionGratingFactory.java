package optics_object_factories;

import optics_objects.templates.LightSource;
import optics_objects.templates.OpticsObject;
import optics_objects.materials.DiffractionGrating;
import util.Vector2d;

public class DiffractionGratingFactory extends OpticsObjectFactory {

    private static final int GRATING_WIDTH = 10;


    public DiffractionGratingFactory() {
        addSlider("Slit separation (nm)", 1000, 10000, 2000);
        addSlider("Height", 100, 400, 200);
        addSlider("Number of maximums",0,11,4);
    }

    @Override
    public OpticsObject getOpticsObject(Vector2d origin) {
        return new DiffractionGrating(origin,
                GRATING_WIDTH, getParam("Height"), getParam("Slit separation (nm)"), getIntParam("Number of maximums"), fixedPos());
    }

    @Override
    public void updateOpticsObject(OpticsObject object) {
        ((DiffractionGrating)object).updateGrating(GRATING_WIDTH, getParam("Height"),
                getParam("Slit separation (nm)"), getIntParam("Number of maximums"), LightSource.lightWaveDefault());
    }
}
