package settings;

import optics_object_generators.ObjectCreator;
import optics_object_generators.RectangleWallCreator;

public class WallSettingsTab extends SettingsTab {

	public WallSettingsTab() {
		super();
		this.setText("Walls");
		
		ObjectCreator rect = new RectangleWallCreator();
		super.addTab("Rectangle", rect);
	}
}
