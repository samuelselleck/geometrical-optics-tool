package optics_object_factories;

import gui.Main;
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
	private Slider[] sliders;
	protected CheckBox positionFixed;
	
	public OpticsObjectFactory() {
		this.setPadding(new Insets(20, 20, 20, 20));
	}

	public abstract OpticsObject getOpticsObject(Vector2d origin);

	protected void setSliders(String[] names, Vector2d[] bounds, double[] startValues) {
		sliders = new Slider[startValues.length];
		for (int i = 0; i < sliders.length; i++) {
			sliders[i] = new Slider();
			HBox.setMargin(sliders[i], new Insets(100, 100, 100, 100));
			sliders[i].setMin(bounds[i].x);
			sliders[i].setMax(bounds[i].y);
			sliders[i].setValue(startValues[i]);
			;
			sliders[i].setShowTickLabels(true);
			sliders[i].setShowTickMarks(true);
			sliders[i].setMajorTickUnit(Math.abs(bounds[i].x - bounds[i].y + 1) / 4);
		}

		VBox top = new VBox();
		VBox.setVgrow(top, Priority.ALWAYS);
		top.setAlignment(Pos.TOP_LEFT);
		VBox bot = new VBox();
		VBox.setVgrow(bot, Priority.ALWAYS);
		bot.setAlignment(Pos.BOTTOM_CENTER);
		
		this.getChildren().addAll(top, bot);
		
		for (int i = 0; i < sliders.length; i++) {
			Text text = new Text(names[i]);
			text.setFill(Color.WHITE);
			top.getChildren().add(text);
			top.getChildren().add(sliders[i]);
		}
		
		positionFixed = new CheckBox("Fixed position");
		if(Main.ADMIN)
		bot.getChildren().add(positionFixed);
	}

	public int getParamCount() {
		return sliders.length;
	}

	public double getSliderValue(int index) {
		return sliders[index].getValue();
	}
}
