package settings;

import optics_object_factories.BeamLightSourceFactory;
import optics_object_factories.ConeLightSourceFactory;
import optics_object_factories.OpticsObjectFactory;
import optics_object_factories.PointLightSourceFactory;

public class LightSettingsTab extends SettingsTab {

	public LightSettingsTab() {
		super();
		this.setText("Lights");
		OpticsObjectFactory pointLightSource = new PointLightSourceFactory();
		super.addTab("Point", pointLightSource);
		
		OpticsObjectFactory coneLightSource = new ConeLightSourceFactory();
		super.addTab("Cone", coneLightSource);
		 
		OpticsObjectFactory beamLightSource = new BeamLightSourceFactory();
		super.addTab("Beam", beamLightSource);
	}
}
