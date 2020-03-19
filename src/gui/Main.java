package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import javafx.application.Application;

public class Main {
	public static String PATH;
	public static double DPCM;
	private static Properties PROPERTIES;
	

	public static void main(String[] args) {

		PROPERTIES = new Properties();
		
		try {

			PATH = new File(Main.class
					.getProtectionDomain()
					.getCodeSource()
					.getLocation()
				    .toURI())
					.getParent();

			PROPERTIES.load(new FileInputStream(PATH + "/config.txt"));

		} catch (IOException e) {
			System.err.println("Could not find config.txt file");
		} catch (URISyntaxException e) {
			System.err.println("Could not find the correct file path, unsupported operating system?");
		}

		Application.launch(ApplicationMain.class, args);
	}

	public static int getIntProperty(String name) {
		return Integer.parseInt(PROPERTIES.getProperty(name));
	}

	public static boolean isActive(String name) {
		return PROPERTIES.getProperty(name).equalsIgnoreCase("true");
	}
}
