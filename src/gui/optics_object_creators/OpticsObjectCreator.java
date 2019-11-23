package gui.optics_object_creators;
import java.util.Map;
import java.util.TreeMap;

import gui.Main;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
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
	private OpticsObject lastBound;
	
	public OpticsObjectCreator() {
		
		this.setPadding(new Insets(20, 20, 20, 20));
		sliders = new TreeMap<>();
		properties = new TreeMap<>();
		
		for(String p : OpticsObject.REQUIRED_PROPERTIES) {
			addProperty(p);
		}
		properties.get("Rotation").addListener((s, o, n) -> {
			double newValue = n.doubleValue();
			while(newValue < 0) {
				newValue += 360;
			}
			while(newValue >= 360) {
				newValue -= 360;
			}
			properties.get("Rotation").set(newValue);
		});
		top = new VBox();
		VBox.setVgrow(top, Priority.ALWAYS);
		top.setAlignment(Pos.TOP_LEFT);
		
		HBox bot = new HBox();
		Label xLabel = new Label("x:");
		TextField xPos = new TextField();
		xPos.setStyle("-fx-control-inner-background: black");
		bindTextFieldToDouble(xPos, properties.get("X"));
		Label yLabel = new Label("y:");
		TextField yPos = new TextField();
		yPos.setStyle("-fx-control-inner-background: black");
		bindTextFieldToDouble(yPos, properties.get("Y"));
		Label rLabel = new Label("r:");
		TextField rotation = new TextField();
		rotation.setStyle("-fx-control-inner-background: black");
		bindTextFieldToDouble(rotation, properties.get("Rotation"));
		
		bot.getChildren().addAll(xLabel, xPos, yLabel, yPos, rLabel, rotation);
		this.getChildren().addAll(top, bot);
		
	}
	
	private void bindTextFieldToDouble(TextField t, DoubleProperty d) {
		EventHandler<ActionEvent> setDouble = a -> {
			d.set(Double.parseDouble(t.getText().replace(",", ".")));
		};
		t.setOnAction(setDouble);
		d.addListener((l, o, n) -> {
			t.setText(String.format("%.2f", d.get()));
		});
	}

	public abstract OpticsObject getOpticsObject(Vector2d origin);
	public abstract boolean editsOpticsObject(OpticsObject obj);
	
	public void bind(OpticsObject obj) {
		
		 unbind();	 
		 for(Map.Entry<String, DoubleProperty> property : obj.getProperties().entrySet()) {
			
			if (properties.containsKey(property.getKey())) {
				
				DoubleProperty creatorProperty = properties.get(property.getKey());
				DoubleProperty objVal = property.getValue();
				
				creatorProperty.set(objVal.get());
				
				objVal.removeListener(updated);
			    objVal.bindBidirectional(creatorProperty);
				objVal.addListener(updated);
				
				
			} else {
				
				System.out.println("ERROR: could not find creator property to bind to: " + 
				property.getKey());
			}
		 }
		 
		 lastBound = obj;
		 
	}
	
	public void unbind() {
		if(lastBound != null) {
			for(Map.Entry<String, DoubleProperty> property : lastBound.getProperties().entrySet()) {
				
				if (properties.containsKey(property.getKey())) {
					
					DoubleProperty creatorProperty = properties.get(property.getKey());
					DoubleProperty objVal = property.getValue();
					
					creatorProperty.set(objVal.get());
					
				    objVal.unbindBidirectional(creatorProperty);
				} else {
					
					System.out.println("ERROR: could not find creator property to unbind from: " + 
					property.getKey());
				}
			 }
		}
		lastBound = null;
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
			tf.setMaxWidth(Main.DPCM*2);
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
	
	public double get(String name) {
		return getProperty(name).get();
	}
	
	protected void addElement(Node n) {
		top.getChildren().add(n);
	}

	protected Map<String, DoubleProperty> getInitializationProperties(Vector2d origin) {
		unbind();
		properties.get("X").set(origin.x/Main.DPCM);
		properties.get("Y").set(origin.y/Main.DPCM);
		properties.get("Rotation").set(1);
		properties.get("Rotation").set(0);
		return properties;
	}
}
