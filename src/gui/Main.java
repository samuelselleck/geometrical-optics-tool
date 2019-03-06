package gui;


import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import optics_logic.OpticsModel;
import optics_logic.OpticsSettings;
import settings.BigSettingsBox;

public class Main extends Application {
	public static boolean DEBUG = false;
	public static boolean ADMIN;
	public static double WIDTH;
	public static double HEIGHT;

	public static void main(String[] args) {
		if(args.length > 0 && args[0].equals("admin")) ADMIN = true;
		else ADMIN = false;
		//ADMIN = true;
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
		OpticsSettings settings = new OpticsSettings();
		
		OpticsModel model = new OpticsModel(settings);
		OpticsView view = new OpticsView(WIDTH * 4 / 5, HEIGHT * 60 / 61);
		OpticsController opticsController = new OpticsController(model, view);
		root.setLeft(view.getCanvas());
		

		HBox settingsBox = new BigSettingsBox(opticsController);
		root.setRight(settingsBox);
		
		HBox toolBox = new OpticsToolBox(opticsController);
		root.setBottom(toolBox);
		
		if(!Platform.isSupported(ConditionalFeature.INPUT_MULTITOUCH)) {
			Alert noMultiTouch = new Alert(AlertType.INFORMATION, 
					"This program needs a screen with multitouch\n "
					+ "and gesture support for all futures to work\n"
					+ "as intended. You do not seem to be on one.",
					ButtonType.OK);
			noMultiTouch.showAndWait();
		}
		
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		stage.setTitle("Geometrical Optics Tool");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setFullScreen(true);
		stage.show();
	}

}
