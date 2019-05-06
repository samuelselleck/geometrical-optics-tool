package optics_objects.materials;

import java.util.ArrayList;import javafx.scene.canvas.GraphicsContext;
import optics_logic.LightRay;
import optics_logic.OpticsSettings;
import optics_objects.lights.DiffractionGratingLightSource;
import optics_objects.templates.Wall;
import util.Vector2d;

public class DiffractionGrating extends Wall {
    private static final long serialVersionUID = 1L;
    private double width;
    
    private DiffractionGratingLightSource diffSource;
    
    private ArrayList<LightRay> outLight;  
    

    public DiffractionGrating(Vector2d origin, double width, double height, double slitsPerUnitDistance, int nrMax, boolean fixedPosition) {
        super(origin, fixedPosition);
        setPoints(width, height);
        this.width=width;
        this.diffSource = new DiffractionGratingLightSource(origin, slitsPerUnitDistance, nrMax);
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
       // diffSource.setSlitsPerUnitSitance(slits);
    }

    public void updateRays(int wavelength){
    	
    }
    
    
}
