package gui.optics_object_creators;

import java.util.ArrayList;
import java.util.List;

import gui.Main;
import javafx.geometry.Point2D;
import javafx.scene.control.ComboBox;
import model.LensMaterial;
import model.optics_objects.Lens;

public abstract class LensCreator extends OpticsObjectCreator {
	
	GraphElement graph = new GraphElement(
			Main.getIntProperty("minwavelength"), 
			Main.getIntProperty("maxwavelength"), 
			100);
	
	public LensCreator() {
		
		graph.getYAxis().setTickLabelsVisible(true);
		addElement(graph);
		
		
		addProperty("Material Index");
		
		ComboBox<LensMaterial> materialsBox = new ComboBox<LensMaterial>(Lens.MATERIALS);
		materialsBox.setPrefWidth(Double.MAX_VALUE);
		
		materialsBox.setOnAction(e -> {
			getProperty("Material Index").set(materialsBox.getSelectionModel().getSelectedIndex());
		});
		
		addElement(materialsBox);
		
		addSlider("Refraction Multiplier", 1, 1000, 500);
		getProperty("Refraction Multiplier").addListener(e -> updateGraph());
		
		getProperty("Material Index").addListener((s, o, n) -> {
			updateGraph();
			materialsBox.getSelectionModel().select(n.intValue());
		});
		
		materialsBox.getSelectionModel().select(0);
		
		updateGraph();
	}
	
	private void updateGraph() {
		List<Point2D> func = new ArrayList<>();
		for(double x = Main.getIntProperty("minwavelength"); x <= Main.getIntProperty("maxwavelength"); x++) {
			
			int index = (int)get("Material Index");
			double y = Lens.MATERIALS.get(index < Lens.MATERIALS.size() ? index : 0)
					.refraction(x, get("Refraction Multiplier"));
			func.add(new Point2D(x, y));
		}
		graph.setData(func);
	}
}
