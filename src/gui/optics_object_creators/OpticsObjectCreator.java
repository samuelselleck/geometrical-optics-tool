package gui.optics_object_creators;
import java.util.Map;
import java.util.TreeMap;

import gui.Main;
import gui.OpticsView;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.optics_objects.OpticsObject;
import util.Vector2d;

public abstract class OpticsObjectCreator extends VBox {
	private Map<String, Slider> sliders;
	private VBox top;
	private InvalidationListener updated;
	
	public OpticsObjectCreator() {
		this.setPadding(new Insets(20, 20, 20, 20));
		sliders = new TreeMap<>();
		
		top = new VBox();
		VBox.setVgrow(top, Priority.ALWAYS);
		top.setAlignment(Pos.TOP_LEFT);
		this.getChildren().addAll(top);
		
	}

	public abstract OpticsObject getOpticsObject(Vector2d origin);
	public abstract boolean editsOpticsObject(OpticsObject obj);
	
	public void bind(OpticsObject obj) {
		 for(Map.Entry<String, DoubleProperty> property : obj.getProperties().entrySet()) {
			DoubleProperty sliderVal = sliders.get(property.getKey()).valueProperty();
			DoubleProperty objVal = property.getValue();
		    sliderVal.set(objVal.get());
		    sliderVal.removeListener(updated);
		    objVal.bind(sliderVal);
		    sliderVal.addListener(updated);
		 }
	}
	
	public void onUpdated(InvalidationListener updated) {
		this.updated = updated;
	}
	
	protected Slider addSlider(String name, double min, double max, double start) {
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
		
		HBox box = new HBox();
		box.getChildren().add(text);
		
		if(Main.properties.getProperty("propertytextfields").equals("true")) {
			
			TextField tf = new TextField();
			tf.setMaxWidth(Main.WIDTH/15);
			tf.setStyle("-fx-control-inner-background: black");
			InvalidationListener updateTextField = e -> {
				tf.setText(String.format("%.2f", newSlider.getValue()));
			};
			newSlider.valueProperty().addListener(updateTextField);
			updateTextField.invalidated(null);
			
			EventHandler<ActionEvent> commitValue = e -> {
				double val = Double.parseDouble(tf.getText().replace(',', '.'));
				newSlider.setValue(val);
				tf.positionCaret(tf.getLength());
			};
			tf.setOnAction(commitValue);
			
			
			Pane spacing = new Pane();
			HBox.setHgrow(spacing, Priority.SOMETIMES);
			HBox.setHgrow(tf, Priority.ALWAYS);
			box.getChildren().addAll(spacing, tf);
			box.setPadding(new Insets(10, 0, 10, 0));
		}
		
		top.getChildren().add(box);
		top.getChildren().add(newSlider);		
		return newSlider;
	}
	
	protected void addElement(Node n) {
		top.getChildren().add(n);
	}

	protected Map<String, DoubleProperty> getSliderProperties() {
		Map<String, DoubleProperty> properties = new TreeMap<>();
		for(Map.Entry<String, Slider> entry : sliders.entrySet()) {
			properties.put(entry.getKey(), entry.getValue().valueProperty());
		}
		
		return properties;
	}
	protected double getParam(String name) {
		return sliders.get(name).getValue();
	}
	
	protected int getIntParam(String name) {
		return (int)Math.round(getParam(name));
	}
}
