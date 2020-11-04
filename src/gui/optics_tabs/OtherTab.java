package gui.optics_tabs;

import gui.optics_object_creators.DetectorWallCreator;
import gui.optics_object_creators.FlatGitterCreator;
import gui.optics_object_creators.OpticsObjectCreator;

public class OtherTab extends OpticsTab {

	public OtherTab() {
		this.setText("Other");
		
		OpticsObjectCreator git = new FlatGitterCreator();
		super.addTab("Grating", git);
		OpticsObjectCreator detector = new DetectorWallCreator();
		super.addTab("Detector", detector);
	}
}
