package gui;


import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import optics_logic.OpticsHandler;
import settings.BigSettingsBox;

public class Main extends Application {
	public static boolean DEBUG = false;
	public static double WIDTH;
	public static double HEIGHT;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane root = new BorderPane();
		
		//Set window size and resolution to full screen size.
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX(primaryScreenBounds.getMinX());
		stage.setY(primaryScreenBounds.getMinY());
		stage.setWidth(primaryScreenBounds.getWidth());
		stage.setHeight(primaryScreenBounds.getHeight());
		WIDTH = stage.getWidth();
		HEIGHT = stage.getHeight();

		root.setStyle("-fx-base: black; -fx-fill: white;" +
				" -fx-focus-color: white; -fx-faint-focus-color: white;");
		
		Canvas canvas = new Canvas(WIDTH * 4 / 5, HEIGHT * 60 / 61);
		StackPane canvasHolder = new StackPane();
		canvasHolder.setStyle("-fx-background-color: black");
		canvasHolder.getChildren().add(canvas);
		root.setLeft(canvasHolder);
		
		OpticsHandler opticsHandler = new OpticsHandler(canvas);

		HBox settingsBox = new BigSettingsBox(opticsHandler);
		root.setRight(settingsBox);
		
		HBox toolBox = new OpticsToolBox(opticsHandler);
		root.setBottom(toolBox);
		
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		stage.setTitle("Geometrical Optics Tool");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setFullScreen(true);
		stage.show();
	}

}
