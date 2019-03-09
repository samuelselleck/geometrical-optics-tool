package gui;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import optics_logic.OpticsModel;
import optics_logic.OpticsSettings;
import settings.BigSettingsBox;

public class Main extends Application {
	public static boolean DEBUG = false;
	public static boolean ADMIN;
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
		
		if((args.length > 0 && args[0].equals("admin")) ||
				properties.getProperty("admin").equals("true")) {
			ADMIN = true;
		} else {
			ADMIN = false;
		}
		
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane root = new BorderPane();
		
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

		root.setStyle("-fx-background-color: #222222; -fx-base: black; -fx-fill: black;" +
				" -fx-focus-color: gray; -fx-faint-focus-color: white;");
		OpticsSettings settings = new OpticsSettings();
		
		OpticsModel model = new OpticsModel(settings);
		OpticsView view = new OpticsView(WIDTH * 3/4, HEIGHT - 100);
		OpticsController opticsController = new OpticsController(model, view);
		root.setCenter(view.getCanvas());
		
		OpticsMenuBar menuBar = new OpticsMenuBar(opticsController, stage);
		menuBar.setStyle("-fx-background-color: #333333;");
		root.setTop(menuBar);
		
		HBox settingsBox = new BigSettingsBox(opticsController);
		root.setRight(settingsBox);
		
		ToolBar toolBox = new OpticsToolBox(opticsController, stage);
		toolBox.setStyle("-fx-background-color: #333333;");
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
