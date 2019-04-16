package settings;

import optics_object_factories.ConcaveLensFactory;
import optics_object_factories.ConvexLensFactory;
import optics_object_factories.CrystallBallFactory;
import optics_object_factories.OpticsObjectFactory;
import optics_object_factories.OptimalConvexLensFactory;
import optics_object_factories.PrismFactory;
import optics_object_factories.RectangleLensFactory;

public class LensSettingsTab extends SettingsTab {

	public LensSettingsTab() {
		this.setText("Lenses");
		
		OpticsObjectFactory prism = new PrismFactory();
		super.addTab("Prism", prism.setId(settingsId, 0));
		OpticsObjectFactory lensVex = new ConvexLensFactory();
		super.addTab("Convex", lensVex.setId(settingsId, 1));
		OpticsObjectFactory lensOptVex = new OptimalConvexLensFactory();
		super.addTab("Optimal", lensOptVex.setId(settingsId, 2));
		OpticsObjectFactory lensAve = new ConcaveLensFactory();
		super.addTab("Concave", lensAve.setId(settingsId, 3));
		OpticsObjectFactory ball = new CrystallBallFactory();
		super.addTab("Ball", ball.setId(settingsId, 4));
		OpticsObjectFactory rect = new RectangleLensFactory();
		super.addTab("Rectangle", rect.setId(settingsId, 5));
	}
}
