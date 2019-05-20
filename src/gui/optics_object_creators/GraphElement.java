package gui.optics_object_creators;

import java.util.List;

import gui.Main;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import model.optics_objects.LightSource;
import util.Vector2d;

public class GraphElement extends LineChart<Number, Number> {

	public GraphElement() {
		super(new NumberAxis(LightSource.lightWaveMin(), LightSource.lightWaveMax(), 100), new NumberAxis());
		this.setPrefHeight(Main.HEIGHT/4);
		this.setLegendVisible(false);
		this.getYAxis().setTickLabelsVisible(false);
		this.getYAxis().setVisible(false);
		this.setPadding(new Insets(0, 15, 0, 0));
	}
	
	public void setData(List<Vector2d> data) {
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		this.getData().add(series);
		//TODO
	}
}
