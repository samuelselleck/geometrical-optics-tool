package gui.optics_object_creators;
import java.util.Map;
import java.util.TreeMap;

import gui.Main;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
	private Map<String, DoubleProperty> properties;
	private VBox top;
	private InvalidationListener updated;
	
	public OpticsObjectCreator() {
		this.setPadding(new Insets(20, 20, 20, 20));
		sliders = new TreeMap<>();
		properties = new TreeMap<>();
		
		top = new VBox();
		VBox.setVgrow(top, Priority.ALWAYS);
		top.setAlignment(Pos.TOP_LEFT);
		this.getChildren().addAll(top);
		
	}

	public abstract OpticsObject getOpticsObject(Vector2d origin);
	public abstract boolean editsOpticsObject(OpticsObject obj);
	
	public void bind(OpticsObject obj) {
		
		 for(Map.Entry<String, DoubleProperty> property : obj.getProperties().entrySet()) {
			 
			if (properties.containsKey(property.getKey())) {
				
				DoubleProperty creatorProperty = properties.get(property.getKey());
				DoubleProperty objVal = property.getValue();
				
				creatorProperty.set(objVal.get());
				creatorProperty.removeListener(updated);
				objVal.bind(creatorProperty);
				creatorProperty.addListener(updated);
				
			} else {
				
				System.out.println("ERROR: could not find creator property to bind to");
			}
		 }
		 
	}
	
	public void onUpdated(InvalidationListener updated) {
		this.updated = updated;
	}
	
	protected Slider addSlider(String name, double min, double max, double start) {
		Text text = new Text(name);
		text.setFill(Color.WHITE);
		
		Slider newSlider = new Slider();
		newSlider.setMin(min);
		newSlider.setMax(max);
		newSlider.setValue(start);
		
		newSlider.setShowTickLabels(true);
		newSlider.setShowTickMarks(true);
		newSlider.setMajorTickUnit(Math.round(max - min)/4);

		sliders.put(name, newSlider);
		
		HBox box = new HBox();
		box.getChildren().add(text);
		
		if(Main.isActive("propertytextfields")) {
			
			TextField tf = new TextField();
			tf.setMaxWidth(Main.WIDTH/15);
			tf.setStyle("-fx-control-inner-background: black");
			InvalidationListener updateTextField = e -> {
				tf.setText(String.format("%.2f", newSlider.getValue()));
			};
			updateTextField.invalidated(null);
			newSlider.valueProperty().addListener(updateTextField);
			
			tf.setOnAction(e -> {
				double val = Double.parseDouble(tf.getText().replace(',', '.'));
				newSlider.setValue(val);
				tf.positionCaret(tf.getLength());
			});
			
			
			Pane spacing = new Pane();
			HBox.setHgrow(spacing, Priority.SOMETIMES);
			HBox.setHgrow(tf, Priority.ALWAYS);
			box.getChildren().addAll(spacing, tf);
			box.setPadding(new Insets(10, 0, 10, 0));
		}
		
		top.getChildren().add(box);
		top.getChildren().add(newSlider);		
		
		addProperty(name, newSlider.valueProperty());
		
		return newSlider;
	}
	
	protected Slider addSlider(String name, double min, double max, double start, boolean intValues) {
		Slider slider = addSlider(name, min, max, start);
		
		if(intValues) {
			slider.valueProperty().addListener((obs, oldval, newVal) ->
		    slider.setValue(Math.round(newVal.doubleValue())));
		}
		
		return slider;
	}
	
	public void addProperty(String name, DoubleProperty value) {
		properties.put(name, value);
	}
	
	public void addProperty(String name) {
		addProperty(name, new SimpleDoubleProperty());
	}
	
	public DoubleProperty getProperty(String name) {
		return properties.get(name);
	}
	
	protected void addElement(Node n) {
		top.getChildren().add(n);
	}

	protected Map<String, DoubleProperty> getCreatorProperties() {
		return properties;
	}
	
	protected double getParam(String name) {
		return sliders.get(name).getValue();
	}
	
	protected int getIntParam(String name) {
		return (int)Math.round(getParam(name));
	}
}
