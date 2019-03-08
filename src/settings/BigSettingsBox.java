package settings;

import java.util.ArrayList;
import java.util.List;

import gui.Main;
import gui.OpticsController;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import optics_object_factories.OpticsObjectFactory;

public class BigSettingsBox extends HBox {
	TabPane typeTab;

	public BigSettingsBox(OpticsController opticsHandler) {
		
		HBox.setHgrow(this, Priority.ALWAYS);
		
		typeTab = new TabPane();
		typeTab.setPrefWidth(Main.WIDTH / 4);
		
		List<SettingsTab> tabs = new ArrayList<>();
		tabs.add(new LensSettingsTab());
		tabs.add(new LightSettingsTab());
		tabs.add(new MirrorSettingsTab());
		tabs.add(new WallSettingsTab());
		
		opticsHandler.setBeforeObjectCreation(e -> {
			opticsHandler.setOpticsObjectCreator(getCurrentOpticsObjectCreator());
		});
		
		typeTab.getTabs().addAll(tabs);
		typeTab.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		this.getChildren().add(typeTab);
	}

	public OpticsObjectFactory getCurrentOpticsObjectCreator() {
		SettingsTab currentTab = (SettingsTab) typeTab.getSelectionModel().getSelectedItem();
		OpticsObjectFactory curr = currentTab.getCurrentOpticsObjectCreator();
		return curr;
	}
}
