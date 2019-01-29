package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import optics_logic.OpticsModel;
import optics_logic.OpticsSettings;

public class ExampleBox extends ComboBox<String> {
	private static final String SAVE_PATH = "/Geometrical Optics Tool";
	OpticsController opticsController;
	Button toggleRaysButton, rayModeButton;
	Path root;
	
	public ExampleBox(OpticsController opticsController, Button toggleRaysButton, Button rayModeButton) {
		//Test git
		//TODO make general
		this.toggleRaysButton = toggleRaysButton;
		this.rayModeButton = rayModeButton;
		
		root = Paths.get(System.getProperty("user.home") + SAVE_PATH);
		if(root.toFile().exists()) {
			
			File[] files = root.toFile().listFiles();
			List<String> serializedFiles = new ArrayList<>();
			for(File f : files) {
				String[] name = f.getName().split("\\.");
				if(name[1].equals("ser")) {
					serializedFiles.add(name[0]);
				}
			}
			
			this.getItems().addAll(serializedFiles);
			this.setPromptText("Choose example");
		} else {
			this.setPromptText("Exampes not installed");
		}
		
		if(Main.ADMIN) this.setEditable(true);
		
		this.opticsController = opticsController;
	}
	
	public void saveCurrentWorkspace() {
		root.toFile().mkdirs();
		String s = this.getSelectionModel().getSelectedItem();	
		if(!this.getItems().contains(s)) this.getItems().add(s);
		
		try {
			
			FileOutputStream fileOut = new FileOutputStream(Paths.get(root.toString(), "/" + s + ".ser").toFile());
			ObjectOutputStream out = new  ObjectOutputStream(fileOut);
			out.writeObject(opticsController.getOpticsModel());
			out.close();
			fileOut.close();
		 
		} catch (IOException e) {
			System.out.println("Couldn't save to file: " + s);
			e.printStackTrace();
		}
	}
	
	public void loadExample() {
		String s = this.getSelectionModel().getSelectedItem();
		FileInputStream fileIn;
		try {
			fileIn = new FileInputStream(Paths.get(root.toString(), "/" + s + ".ser").toFile());
			ObjectInputStream in = new ObjectInputStream(fileIn);
			opticsController.setOpticsModel((OpticsModel)in.readObject());
			in.close();
			fileIn.close();
			
			// make general!!
			OpticsSettings settings = opticsController.getModelSettings();
			rayModeButton.setText("Mode: " + (settings.colorMode() ? "Color" : "Ray"));
			toggleRaysButton.setText("Rays: " + (settings.drawOnlyHitting() ? "Off" : "On"));
			
		} catch (Exception e) {
			System.out.println("Couldn't load file: " + s);
			e.printStackTrace();
		}
	}

	public void deleteCurrent() {
		String s = this.getSelectionModel().getSelectedItem();
		this.getItems().remove(s);
		this.getEditor().setText("Removed " + s);
		Paths.get(root.toString(),  "/" + s + ".ser").toFile().delete();
	}
}
