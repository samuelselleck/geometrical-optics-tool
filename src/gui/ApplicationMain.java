package gui;

import gui.optics_tabs.OpticsCreatorsBox;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.OpticsModel;

import javafx.application.Application;

public class ApplicationMain extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		Main.DPCM = Screen.getPrimary().getDpi()/2.54; //pixels per centimeter
		int width = Main.getIntProperty("width");
		int height = Main.getIntProperty("height");

		OpticsModel model = new OpticsModel();

		OpticsCanvas canvasView = new OpticsCanvas();
		OpticsCreatorsBox opticsBox = new OpticsCreatorsBox();

		OpticsEnvironment opticsEnvironment = new OpticsEnvironment(model, canvasView, opticsBox);

		OpticsToolBox toolBox = new OpticsToolBox(opticsEnvironment);
		canvasView.bindXYLabel(toolBox.getXYLabel());
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
		scene.setOnKeyReleased(opticsEnvironment::onKeyReleased);
		stage.setTitle("Geometrical Optics Tool");
		stage.setScene(scene);
		stage.show();
		com.install4j.api.launcher.SplashScreen.hide();
	}
}
