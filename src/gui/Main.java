package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import gui.optics_tabs.OpticsCreatorsBox;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.OpticsModel;

public class Main extends Application {
	public static boolean DEBUG = false;
	public static double WIDTH;
	public static double HEIGHT;
	public static String PATH;
	private static Properties PROPERTIES;

	public static void main(String[] args) {
		
		PROPERTIES = new Properties();
		try {
			
			PATH = new File(Main.class
					.getProtectionDomain()
					.getCodeSource()
					.getLocation()
				    .toURI())
					.getParent();
			
			PROPERTIES.load(new FileInputStream(PATH + "/config.txt"));
			
		} catch (IOException e) {
			System.err.println("Could not find config.txt file");
		} catch (URISyntaxException e) {
			System.err.println("Could not find the correct file path, unsupported operating system?");
		}
		
		Application.launch(args);
	}
	
	public static int getIntProperty(String name) {
		return Integer.parseInt(PROPERTIES.getProperty(name));
	}
	
	public static boolean isActive(String name) {
		return PROPERTIES.getProperty(name).equalsIgnoreCase("true");
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		//Set window size;
		if(!PROPERTIES.getProperty("fullscreen").equals("true")) {
			WIDTH = Integer.parseInt(PROPERTIES.getProperty("width"));
			HEIGHT = Integer.parseInt(PROPERTIES.getProperty("height"));
			
		} else {
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			WIDTH = primaryScreenBounds.getWidth();
			HEIGHT = primaryScreenBounds.getHeight();
			stage.setFullScreen(true);
		}
		
		stage.setWidth(WIDTH);
		stage.setHeight(HEIGHT);
		
		OpticsModel model = new OpticsModel();
		OpticsView view = new OpticsView(WIDTH * 3/4, HEIGHT - 100);
		OpticsCreatorsBox opticsBox = new OpticsCreatorsBox();
		
		OpticsController opticsController = new OpticsController(model, view, opticsBox);
		
		OpticsToolBox toolBox = new OpticsToolBox(opticsController);
		toolBox.setStyle("-fx-background-color: #333333;");
		
		OpticsMenuBar menuBar = new OpticsMenuBar(opticsController, stage);
		menuBar.setStyle("-fx-background-color: #333333;");
		
		
		
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: #222222; -fx-base: black; -fx-fill: black;" +
				" -fx-focus-color: gray; -fx-faint-focus-color: white;");
		
		root.setTop(menuBar);
		root.setCenter(view.getCanvas());
		root.setRight(opticsBox);
		root.setBottom(toolBox);
		
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		stage.setTitle("Geometrical Optics Tool");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

}
