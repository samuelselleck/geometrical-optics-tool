package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import gui.optics_tabs.OpticsCreatorsBox;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.OpticsModel;

public class Main extends Application {
	public static String PATH;
	public static double DPCM;
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

		DPCM = Screen.getPrimary().getDpi()/2.54; //pixels per centimeter
		int width = Integer.parseInt(PROPERTIES.getProperty("width"));
		int height = Integer.parseInt(PROPERTIES.getProperty("height"));

		OpticsModel model = new OpticsModel();

		OpticsCanvas canvasView = new OpticsCanvas();
		OpticsCreatorsBox opticsBox = new OpticsCreatorsBox();

		OpticsEnvironment opticsEnvironment = new OpticsEnvironment(model, canvasView, opticsBox);

		OpticsToolBox toolBox = new OpticsToolBox(opticsEnvironment);
		
		OpticsMenuBar menuBar = new OpticsMenuBar(opticsEnvironment, stage);
		
		SplitPane splitPane = new SplitPane();
		
		splitPane.getItems().add(canvasView.getCanvas());
		splitPane.getItems().add(opticsBox);
		SplitPane.setResizableWithParent(opticsBox, false);
		splitPane.setDividerPositions(0.7);
		
		BorderPane root = new BorderPane();
		
		root.setStyle(
				"-fx-base: #222222;" +
				"-fx-faint-focus-color: white;"
				);
		
		root.setTop(menuBar);
		root.setCenter(splitPane);
		root.setBottom(toolBox);

		Scene scene = new Scene(root, width, height);
		stage.setTitle("Geometrical Optics Tool");
		stage.setScene(scene);
		stage.show();
	}

}
