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
		
		getProperty("Material Index").addListener((s, o, n) -> {
			update();
			materialsBox.getSelectionModel().select(n.intValue());
		});
		
		materialsBox.getSelectionModel().select(0);
		
		update();
	}
	
	private void update() {
		List<Point2D> func = new ArrayList<>();
		for(double x = Main.getIntProperty("minwavelength"); x <= Main.getIntProperty("maxwavelength"); x++) {
			
			double y = Lens.MATERIALS.get((int)get("Material Index"))
					.refractionSellmeier(x);
			func.add(new Point2D(x, y));
		}
		graph.setData(func);
	}
}
