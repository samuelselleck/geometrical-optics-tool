package gui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import optics_logic.OpticsHandler;
import optics_objects.OpticsObject;

public class ExampleBox extends ChoiceBox<String> {
	OpticsHandler opticsHandler;
	
	public ExampleBox(OpticsHandler opticsHandler) {
		super(FXCollections.observableArrayList(
			    "Test num 1", "Test2", "Stuffff")
				);
		this.opticsHandler = opticsHandler;
	}
	
	public void saveCurrentWorkspaceAs(String s) {
		try {
			
			FileOutputStream fileOut = new FileOutputStream("/tmp/" + s + ".ser");
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
	public void loadExample(String s) {
		FileInputStream fileIn;
		try {
			fileIn = new FileInputStream("/tmp/" + s + ".ser");
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
