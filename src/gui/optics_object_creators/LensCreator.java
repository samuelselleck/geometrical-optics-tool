package gui.optics_object_creators;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.geometry.Point2D;
import javafx.scene.control.ComboBox;
import model.optics_objects.Lens.SellmeierCoefficients;
import model.optics_objects.LightSource;

public abstract class LensCreator extends OpticsObjectCreator {
	
	GraphElement graph = new GraphElement();
	
	public LensCreator() {
		
		graph.getYAxis().setTickLabelsVisible(true);
		addElement(graph);
		
		
		addProperty("Material Index");
		
		ComboBox<SellmeierCoefficients> materialsBox = new ComboBox<SellmeierCoefficients>(
				FXCollections.observableArrayList(SellmeierCoefficients.values()));
		materialsBox.setPrefWidth(Double.MAX_VALUE);
		
		materialsBox.setOnAction(e -> {
			getProperty("Material Index").set(materialsBox.getSelectionModel().getSelectedIndex());
		});
		
		addElement(materialsBox);
		
		addSlider("Refraction Multiplier", 1, 1000, 500);
		getProperty("Refraction Multiplier").addListener((s, o, n) -> update());
		
		getProperty("Material Index").addListener((s, o, n) -> {
			update();
			materialsBox.getSelectionModel().select(n.intValue());
		});
		
		materialsBox.getSelectionModel().select(0);
		
		update();
	}
	
	private void update() {
		List<Point2D> func = new ArrayList<>();
		for(double x = LightSource.lightWaveMin(); x <= LightSource.lightWaveMax(); x++) {
			
			double y = SellmeierCoefficients.values()[getProperty("Material Index").intValue()]
					.refraction(x, getProperty("Refraction Multiplier").get());
			func.add(new Point2D(x, y));
		}
		graph.setData(func);
	}
}
