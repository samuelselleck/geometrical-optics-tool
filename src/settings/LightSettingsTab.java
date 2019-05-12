package settings;

import gui.OpticsView;
import optics_object_factories.BeamLightSourceFactory;
import optics_object_factories.ConeLightSourceFactory;
import optics_object_factories.OpticsObjectFactory;
import optics_object_factories.PointLightSourceFactory;

public class LightSettingsTab extends SettingsTab {

	public LightSettingsTab(OpticsView view) {
		super();
		this.setText("Lights");
		OpticsObjectFactory pointLightSource = new PointLightSourceFactory(view);
		super.addTab("Point", pointLightSource);
		
		OpticsObjectFactory coneLightSource = new ConeLightSourceFactory(view);
		super.addTab("Cone", coneLightSource);
		 
		OpticsObjectFactory beamLightSource = new BeamLightSourceFactory(view);
		super.addTab("Beam", beamLightSource);
	}
}
