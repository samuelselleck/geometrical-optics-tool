package gui;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import gui.subviews.AddMaterialWindow;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.OpticsModel;
import model.optics_objects.OpticsObject;
import util.OpticsIO;

public class OpticsMenuBar extends MenuBar {

	private File workspaceFile, imgsaveFile;
	
	public OpticsMenuBar(OpticsEnvironment opticsEnvironment, Stage primaryStage) {
		
		Menu file = new Menu("File");
		
		OpticsIO opticsIO = new OpticsIO(opticsEnvironment);
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
				opticsEnvironment.saveScreenshotTo(saveFile);
			}
			
		});
		
		
		Menu window = new Menu("Window");
		
		MenuItem resetView = new MenuItem("Reset View");
		resetView.setOnAction(e -> {
			opticsEnvironment.getView().resetView();
			opticsEnvironment.redraw();
		});
		
		CheckMenuItem grid = new CheckMenuItem("Grid");
		grid.setOnAction(e -> {
		    opticsEnvironment.getView().setGrid(grid.isSelected());
			opticsEnvironment.redraw();
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
		
		MenuItem lockObjects = new MenuItem("Lock all Objects");
		lockObjects.setOnAction(e -> {
			setObjectsLocked(opticsEnvironment, true);
		});
		
		MenuItem unlockObjects = new MenuItem("Unlock all Objects");
		unlockObjects.setOnAction(e -> {
			setObjectsLocked(opticsEnvironment, false);
		});
		
		file.getItems().addAll(open, save, saveImage, new SeparatorMenuItem(), exit);
		window.getItems().addAll(resetView, grid);
		options.getItems().addAll(addLensMaterial);
		if(Main.isActive("admin")) {
			options.getItems().addAll(lockObjects, unlockObjects);
		}
		this.getMenus().addAll(file, window, options);
	}
	
	private void setObjectsLocked(OpticsEnvironment opticsEnvironment, boolean val) {
		
		OpticsModel model = opticsEnvironment.getOpticsModel();
		List<OpticsObject> objects = Stream.concat(
				model.getMaterials().stream(),
				model.getLights().stream())
                .collect(Collectors.toList());
		
		if(val) {
			opticsEnvironment.getOpticsCreatorBox().unbindAll();
			opticsEnvironment.deselect();
		}
		
		objects.forEach(o -> {
				o.getProperties().get("FixedPosition").set(val ? 1 : 0);
				o.getProperties().get("FixedRotation").set(val ? 1 : 0);
				o.getProperties().get("FixedProperties").set(val ? 1 : 0);
				o.getProperties().get("NoFocus").set(val ? 1 : 0);
			});
	}
}
