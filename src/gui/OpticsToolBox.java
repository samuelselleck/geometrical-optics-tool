package gui;

import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.GlobalOpticsSettings;

public class OpticsToolBox extends ToolBar {
	
	private OpticsController opticsController;
	
	public OpticsToolBox(OpticsController opticsController) {
		HBox.setHgrow(this, Priority.ALWAYS);
		VBox.setVgrow(this, Priority.ALWAYS);
		
		double buttonHeight = 35;
		
		Button clearLightsButton = new Button("Clear Lights");
		clearLightsButton.setPrefHeight(buttonHeight);
		clearLightsButton.setOnAction(e -> {
			opticsController.clearLights();
		});
		Button clearMaterialsButton = new Button("Clear Objects");
		clearMaterialsButton.setPrefHeight(buttonHeight);
		clearMaterialsButton.setOnAction(e -> {
			opticsController.clearMaterials();
		});
		Button clearButton = new Button("Clear All");
		clearButton.setPrefHeight(buttonHeight);
		clearButton.setOnAction(e -> {
			opticsController.clear();
		});
		Button rotationFactorButton = new Button("Rotation factor: 1");
		rotationFactorButton.setPrefHeight(buttonHeight);
		rotationFactorButton.setPrefWidth(170);
		rotationFactorButton.setOnAction(e -> {
			String[] parts = rotationFactorButton.getText().split(" ");
			double val = Double.parseDouble(parts[parts.length - 1]);
			val /= 2;
			if(val < 0.10) val = 1;
			opticsController.setRotationFactor(val);
			rotationFactorButton.setText("Rotation factor: " + val );
		});
		
		this.getItems().addAll(clearButton, clearLightsButton, clearMaterialsButton, new Separator(), rotationFactorButton);
		this.opticsController = opticsController;
	}
}
