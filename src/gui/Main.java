package gui;


import java.io.FileInputStream;
import java.io.IOException;
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
	public static boolean ADMIN;
	public static double WIDTH;
	public static double HEIGHT;
	public static Properties properties;

	public static void main(String[] args) {
		properties = new Properties();
		try {
			properties.load(new FileInputStream("config.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		root.setStyle("-fx-base: black; -fx-fill: white;" +
				" -fx-focus-color: white; -fx-faint-focus-color: white;");
		OpticsSettings settings = new OpticsSettings();
		
		OpticsModel model = new OpticsModel(settings);
		OpticsView view = new OpticsView(WIDTH * 4 / 5, HEIGHT * 9/10);
		OpticsController opticsController = new OpticsController(model, view);
		root.setLeft(view.getCanvas());
		

		HBox settingsBox = new BigSettingsBox(opticsController);
		root.setRight(settingsBox);
		
		HBox toolBox = new OpticsToolBox(opticsController, stage);
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
