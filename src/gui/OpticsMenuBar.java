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

	public OpticsMenuBar(OpticsController opticsController, Stage primaryStage) {
		Menu file = new Menu("File");
		
		OpticsIO opticsIO = new OpticsIO(opticsController);
		MenuItem open = new MenuItem("Open Workspace");
		open.setOnAction(e -> {
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Open Workspace");
			File exampleFile = chooser.showOpenDialog(primaryStage);
			if(exampleFile != null) {
				opticsIO.loadExample(exampleFile);
			}
		});
		
		MenuItem save = new MenuItem("Save Workspace");
		save.setOnAction(e -> {
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Save Workspace");
			File exampleFile = chooser.showSaveDialog(primaryStage);
			if(exampleFile != null) {
				opticsIO.saveCurrentWorkspace(exampleFile);
			}
		});
		
		MenuItem saveImage = new MenuItem("Save Screenshot");
		saveImage.setOnAction(e -> {
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Save Screenshot");
			chooser.getExtensionFilters().add(
					new ExtensionFilter("PNG file (*.png)", "*.png"));
			File saveFile = chooser.showSaveDialog(primaryStage);
			if(saveFile != null) {
				opticsController.saveScreenshotTo(saveFile);
			}
			
		});
		
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(e -> {
			System.exit(0);
		});
		
		file.getItems().addAll(open, save, saveImage, new SeparatorMenuItem(), exit);
		this.getMenus().add(file);
	}
}
