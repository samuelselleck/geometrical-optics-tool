package gui.optics_tabs;

import gui.optics_object_creators.ConcaveLensCreator;
import gui.optics_object_creators.ConvexLensCreator;
import gui.optics_object_creators.CrystallBallCreator;
import gui.optics_object_creators.OpticsObjectCreator;
import gui.optics_object_creators.OptimalConvexLensCreator;
import gui.optics_object_creators.PlanoConvexLensCreator;
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
		OpticsObjectCreator optVex = new OptimalConvexLensCreator();
		super.addTab("Optimal Convex", optVex);
		OpticsObjectCreator planVex = new PlanoConvexLensCreator();
		super.addTab("Plano-Convex", planVex);
		OpticsObjectCreator lensAve = new ConcaveLensCreator();
		super.addTab("Concave", lensAve);
		OpticsObjectCreator rect = new RectangleLensCreator();
		super.addTab("Rectangle", rect);
	}
}
