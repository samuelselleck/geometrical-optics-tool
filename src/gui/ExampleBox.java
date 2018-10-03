package gui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import optics_logic.OpticsHandler;
import optics_objects.materials.OpticsObject;

public class ExampleBox extends ChoiceBox<String> {
	private static final String SAVE_PATH = "/Geometrical Optics Tool";
	OpticsHandler opticsHandler;
	
	public ExampleBox(OpticsHandler opticsHandler) {
		super(FXCollections.observableArrayList(
			    "Plano-convex Lenses", "Prism Angle", "Optical Fiber")
				);
		Paths.get(System.getProperty("user.home") + SAVE_PATH).toFile().mkdir();
		this.opticsHandler = opticsHandler;
	}
	
	public void saveCurrentWorkspace() {
		String s = this.getSelectionModel().getSelectedItem();
		try {
			
			FileOutputStream fileOut = new FileOutputStream(Paths.get(System.getProperty("user.home") + SAVE_PATH + "/" + s + ".ser").toFile());
			ObjectOutputStream out = new  ObjectOutputStream(fileOut);
			out.writeObject(opticsHandler.getOpticsObjectList());
			out.close();
			fileOut.close();
		 
		} catch (IOException e) {
			System.out.println("Couldn't save to file: " + s);
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadExample() {
		String s = this.getSelectionModel().getSelectedItem();
		FileInputStream fileIn;
		try {
			fileIn = new FileInputStream(Paths.get(System.getProperty("user.home") + SAVE_PATH + "/" + s + ".ser").toFile());
			ObjectInputStream in = new ObjectInputStream(fileIn);
			opticsHandler.setOpticsObjects((List<OpticsObject>)in.readObject());
			in.close();
			fileIn.close();
		} catch (Exception e) {
			System.out.println("Couldn't load file: " + s);
			e.printStackTrace();
		}
	}
}
