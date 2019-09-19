package gui.optics_object_creators;

import java.util.List;
import java.util.stream.Collectors;

import gui.Main;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import model.optics_objects.LightSource;

public class GraphElement extends LineChart<Number, Number> {

	public GraphElement() {	
		super(new NumberAxis(LightSource.lightWaveMin(), LightSource.lightWaveMax(), 100), new NumberAxis());
		this.setMinHeight(Main.HEIGHT/6);
		this.setPrefHeight(Main.HEIGHT/6);
		this.setLegendVisible(false);
		this.getYAxis().setTickLabelsVisible(false);
		this.getYAxis().setVisible(false);
		this.setCreateSymbols(false);
		this.setAnimated(false);
		this.setPadding(new Insets(0, 15, 0, 0));
	}
	
	public void setData(List<Point2D> data) {
		this.getData().clear();
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.getData().addAll(
				data.stream()
				.map(e -> new XYChart.Data<Number, Number>(e.getX(), e.getY()))
				.collect(Collectors.toList()));
		this.getData().add(series);
		series.getNode().setStyle("-fx-stroke: white;");
	}
}
