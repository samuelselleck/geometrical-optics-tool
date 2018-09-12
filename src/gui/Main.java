package gui;


import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX(primaryScreenBounds.getMinX());
		stage.setY(primaryScreenBounds.getMinY());
		stage.setWidth(primaryScreenBounds.getWidth());
		stage.setHeight(primaryScreenBounds.getHeight());
		WIDTH = stage.getWidth();
		HEIGHT = stage.getHeight();

		Canvas canvas = new Canvas(WIDTH * 4 / 5, HEIGHT * 60 / 61);
		canvas.getGraphicsContext2D().setGlobalBlendMode(BlendMode.ADD);
		OpticsHandler opticsHandler = new OpticsHandler(canvas);

		HBox settingsBox = new BigSettingsBox(opticsHandler);
		root.setRight(new Group(settingsBox));
		HBox toolBox = new OpticsToolBox(opticsHandler);
		root.setBottom(toolBox);

		root.setLeft(canvas);
		
		root.setStyle("-fx-base: black; -fx-fill: white;" +
		" -fx-focus-color: white; -fx-faint-focus-color: white;");
		
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		stage.setTitle("Geometrical Optics Tool");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setFullScreen(true);
		stage.show();

		stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
			if (KeyCode.ESCAPE == event.getCode()) {
				stage.close();
			}
		});
	}

}
