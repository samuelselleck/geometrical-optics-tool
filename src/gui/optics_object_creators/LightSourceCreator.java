package gui.optics_object_creators;

import java.util.ArrayList;
import java.util.List;

import gui.Main;
import javafx.geometry.Point2D;
import javafx.scene.control.Slider;

public abstract class LightSourceCreator extends OpticsObjectCreator {
	private GraphElement graph;
	
	public LightSourceCreator() {
		
		int min = Main.getIntProperty("minwavelength");
		int max = Main.getIntProperty("maxwavelength");
		
		graph = new GraphElement(min, max, 100);
		this.addElement(graph);
		
		Slider wavelength = addSlider("Wavelength", min, max, Main.getIntProperty("defaultwavelength"));
		Slider bandwidth = addSlider("Bandwidth", 0, max - min, 0);
		
		wavelength.valueProperty().addListener(e -> {
			double distToEdge = Math.min(
					wavelength.getValue() - min,
					max -  wavelength.getValue());
			bandwidth.setValue(Math.min(bandwidth.getValue(), distToEdge*2));
			setData(wavelength.getValue(), bandwidth.getValue());
		});
		bandwidth.valueProperty().addListener(e -> {
			double dist = bandwidth.getValue()/2;
			if(wavelength.getValue() - min < dist) {
				wavelength.setValue(min + dist);
			} else if (max - wavelength.getValue() < dist){
				wavelength.setValue(max - dist);
			}
			setData(wavelength.getValue(), bandwidth.getValue());
		});
		
		setData(wavelength.getValue(), bandwidth.getValue());
	}
	
	private void setData(double wavelength, double bandwidth) {
		bandwidth = Math.max(bandwidth, 18);
		List<Point2D> normalCurve = new ArrayList<>();
		
		for(double x = Main.getIntProperty("minwavelength") - wavelength;
				x <= Main.getIntProperty("maxwavelength") + wavelength; x++) {
			double y = Math.exp(-12*(x - wavelength)*(x - wavelength)/bandwidth/bandwidth);
			normalCurve.add(new Point2D(x, y));
		}
		graph.setData(normalCurve);
	}

}
