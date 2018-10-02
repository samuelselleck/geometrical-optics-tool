package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import optics_logic.LightRay;
import optics_logic.OpticsHandler;
import optics_objects.LightSource;

public class OpticsToolBox extends HBox {
	public OpticsToolBox(OpticsHandler opticsHandler) {
		double buttonHeight = Main.HEIGHT/20;
		HBox.setHgrow(this, Priority.ALWAYS);
		this.setPrefHeight(Main.HEIGHT / 10);
		HBox tools = new HBox(Main.HEIGHT/80);
		HBox exit = new HBox();
		HBox.setHgrow(tools, Priority.ALWAYS);
		HBox.setHgrow(exit, Priority.ALWAYS);
		
		ToggleButton toggleRaysButton = new ToggleButton("Rays: On");
		toggleRaysButton.setPrefHeight(buttonHeight);
		toggleRaysButton.setOnAction(e -> {
			if (LightRay.DRAW_ONLY_HITTING == false) {
				toggleRaysButton.setText("Rays: Off");
				LightRay.DRAW_ONLY_HITTING = true;
			} else {
				toggleRaysButton.setText("Rays: On");
				LightRay.DRAW_ONLY_HITTING = false;
			}
			opticsHandler.redraw();
		});
		toggleRaysButton.setPrefWidth(Main.WIDTH/10);
		Button clearLightsButton = new Button("Clear Lights");
		clearLightsButton.setPrefHeight(buttonHeight);
		clearLightsButton.setOnAction(e -> {
			opticsHandler.clearLights();
		});
		Button clearMaterialsButton = new Button("Clear Objects");
		clearMaterialsButton.setPrefHeight(buttonHeight);
		clearMaterialsButton.setOnAction(e -> {
			opticsHandler.clearMaterials();
		});
		Button clearButton = new Button("Clear All");
		clearButton.setPrefHeight(buttonHeight);
		clearButton.setOnAction(e -> {
			opticsHandler.clear();
		});
		Button rotationFactorButton = new Button("Rotation factor: 1");
		rotationFactorButton.setPrefHeight(buttonHeight);
		rotationFactorButton.setOnAction(e -> {
			String[] parts = rotationFactorButton.getText().split(" ");
			double val = Double.parseDouble(parts[parts.length - 1]);
			val /= 2;
			if(val < 0.10) val = 1;
			opticsHandler.setRotationFactor(val);
			rotationFactorButton.setText("Rotation factor: " + val );
		});
		rotationFactorButton.setPrefWidth(Main.WIDTH/8);
		Button rayModeButton = new Button("Mode: Ray");
		rayModeButton.setPrefHeight(buttonHeight);
		rayModeButton.setOnAction(e -> {
			if(rayModeButton.getText().equals("Mode: Ray")) {
				LightSource.WHITE = true;
				
				rayModeButton.setText("Mode: Color");
			} else {
				LightSource.WHITE = false;
				rayModeButton.setText("Mode: Ray");
			}
			opticsHandler.createRays();
		});
		rayModeButton.setPrefWidth(Main.WIDTH/8);
		
		ExampleBox examples = new ExampleBox(opticsHandler);
		
		Button saveButton = new Button("Save");
		saveButton.setPrefHeight(buttonHeight);
		saveButton.setOnAction(e -> {
			examples.saveCurrentWorkspaceAs("test");
		});
		
		Button loadButton = new Button("Load");
		loadButton.setPrefHeight(buttonHeight);
		loadButton.setOnAction(e -> {
			examples.loadExample("test");
		});
		
		Button exitButton = new Button("Exit");
		exitButton.setPrefHeight(buttonHeight);
		exitButton.setPrefWidth(Main.WIDTH*0.193);
		exitButton.setOnAction(e -> {
			System.exit(0);
		});
		
		tools.getChildren().addAll(toggleRaysButton, clearButton,
				clearLightsButton, clearMaterialsButton, rotationFactorButton,
				rayModeButton, examples, saveButton, loadButton);
		exit.getChildren().addAll(exitButton);
		exit.setAlignment(Pos.BASELINE_RIGHT);
		this.getChildren().addAll(tools, exit);
		VBox.setVgrow(this, Priority.ALWAYS);
		for (Node n : this.getChildren()) {
			VBox.setVgrow(n, Priority.ALWAYS);
			HBox.setMargin(n, new Insets(Main.HEIGHT/100));
		}
	}
}
