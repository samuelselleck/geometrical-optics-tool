package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import gui.OpticsEnvironment;
import model.OpticsModel;

public class OpticsIO {
	private OpticsEnvironment opticsEnvironment;
	
	public OpticsIO(OpticsEnvironment opticsEnvironment) {
		this.opticsEnvironment = opticsEnvironment;
	}
	
	public void saveWorkspace(File file) {
		
		try {
			
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new  ObjectOutputStream(fileOut);
			out.writeObject(opticsEnvironment.getOpticsModel());
			out.close();
			fileOut.close();
		 
		} catch (IOException e) {
			System.out.println("Couldn't save to file: " + file.getAbsolutePath());
			e.printStackTrace();
		}
	}
	
	public void loadWorkspace(File file) {
		try {
			FileInputStream fileIn =  new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			OpticsModel model = (OpticsModel)in.readObject();
			opticsEnvironment.setOpticsModel(model);
			int len = opticsEnvironment.modelLensMaterials.size();
			opticsEnvironment.modelLensMaterials.addAll(model.getMetadata().getLensMaterials());
			opticsEnvironment.modelLensMaterials.remove(0, len);
			in.close();
			fileIn.close();
			
			opticsEnvironment.getView().redraw();
		} catch (Exception e) {
			System.err.println("Couldn't load file: " + file.getAbsolutePath());
			e.printStackTrace();
		}
	}
}
