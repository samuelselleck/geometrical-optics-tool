package settings;

import optics_object_generators.BeamLightSourceCreator;
import optics_object_generators.ConeLightSourceCreator;
import optics_object_generators.ObjectCreator;
import optics_object_generators.PointLightSourceCreator;

public class LightSettingsTab extends SettingsTab {

	public LightSettingsTab() {
		super();
		this.setText("Lights");
		ObjectCreator pointLightSource = new PointLightSourceCreator();
		super.addTab("Point", pointLightSource);
		
		ObjectCreator coneLightSource = new ConeLightSourceCreator();
		super.addTab("Cone", coneLightSource);
		 
		ObjectCreator beamLightSource = new BeamLightSourceCreator();
		super.addTab("Beam", beamLightSource);
	}
}
