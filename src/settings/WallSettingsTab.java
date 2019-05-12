package settings;

import gui.OpticsView;
import optics_object_factories.OpticsObjectFactory;
import optics_object_factories.RectangleWallFactory;

public class WallSettingsTab extends SettingsTab {

	public WallSettingsTab(OpticsView view) {
		this.setText("Walls");
		
		OpticsObjectFactory rect = new RectangleWallFactory(view);
		super.addTab("Rectangle", rect);
	}
}
