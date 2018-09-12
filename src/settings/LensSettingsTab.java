package settings;

import optics_object_generators.ConcaveLensCreator;
import optics_object_generators.ConvexLensCreator;
import optics_object_generators.CrystallBallCreator;
import optics_object_generators.ObjectCreator;
import optics_object_generators.PrismCreator;

public class LensSettingsTab extends SettingsTab {

	public LensSettingsTab() {
		this.setText("Lenses");
		
		ObjectCreator prism = new PrismCreator();
		super.addTab("Prism", prism);
		ObjectCreator lensVex = new ConvexLensCreator();
		super.addTab("Convex", lensVex);
		ObjectCreator lensAve = new ConcaveLensCreator();
		super.addTab("Concave", lensAve);
		ObjectCreator ball = new CrystallBallCreator();
		super.addTab("Ball", ball);
	}
}
