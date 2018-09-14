package settings;

import optics_object_factories.OpticsObjectFactory;
import optics_object_factories.RectangleWallFactory;

public class WallSettingsTab extends SettingsTab {

	public WallSettingsTab() {
		super();
		this.setText("Walls");
		
		OpticsObjectFactory rect = new RectangleWallFactory();
		super.addTab("Rectangle", rect);
	}
}
