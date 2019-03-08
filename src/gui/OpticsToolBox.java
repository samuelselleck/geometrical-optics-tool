package gui;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import optics_logic.OpticsSettings;

public class OpticsToolBox extends HBox {
	
	public OpticsToolBox(OpticsController opticsController, Stage primaryStage) {
		double buttonHeight = Main.HEIGHT/20;
		HBox.setHgrow(this, Priority.ALWAYS);
		this.setPrefHeight(Main.HEIGHT / 10);
		HBox tools = new HBox(Main.HEIGHT/80);
		HBox exit = new HBox();
		HBox.setHgrow(tools, Priority.ALWAYS);
		HBox.setHgrow(exit, Priority.ALWAYS);
		
		Button toggleRaysButton = new Button("Rays: On");
		toggleRaysButton.setPrefHeight(buttonHeight);
		toggleRaysButton.setPrefWidth(Main.WIDTH/12);
		toggleRaysButton.setOnAction(e -> {
			OpticsSettings settings = opticsController.getModelSettings();
			toggleRaysButton.setText("Rays: " + (settings.toggleDrawOnlyHitting() ? "Off" : "On"));
			//TODO make button labels update when loading new model
			opticsController.redraw();
		});
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
		rotationFactorButton.setPrefWidth(Main.WIDTH/10);
		rotationFactorButton.setOnAction(e -> {
			String[] parts = rotationFactorButton.getText().split(" ");
			double val = Double.parseDouble(parts[parts.length - 1]);
			val /= 2;
			if(val < 0.10) val = 1;
			opticsController.setRotationFactor(val);
			rotationFactorButton.setText("Rotation factor: " + val );
		});
		Button rayModeButton = new Button("Mode: Ray");
		rayModeButton.setPrefHeight(buttonHeight);
		rayModeButton.setPrefWidth(Main.WIDTH/12);
		rayModeButton.setOnAction(e -> {
			OpticsSettings settings = opticsController.getModelSettings();
			rayModeButton.setText("Mode: " + (settings.toggleColorMode() ? "Color" : "Ray"));
			opticsController.redraw();
		});
		
		ExampleBox examples = new ExampleBox(opticsController, toggleRaysButton, rayModeButton);
		examples.setPrefHeight(buttonHeight);
		
		Button saveButton = new Button("Save");
		saveButton.setPrefHeight(buttonHeight);
		saveButton.setOnAction(e -> {
			examples.saveCurrentWorkspace();
		});
		
		Button loadButton = new Button("Load");
		loadButton.setPrefHeight(buttonHeight);
		loadButton.setOnAction(e -> {
			examples.loadExample();
		});
		
		Button deleteButton = new Button("Delete");
		deleteButton.setPrefHeight(buttonHeight);
		deleteButton.setOnAction(e -> {
			examples.deleteCurrent();
		});
		
		Button saveImageButton = new Button("Save image");
		saveImageButton.setPrefHeight(buttonHeight);
		saveImageButton.setOnAction(e -> {
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Choose save directory");
			chooser.getExtensionFilters().add(
					new ExtensionFilter("PNG file (*.png)", "*.png"));
			File saveFile = chooser.showSaveDialog(primaryStage);
			if(saveFile != null) {
				opticsController.saveScreenshotTo(saveFile);
			}
			
		});
		
		Button exitButton = new Button("Exit");
		exitButton.setPrefHeight(buttonHeight);
		exitButton.setPrefWidth(Main.WIDTH*0.193);
		exitButton.setOnAction(e -> {
			System.exit(0);
		});
		
		tools.getChildren().addAll(toggleRaysButton, clearButton,
				clearLightsButton, clearMaterialsButton, rotationFactorButton,
				rayModeButton, examples);
		
		if(Main.ADMIN)
		tools.getChildren().addAll(saveButton, deleteButton);
		
		tools.getChildren().addAll(loadButton, saveImageButton);
		
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
