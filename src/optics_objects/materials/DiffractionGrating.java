package optics_objects.materials;

import java.util.ArrayList;import javafx.scene.canvas.GraphicsContext;
import optics_logic.LightRay;
import optics_logic.OpticsSettings;
import optics_objects.templates.Wall;
import util.Vector2d;

public class DiffractionGrating extends Wall {
    private static final long serialVersionUID = 1L;
    private double slitsPerUnitDistance;
    private int nrMax;
    private double width;
    
    private ArrayList<LightRay> outLight;  
    

    public DiffractionGrating(Vector2d origin, double width, double height, double slitsPerUnitDistance, int nrMax, boolean fixedPosition) {
        super(origin, fixedPosition);
        this.slitsPerUnitDistance = slitsPerUnitDistance;
        setPoints(width, height);
        this.nrMax=nrMax;
        this.width=width;
 
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

    public void draw(GraphicsContext gc, OpticsSettings settings) {
    	super.draw(gc, settings);
    	
    	if(outLight == null) {
    		return;
    	}
    	for(LightRay lr : outLight) {
    		lr.draw(gc, settings.drawOnlyHitting());
    	}
    }
    
    public void updateRays(int wavelength){
    	outLight=new ArrayList<LightRay>();
    	if(outLight.size() == 0) {
    		for(int i=0;i<=nrMax;i++) {
        		outLight.add(new LightRay(this.origin, new Vector2d(width/2, 0), new Vector2d(1, 1))); 
        	}
    	}
    }
    
    
}
