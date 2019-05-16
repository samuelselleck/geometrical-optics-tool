package gui.optics_tabs;

import gui.OpticsView;
import gui.optics_object_creators.ConcaveLensCreator;
import gui.optics_object_creators.ConvexLensCreator;
import gui.optics_object_creators.CrystallBallCreator;
import gui.optics_object_creators.OpticsObjectCreator;
import gui.optics_object_creators.PrismCreator;
import gui.optics_object_creators.RectangleLensCreator;

public class LensTab extends OpticsTab {

	public LensTab(OpticsView view) {
		this.setText("Lenses");
		
		OpticsObjectCreator ball = new CrystallBallCreator(view);
		super.addTab("Ball", ball);
		OpticsObjectCreator prism = new PrismCreator(view);
		super.addTab("Prism", prism);
		OpticsObjectCreator lensVex = new ConvexLensCreator(view);
		super.addTab("Convex", lensVex);
		OpticsObjectCreator lensAve = new ConcaveLensCreator(view);
		super.addTab("Concave", lensAve);
		OpticsObjectCreator rect = new RectangleLensCreator(view);
		super.addTab("Rectangle", rect);
	}
}
