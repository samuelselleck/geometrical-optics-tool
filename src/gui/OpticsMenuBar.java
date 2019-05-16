package gui;

import java.io.File;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import util.OpticsIO;

public class OpticsMenuBar extends MenuBar {

	private File workspaceFile, imgsaveFile;
	
	public OpticsMenuBar(OpticsController opticsController, OpticsToolBox toolBox, Stage primaryStage) {
		Menu file = new Menu("File");
		
		OpticsIO opticsIO = new OpticsIO(opticsController);
		FileChooser chooser = new FileChooser();
		workspaceFile = imgsaveFile = new File(Main.properties.getProperty("jarpath"));
		
		MenuItem open = new MenuItem("Open");
		open.setOnAction(e -> {
			chooser.setTitle("Open Workspace");
			chooser.getExtensionFilters().clear();
			if(workspaceFile != null) {
				chooser.setInitialDirectory(workspaceFile);
			}
			File exampleFile = chooser.showOpenDialog(primaryStage);
			if(exampleFile != null) {
				workspaceFile = exampleFile.getParentFile();
				opticsIO.loadExample(exampleFile);
				toolBox.update();
			}
		});
		
		MenuItem save = new MenuItem("Save");
		save.setOnAction(e -> {
			chooser.setTitle("Save Workspace");
			chooser.getExtensionFilters().clear();
			if(workspaceFile != null) {
				chooser.setInitialDirectory(workspaceFile);
			}
			File saveFile = chooser.showSaveDialog(primaryStage);
			if(saveFile != null) {
				workspaceFile = saveFile.getParentFile();
				opticsIO.saveCurrentWorkspace(saveFile);
			}
		});
		
		MenuItem saveImage = new MenuItem("Screenshot");
		saveImage.setOnAction(e -> {
			chooser.setTitle("Save Screenshot");
			chooser.getExtensionFilters().add(
					new ExtensionFilter("PNG file (*.png)", "*.png"));
			if(imgsaveFile != null) {
				chooser.setInitialDirectory(imgsaveFile);
			}
			File saveFile = chooser.showSaveDialog(primaryStage);
			if(saveFile != null) {
				imgsaveFile = saveFile.getParentFile();
				opticsController.saveScreenshotTo(saveFile);
			}
			
		});
		
		
		Menu window = new Menu("Window");
		
		MenuItem resetView = new MenuItem("Reset View");
		resetView.setOnAction(e -> {
			opticsController.getView().resetView();
			opticsController.redraw();
		});
		
		MenuItem grid = new MenuItem("Grid: ON");
		grid.setOnAction(e -> {
			String str = grid.getText().split(" ")[1];
			if(str.equals("OFF")) {
				opticsController.getView().setGrid(true);
				grid.setText("Grid: ON");
			} else {
				opticsController.getView().setGrid(false);
				grid.setText("Grid: OFF");
			}
			opticsController.redraw();
		});
		
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(e -> {
			System.exit(0);
		});
		
		file.getItems().addAll(open, save, saveImage, new SeparatorMenuItem(), exit);
		window.getItems().addAll(resetView, grid);
		this.getMenus().addAll(file, window);
	}
}
