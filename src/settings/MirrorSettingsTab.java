package settings;

import gui.OpticsView;
import optics_object_factories.FlatMirrorFactory;
import optics_object_factories.OpticsObjectFactory;
import optics_object_factories.RoundedMirrorFactory;

public class MirrorSettingsTab extends SettingsTab {

	public MirrorSettingsTab(OpticsView view) {
		super();
		this.setText("Mirrors");
		
		OpticsObjectFactory flat = new FlatMirrorFactory(view);
		super.addTab("Flat", flat);
		OpticsObjectFactory rounded = new RoundedMirrorFactory(view);
		super.addTab("Rounded", rounded);
	}
}
