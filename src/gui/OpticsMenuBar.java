package gui;

import java.io.File;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class OpticsMenuBar extends MenuBar {

	public OpticsMenuBar(OpticsController opticsController, Stage primaryStage) {
		Menu file = new Menu("File");
		
		MenuItem open = new MenuItem("Open Example...");
		MenuItem save = new MenuItem("Save Workspace...");
		
		MenuItem saveImage = new MenuItem("Save screenshot...");
		saveImage.setOnAction(e -> {
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Choose save directory");
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
