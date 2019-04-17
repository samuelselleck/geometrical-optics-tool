package optics_object_factories;

import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import optics_objects.materials.ConcaveLens;
import optics_objects.materials.ConvexLens;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public class ConcaveLensFactory extends OpticsObjectFactory {

	public ConcaveLensFactory() {
		addSlider("Diameter", 103, 800, 180);
		addSlider("Width", 11, 100, 10);
		addSlider("Radius 1", 55, 800, 300);
		addSlider("Radius 2", 55, 800, 300);
		addSlider("Refractionindex", 1, 3, 1.5);
		
		addCheckBox("Show optical axis");
	}
	
	private boolean correctParams() {
		return getParam("Radius 1")*2 >= getParam("Diameter") && getParam("Radius 2")*2 >= getParam("Diameter");
	}


	@Override
	public OpticsObject getOpticsObject(Vector2d origin) {
		double d = getParam("Diameter");
		double r1 = getParam("Radius 1");
		double r2 = getParam("Radius 2");
		if(r1*2 >= d && r2*2 >= d) {
			return new ConcaveLens(origin, d, getParam("Width"), r1, r2,
					getParam("Refractionindex"), fixedPos(), getBoxParam("Show optical axis"));
		} else {
			return null;
		}
	}

	@Override
	public void updateOpticsObject(OpticsObject object) {
		double d = getParam("Diameter");
		double r1 = getParam("Radius 1");
		double r2 = getParam("Radius 2");
		if(r1*2 >= d && r2*2 >= d) {
		((ConcaveLens)object).setPoints(d, getParam("Width"), r1, r2);
		}
		((ConcaveLens)object).setRefractionIndex(getParam("Refractionindex"));
		((ConcaveLens)object).showOpticalAxis(getBoxParam("Show optical axis"));
	}
	
	public void setListeners(EventHandler e) {
		super.setListeners(e);
		String[] relSliders = {"Radius 1", "Radius 2", "Diameter"};
		
		EventHandler onIncorrectParams = event ->{
			if(!correctParams()) {
				for(String s: relSliders)
					sliders.get(s).setStyle("-fx-background-color:red");
			}else {
				for(String s: relSliders)
				sliders.get(s).setStyle("");
			}
		};
	
		for(String s: relSliders) {
			sliders.get(s).addEventHandler(MouseEvent.MOUSE_CLICKED, onIncorrectParams);
			sliders.get(s).addEventFilter(MouseEvent.MOUSE_DRAGGED, onIncorrectParams);
		}
	}
	
}
