package settings;

import java.util.ArrayList;
import java.util.List;

import gui.Main;
import gui.OpticsController;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import optics_object_factories.OpticsObjectFactory;
import optics_objects.templates.OpticsObject;

public class BigSettingsBox extends HBox {
	TabPane typeTab;

	public BigSettingsBox(OpticsController opticsHandler) {
		
		HBox.setHgrow(this, Priority.ALWAYS);
		opticsHandler.setSettingsBox(this);
		
		typeTab = new TabPane();
			
		typeTab.setPrefWidth(Main.WIDTH / 4);
		
		List<SettingsTab> tabs = new ArrayList<>();
		tabs.add(SettingsMap.LENS_SETTINGS, new LensSettingsTab());
		tabs.add(SettingsMap.LIGHT_SETTINGS, new LightSettingsTab());
		tabs.add(SettingsMap.MIRROR_SETTINGS, new MirrorSettingsTab());
		tabs.add(SettingsMap.WALL_SETTINGS, new WallSettingsTab());
		
		for(SettingsTab tab: tabs) {
			tab.addListeners(opticsHandler.getDeselectListener(),opticsHandler.getSettingsUpdateListener());
		}
		
		opticsHandler.setBeforeObjectCreation(e -> {
			opticsHandler.setOpticsObjectCreator(getCurrentOpticsObjectCreator());
		});
		
		typeTab.getTabs().addAll(tabs);
		typeTab.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		this.getChildren().add(typeTab);

		typeTab.addEventHandler(MouseEvent.MOUSE_PRESSED,opticsHandler.getDeselectListener());
		
		SettingsMap.initSettingsMap();
		
	}

	public OpticsObjectFactory getCurrentOpticsObjectCreator() {
		SettingsTab currentTab = (SettingsTab) typeTab.getSelectionModel().getSelectedItem();
		OpticsObjectFactory curr = currentTab.getCurrentOpticsObjectCreator();
		return curr;
	}
	
	public void selectSettingsTab(OpticsObject select) {
		typeTab.getSelectionModel().select(SettingsMap.getSettingId(select));
		((SettingsTab) typeTab.getSelectionModel().getSelectedItem()).
				selectCreator(SettingsMap.getCreatorId(select));
	}
	
}
