package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;

import gui.OpticsController;
import optics_logic.OpticsModel;

public class OpticsIO {
	OpticsController opticsController;
	Path root;
	
	public OpticsIO(OpticsController opticsController) {
		this.opticsController = opticsController;
	}
	
	public void saveCurrentWorkspace(File file) {
		
		try {
			
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new  ObjectOutputStream(fileOut);
			out.writeObject(opticsController.getOpticsModel());
			out.close();
			fileOut.close();
		 
		} catch (IOException e) {
			System.out.println("Couldn't save to file: " + file.getAbsolutePath());
			e.printStackTrace();
		}
	}
	
	public void loadExample(File file) {
		try {
			FileInputStream fileIn =  new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			opticsController.setOpticsModel((OpticsModel)in.readObject());
			in.close();
			fileIn.close();
			//TODO UPDATE BUTTONS
		} catch (Exception e) {
			System.err.println("Couldn't load file: " + file.getAbsolutePath());
			e.printStackTrace();
		}
	}
}