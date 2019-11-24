package gui.optics_tabs;

import gui.optics_object_creators.FlatGratingCreator;
import gui.optics_object_creators.OpticsObjectCreator;

public class OtherTab extends OpticsTab {

	public OtherTab() {
		this.setText("Other");
		
		OpticsObjectCreator git = new FlatGratingCreator();
		super.addTab("Gitter", git);
	}
}
