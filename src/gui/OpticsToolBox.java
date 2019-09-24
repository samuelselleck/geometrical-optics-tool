package gui;

import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class OpticsToolBox extends ToolBar {

	public OpticsToolBox(OpticsEnvironment opticsEnvironment) {
		HBox.setHgrow(this, Priority.ALWAYS);
		VBox.setVgrow(this, Priority.ALWAYS);
		
		double buttonHeight = 35;
		
		Button clearLightsButton = new Button("Clear Lights");
		clearLightsButton.setPrefHeight(buttonHeight);
		clearLightsButton.setOnAction(e -> {
			opticsEnvironment.clearLights();
		});
		Button clearMaterialsButton = new Button("Clear Objects");
		clearMaterialsButton.setPrefHeight(buttonHeight);
		clearMaterialsButton.setOnAction(e -> {
			opticsEnvironment.clearMaterials();
		});
		Button clearButton = new Button("Clear All");
		clearButton.setPrefHeight(buttonHeight);
		clearButton.setOnAction(e -> {
			opticsEnvironment.clear();
		});
		Button rotationFactorButton = new Button("Rotation factor: 1");
		rotationFactorButton.setPrefHeight(buttonHeight);
		rotationFactorButton.setPrefWidth(170);
		rotationFactorButton.setOnAction(e -> {
			String[] parts = rotationFactorButton.getText().split(" ");
			double val = Double.parseDouble(parts[parts.length - 1]);
			val /= 2;
			if(val < 0.10) val = 1;
			opticsEnvironment.setRotationFactor(val);
			rotationFactorButton.setText("Rotation factor: " + val );
		});
		
		this.getItems().addAll(clearButton, clearLightsButton, clearMaterialsButton, new Separator(), rotationFactorButton);
	}
}
