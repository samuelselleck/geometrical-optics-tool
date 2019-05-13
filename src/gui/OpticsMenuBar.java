package gui;

import java.io.File;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import util.OpticsIO;
import javafx.stage.Stage;

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
		
		MenuItem resetView = new MenuItem("Reset View");
		resetView.setOnAction(e -> {
			opticsController.getView().resetView();
			opticsController.redraw();
		});
		
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(e -> {
			System.exit(0);
		});
		
		file.getItems().addAll(open, save, saveImage, resetView, new SeparatorMenuItem(), exit);
		this.getMenus().add(file);
	}
}
