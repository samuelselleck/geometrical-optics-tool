package settings;

import gui.Main;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import optics_logic.OpticsHandler;
import optics_object_generators.ObjectCreator;

public class BigSettingsBox extends HBox {
	TabPane typeTab;

	public BigSettingsBox(OpticsHandler opticsHandler) {
		
		HBox.setHgrow(this, Priority.ALWAYS);
		
		typeTab = new TabPane();
		typeTab.setPrefWidth(Main.WIDTH / 5);
		
		SettingsTab[] tabs = new SettingsTab[4];
		tabs[0] = new LensSettingsTab();
		tabs[1] = new LightSettingsTab();
		tabs[2] = new MirrorSettingsTab();
		tabs[3] = new WallSettingsTab();
		opticsHandler.setEvent(e -> {
			opticsHandler.setOpticsObjectCreator(getCurrentOpticsObjectCreator());
		});
		typeTab.getTabs().addAll(tabs);
		typeTab.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		this.getChildren().add(typeTab);
	}

	public ObjectCreator getCurrentOpticsObjectCreator() {
		SettingsTab currentTab = (SettingsTab) typeTab.getSelectionModel().getSelectedItem();
		ObjectCreator curr = currentTab.getCurrentOpticsObjectCreator();
		return curr;
	}
}
