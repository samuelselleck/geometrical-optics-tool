package gui.optics_tabs;

import gui.OpticsEnvironment;
import gui.optics_object_creators.ConcaveLensCreator;
import gui.optics_object_creators.ConvexLensCreator;
import gui.optics_object_creators.CrystallBallCreator;
import gui.optics_object_creators.OpticsObjectCreator;
import gui.optics_object_creators.OptimalConvexLensCreator;
import gui.optics_object_creators.PlanoConvexLensCreator;
import gui.optics_object_creators.PrismCreator;
import gui.optics_object_creators.RectangleLensCreator;
import gui.optics_object_creators.ThinLensCreator;

public class LensTab extends OpticsTab {

	public LensTab(OpticsEnvironment environment) {
		this.setText("Lenses");
		
		OpticsObjectCreator ball = new CrystallBallCreator(environment);
		super.addTab("Ball", ball);
		OpticsObjectCreator prism = new PrismCreator(environment);
		super.addTab("Prism", prism);
		OpticsObjectCreator lensVex = new ConvexLensCreator(environment);
		super.addTab("Convex", lensVex);
		OpticsObjectCreator optVex = new OptimalConvexLensCreator(environment);
		super.addTab("Optimal Convex", optVex);
		OpticsObjectCreator planVex = new PlanoConvexLensCreator(environment);
		super.addTab("Plano-Convex", planVex);
		OpticsObjectCreator lensAve = new ConcaveLensCreator(environment);
		super.addTab("Concave", lensAve);
		OpticsObjectCreator thin = new ThinLensCreator();
		super.addTab("Thin", thin);
		OpticsObjectCreator rect = new RectangleLensCreator(environment);
		super.addTab("Rectangle", rect);
	}
}
