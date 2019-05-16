package gui.optics_tabs;

import gui.OpticsView;
import gui.optics_object_creators.FlatMirrorCreator;
import gui.optics_object_creators.OpticsObjectCreator;
import gui.optics_object_creators.RoundedMirrorCreator;

public class MirrorTab extends OpticsTab {

	public MirrorTab(OpticsView view) {
		super();
		this.setText("Mirrors");
		
		OpticsObjectCreator flat = new FlatMirrorCreator(view);
		super.addTab("Flat", flat);
		OpticsObjectCreator rounded = new RoundedMirrorCreator(view);
		super.addTab("Rounded", rounded);
	}
}
