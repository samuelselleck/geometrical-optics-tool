package settings;

import optics_object_factories.DiffractionGratingFactory;
import optics_object_factories.OpticsObjectFactory;

public class DiffractionGratingSettingsTab extends SettingsTab {

    public DiffractionGratingSettingsTab() {
        super();
        this.setText("Diffraction Grating");

        OpticsObjectFactory rect = new DiffractionGratingFactory();
        super.addTab("Diffraction Grating", rect);
    }

}
