package gui.optics_tabs;

import gui.OpticsView;
import gui.optics_object_creators.OpticsObjectCreator;
import gui.optics_object_creators.RectangleWallCreator;

public class WallTab extends OpticsTab {

	public WallTab(OpticsView view) {
		this.setText("Walls");
		
		OpticsObjectCreator rect = new RectangleWallCreator(view);
		super.addTab("Rectangle", rect);
	}
}
