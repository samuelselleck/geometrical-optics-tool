package settings;

import optics_object_generators.FlatMirrorCreator;
import optics_object_generators.ObjectCreator;
import optics_object_generators.RoundedMirrorCreator;

public class MirrorSettingsTab extends SettingsTab {

	public MirrorSettingsTab() {
		super();
		this.setText("Mirrors");
		
		ObjectCreator flat = new FlatMirrorCreator();
		super.addTab("Flat", flat);
		ObjectCreator rounded = new RoundedMirrorCreator();
		super.addTab("Rounded", rounded);
	}
}
