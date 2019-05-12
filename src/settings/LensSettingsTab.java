package settings;

import gui.OpticsView;
import optics_object_factories.ConcaveLensFactory;
import optics_object_factories.ConvexLensFactory;
import optics_object_factories.CrystallBallFactory;
import optics_object_factories.OpticsObjectFactory;
import optics_object_factories.PrismFactory;
import optics_object_factories.RectangleLensFactory;

public class LensSettingsTab extends SettingsTab {

	public LensSettingsTab(OpticsView view) {
		this.setText("Lenses");
		
		OpticsObjectFactory ball = new CrystallBallFactory(view);
		super.addTab("Ball", ball);
		OpticsObjectFactory prism = new PrismFactory(view);
		super.addTab("Prism", prism);
		OpticsObjectFactory lensVex = new ConvexLensFactory(view);
		super.addTab("Convex", lensVex);
		OpticsObjectFactory lensAve = new ConcaveLensFactory(view);
		super.addTab("Concave", lensAve);
		OpticsObjectFactory rect = new RectangleLensFactory(view);
		super.addTab("Rectangle", rect);
	}
}
