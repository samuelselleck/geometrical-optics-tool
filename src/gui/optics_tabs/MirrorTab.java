package gui.optics_tabs;

import gui.optics_object_creators.FlatMirrorCreator;
import gui.optics_object_creators.OpticsObjectCreator;
import gui.optics_object_creators.RoundedMirrorCreator;

public class MirrorTab extends OpticsTab {

	public MirrorTab() {
		this.setText("Mirrors");
		
		OpticsObjectCreator flat = new FlatMirrorCreator();
		super.addTab("Flat", flat);
		OpticsObjectCreator rounded = new RoundedMirrorCreator();
		super.addTab("Rounded", rounded);
	}
}