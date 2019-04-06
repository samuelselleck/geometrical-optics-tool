package gui;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import optics_logic.OpticsModel;
import optics_logic.OpticsSettings;
import settings.BigSettingsBox;

public class Main extends Application {
	public static boolean DEBUG = false;
	public static double WIDTH;
	public static double HEIGHT;
	public static Properties properties;

	public static void main(String[] args) {
		properties = new Properties();
		try {
			String jarPath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation()
				    .toURI()).getParent();
			properties.load(new FileInputStream(jarPath + "/config.txt"));
			properties.setProperty("jarpath", jarPath);
		} catch (IOException e) {
			System.err.println("Could not find config.txt file");
		} catch (URISyntaxException e) {
			System.err.println("Could not find the correct file path, unsupported operating system?");
		}
		
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		//Set window size;
		if(!properties.getProperty("fullscreen").equals("true")) {
			WIDTH = Integer.parseInt(properties.getProperty("width"));
			HEIGHT = Integer.parseInt(properties.getProperty("height"));
			
		} else {
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			WIDTH = primaryScreenBounds.getWidth();
			HEIGHT = primaryScreenBounds.getHeight();
		}
		
		stage.setWidth(WIDTH);
		stage.setHeight(HEIGHT);
		
		OpticsSettings settings = new OpticsSettings();
		
		OpticsModel model = new OpticsModel(settings);
		OpticsView view = new OpticsView(WIDTH * 3/4, HEIGHT - 100);
		OpticsController opticsController = new OpticsController(model, view);
		
		OpticsToolBox toolBox = new OpticsToolBox(opticsController);
		toolBox.setStyle("-fx-background-color: #333333;");
		
		OpticsMenuBar menuBar = new OpticsMenuBar(opticsController, toolBox, stage);
		menuBar.setStyle("-fx-background-color: #333333;");
		
		HBox settingsBox = new BigSettingsBox(opticsController);
		
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: #222222; -fx-base: black; -fx-fill: black;" +
				" -fx-focus-color: gray; -fx-faint-focus-color: white;");
		
		root.setTop(menuBar);
		root.setCenter(view.getCanvas());
		root.setRight(settingsBox);
		root.setBottom(toolBox);
		
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		stage.setTitle("Geometrical Optics Tool");
		stage.setScene(scene);
		stage.setResizable(false);
		if(properties.getProperty("fullscreen").equals("true")) {
			stage.setFullScreen(true);
		}
		stage.show();
	}

}
