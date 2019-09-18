package gui.optics_tabs;

import gui.optics_object_creators.OpticsObjectCreator;
import gui.optics_object_creators.RectangleWallCreator;

public class WallTab extends OpticsTab {

	public WallTab() {
		this.setText("Walls");
		
		OpticsObjectCreator rect = new RectangleWallCreator();
		super.addTab("Rectangle", rect);
	}
}
