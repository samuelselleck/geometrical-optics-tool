package optics_object_factories;

import javafx.geometry.Insets;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import optics_objects.templates.OpticsObject;
import util.Vector2d;

public abstract class OpticsObjectFactory extends VBox {
	private Slider[] sliders;

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

		for (int i = 0; i < sliders.length; i++) {
			Text text = new Text(names[i]);
			text.setFill(Color.WHITE);
			this.getChildren().add(text);
			this.getChildren().add(sliders[i]);
		}
	}

	public int getParamCount() {
		return sliders.length;
	}

	public double getSliderValue(int index) {
		return sliders[index].getValue();
	}
}
