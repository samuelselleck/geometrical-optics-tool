package optics_object_factories;
import java.util.Map;
import java.util.TreeMap;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public abstract class OpticsObjectFactory extends VBox {
	private Map<String, Slider> sliders;
	private VBox top;
	
	public OpticsObjectFactory() {
		this.setPadding(new Insets(20, 20, 20, 20));
		sliders = new TreeMap<>();
		
		top = new VBox();
		VBox.setVgrow(top, Priority.ALWAYS);
		top.setAlignment(Pos.TOP_LEFT);
		this.getChildren().addAll(top);
		
	}

	public abstract OpticsObject getOpticsObject(Vector2d origin);

	protected void addSlider(String name, double min, double max, double start) {
		Text text = new Text(name);
		text.setFill(Color.WHITE);
		
		Slider newSlider = new Slider();
		HBox.setMargin(newSlider, new Insets(100, 100, 100, 100));
		newSlider.setMin(min);
		newSlider.setMax(max);
		newSlider.setValue(start);
		
		newSlider.setShowTickLabels(true);
		newSlider.setShowTickMarks(true);
		newSlider.setMajorTickUnit(Math.abs(max - min + 1) / 4);

		sliders.put(name, newSlider);
		top.getChildren().add(text);
		top.getChildren().add(newSlider);		
	}

	protected double getParam(String name) {
		return sliders.get(name).getValue();
	}
	
	protected int getIntParam(String name) {
		return (int)Math.round(getParam(name));
	}
}
