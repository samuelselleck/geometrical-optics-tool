package gui.optics_tabs;

import gui.optics_object_creators.ConcaveLensCreator;
import gui.optics_object_creators.ConvexLensCreator;
import gui.optics_object_creators.CrystallBallCreator;
import gui.optics_object_creators.OpticsObjectCreator;
import gui.optics_object_creators.PrismCreator;
import gui.optics_object_creators.RectangleLensCreator;

public class LensTab extends OpticsTab {

	public LensTab() {
		this.setText("Lenses");
		
		OpticsObjectCreator ball = new CrystallBallCreator();
		super.addTab("Ball", ball);
		OpticsObjectCreator prism = new PrismCreator();
		super.addTab("Prism", prism);
		OpticsObjectCreator lensVex = new ConvexLensCreator();
		super.addTab("Convex", lensVex);
		OpticsObjectCreator lensAve = new ConcaveLensCreator();
		super.addTab("Concave", lensAve);
		OpticsObjectCreator rect = new RectangleLensCreator();
		super.addTab("Rectangle", rect);
	}
}
