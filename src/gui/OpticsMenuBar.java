package gui;

import java.io.File;

import gui.subviews.AddMaterialWindow;
import javafx.scene.control.CheckMenuItem;
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
	
	public OpticsMenuBar(OpticsController opticsController, Stage primaryStage) {
		
		Menu file = new Menu("File");
		
		OpticsIO opticsIO = new OpticsIO(opticsController);
		FileChooser chooser = new FileChooser();
		workspaceFile = imgsaveFile = new File(Main.PATH);
		
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
				opticsIO.loadWorkspace(exampleFile);
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
				opticsIO.saveWorkspace(saveFile);
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
		
		CheckMenuItem grid = new CheckMenuItem("Grid");
		grid.setOnAction(e -> {
		    opticsController.getView().setGrid(grid.isSelected());
			opticsController.redraw();
		});
		
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(e -> {
			System.exit(0);
		});
		
		Menu options = new Menu("Options");
		
		MenuItem addLensMaterial = new MenuItem("Add Lens Material...");
		
		addLensMaterial.setOnAction(e -> {
			@SuppressWarnings("unused")
			AddMaterialWindow addMWin = new AddMaterialWindow();
		});
		file.getItems().addAll(open, save, saveImage, new SeparatorMenuItem(), exit);
		window.getItems().addAll(resetView, grid);
		options.getItems().addAll(addLensMaterial);
		this.getMenus().addAll(file, window, options);
	}
}
