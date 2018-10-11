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

import javafx.scene.control.ComboBox;
import optics_logic.OpticsController;
import optics_logic.OpticsModel;

public class ExampleBox extends ComboBox<String> {
	private static final String SAVE_PATH = "/Geometrical Optics Tool";
	OpticsController opticsHandler;
	Path root;
	
	public ExampleBox(OpticsController opticsHandler) {
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
		
		this.opticsHandler = opticsHandler;
	}
	
	public void saveCurrentWorkspace() {
		root.toFile().mkdirs();
		String s = this.getSelectionModel().getSelectedItem();	
		if(!this.getItems().contains(s)) this.getItems().add(s);
		
		try {
			
			FileOutputStream fileOut = new FileOutputStream(Paths.get(root.toString(), "/" + s + ".ser").toFile());
			ObjectOutputStream out = new  ObjectOutputStream(fileOut);
			out.writeObject(opticsHandler.getOpticsModel());
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
			opticsHandler.setOpticsModel((OpticsModel)in.readObject());
			in.close();
			fileIn.close();
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
