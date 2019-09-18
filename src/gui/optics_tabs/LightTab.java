package gui.optics_tabs;

import gui.optics_object_creators.BeamLightSourceCreator;
import gui.optics_object_creators.ConeLightSourceCreator;
import gui.optics_object_creators.OpticsObjectCreator;
import gui.optics_object_creators.PointLightSourceCreator;

public class LightTab extends OpticsTab {

	public LightTab() {
		super();
		this.setText("Lights");
		OpticsObjectCreator pointLightSource = new PointLightSourceCreator();
		super.addTab("Point", pointLightSource);
		
		OpticsObjectCreator coneLightSource = new ConeLightSourceCreator();
		super.addTab("Cone", coneLightSource);
		 
		OpticsObjectCreator beamLightSource = new BeamLightSourceCreator();
		super.addTab("Beam", beamLightSource);
	}
}
