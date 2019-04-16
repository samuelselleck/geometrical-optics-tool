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
		super.addTab("Prism", prism);
		OpticsObjectFactory lensVex = new ConvexLensFactory();
		super.addTab("Convex", lensVex);
		OpticsObjectFactory lensOptVex = new OptimalConvexLensFactory();
		super.addTab("Optimal", lensOptVex);
		OpticsObjectFactory lensAve = new ConcaveLensFactory();
		super.addTab("Concave", lensAve);
		OpticsObjectFactory ball = new CrystallBallFactory();
		super.addTab("Ball", ball);
		OpticsObjectFactory rect = new RectangleLensFactory();
		super.addTab("Rectangle", rect);
	}
}
