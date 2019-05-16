package gui.optics_tabs;

import gui.OpticsView;
import gui.optics_object_creators.BeamLightSourceCreator;
import gui.optics_object_creators.ConeLightSourceCreator;
import gui.optics_object_creators.OpticsObjectCreator;
import gui.optics_object_creators.PointLightSourceCreator;

public class LightTab extends OpticsTab {

	public LightTab(OpticsView view) {
		super();
		this.setText("Lights");
		OpticsObjectCreator pointLightSource = new PointLightSourceCreator(view);
		super.addTab("Point", pointLightSource);
		
		OpticsObjectCreator coneLightSource = new ConeLightSourceCreator(view);
		super.addTab("Cone", coneLightSource);
		 
		OpticsObjectCreator beamLightSource = new BeamLightSourceCreator(view);
		super.addTab("Beam", beamLightSource);
	}
}
