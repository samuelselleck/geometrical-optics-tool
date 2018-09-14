package settings;

import optics_object_factories.ConcaveLensFactory;
import optics_object_factories.ConvexLensFactory;
import optics_object_factories.CrystallBallFactory;
import optics_object_factories.OpticsObjectFactory;
import optics_object_factories.PrismFactory;

public class LensSettingsTab extends SettingsTab {

	public LensSettingsTab() {
		this.setText("Lenses");
		
		OpticsObjectFactory prism = new PrismFactory();
		super.addTab("Prism", prism);
		OpticsObjectFactory lensVex = new ConvexLensFactory();
		super.addTab("Convex", lensVex);
		OpticsObjectFactory lensAve = new ConcaveLensFactory();
		super.addTab("Concave", lensAve);
		OpticsObjectFactory ball = new CrystallBallFactory();
		super.addTab("Ball", ball);
	}
}
