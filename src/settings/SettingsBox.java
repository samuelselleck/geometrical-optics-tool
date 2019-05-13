package settings;

import java.util.ArrayList;
import java.util.List;

import gui.Main;
import gui.OpticsView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import optics_object_factories.OpticsObjectFactory;
import optics_objects.templates.OpticsObject;

public class SettingsBox extends HBox {
	TabPane typeTab;
	OpticsObject editing;
	
	public SettingsBox(OpticsView view) {
		
		HBox.setHgrow(this, Priority.ALWAYS);
		
		typeTab = new TabPane();
		typeTab.setPrefWidth(Main.WIDTH / 4);
		
		List<SettingsTab> tabs = new ArrayList<>();
		tabs.add(new LensSettingsTab(view));
		tabs.add(new LightSettingsTab(view));
		tabs.add(new MirrorSettingsTab(view));
		tabs.add(new WallSettingsTab(view));
		
		
		
		typeTab.getTabs().addAll(tabs);
		typeTab.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		this.getChildren().add(typeTab);
		editing = null;
	}

	public OpticsObjectFactory getCurrentOpticsObjectCreator() {
		SettingsTab currentTab = (SettingsTab) typeTab.getSelectionModel().getSelectedItem();
		OpticsObjectFactory curr = currentTab.getCurrentOpticsObjectCreator();
		return curr;
	}

	public void setEditing(OpticsObject obj) {
		if(editing != null) {
			editing.undbind();
		}
		for(Tab tab : typeTab.getTabs()) {
			Tab focus = ((SettingsTab)tab).setEditing(obj);
			if(focus != null) {
				editing = obj;
				typeTab.getSelectionModel().select(focus);
				return;
			}
		}
	}
}
