package settings;

import optics_object_factories.FlatMirrorFactory;
import optics_object_factories.OpticsObjectFactory;
import optics_object_factories.RoundedMirrorFactory;

public class MirrorSettingsTab extends SettingsTab {

	public MirrorSettingsTab() {
		super();
		this.setText("Mirrors");
		
		OpticsObjectFactory flat = new FlatMirrorFactory();
		super.addTab("Flat", flat);
		OpticsObjectFactory rounded = new RoundedMirrorFactory();
		super.addTab("Rounded", rounded);
	}
}
