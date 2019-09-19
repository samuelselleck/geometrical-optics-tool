package gui.optics_object_creators;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.control.Slider;
import model.optics_objects.LightSource;

public abstract class LightSourceCreator extends OpticsObjectCreator {
	private GraphElement graph;
	
	public LightSourceCreator() {
		graph = new GraphElement();
		this.addElement(graph);
		
		Slider wavelength = addSlider("Wavelength", LightSource.lightWaveMin(), LightSource.lightWaveMax(), LightSource.lightWaveDefault());
		Slider bandwidth = addSlider("Bandwidth", 0, LightSource.lightWaveMax() - LightSource.lightWaveMin(), 0);
		
		wavelength.valueProperty().addListener(e -> {
			double distToEdge = Math.min(
					wavelength.getValue() - LightSource.lightWaveMin(),
					LightSource.lightWaveMax() -  wavelength.getValue());
			bandwidth.setValue(Math.min(bandwidth.getValue(), distToEdge*2));
			setData(wavelength.getValue(), bandwidth.getValue());
		});
		bandwidth.valueProperty().addListener(e -> {
			double dist = bandwidth.getValue()/2;
			if(wavelength.getValue() - LightSource.lightWaveMin() < dist) {
				wavelength.setValue(LightSource.lightWaveMin() + dist);
			} else if (LightSource.lightWaveMax() - wavelength.getValue() < dist){
				wavelength.setValue(LightSource.lightWaveMax() - dist);
			}
			setData(wavelength.getValue(), bandwidth.getValue());
		});
		
		setData(wavelength.getValue(), bandwidth.getValue());
	}
	
	private void setData(double wavelength, double bandwidth) {
		bandwidth = Math.max(bandwidth, 18);
		List<Point2D> normalCurve = new ArrayList<>();
		for(double x = LightSource.lightWaveMin() - wavelength; x <= LightSource.lightWaveMax() + wavelength; x++) {
			double y = Math.exp(-12*(x - wavelength)*(x - wavelength)/bandwidth/bandwidth);
			normalCurve.add(new Point2D(x, y));
		}
		graph.setData(normalCurve);
	}

}
