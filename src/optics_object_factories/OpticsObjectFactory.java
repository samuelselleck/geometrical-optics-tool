package optics_object_factories;
import java.util.Map;
import java.util.TreeMap;

import gui.Main;
import gui.OpticsController;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
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
	private CheckBox positionFixed;
	
	public OpticsObjectFactory() {
		this.setPadding(new Insets(20, 20, 20, 20));
		sliders = new TreeMap<>();
		
		top = new VBox();
		VBox.setVgrow(top, Priority.ALWAYS);
		top.setAlignment(Pos.TOP_LEFT);
		VBox bot = new VBox();
		VBox.setVgrow(bot, Priority.ALWAYS);
		bot.setAlignment(Pos.BOTTOM_CENTER);
		
		positionFixed = new CheckBox("Fixed position");
		if(Main.properties.getProperty("admin").equals("true")) {
			bot.getChildren().add(positionFixed);
		}
		
		this.getChildren().addAll(top, bot);
		
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
	
	protected boolean fixedPos() {
		return positionFixed.selectedProperty().get();
	}

	public void setSliderListener(EventHandler e) {
		for(Slider s: sliders.values()) {
			s.setOnMousePressed(e);
		}
	}
}
