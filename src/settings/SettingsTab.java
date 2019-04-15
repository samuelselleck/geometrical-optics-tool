package settings;

import java.util.ArrayList;

import gui.OpticsController;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import optics_object_factories.OpticsObjectFactory;

public abstract class SettingsTab extends Tab {
	private TabPane tabPane;
	private ArrayList<OpticsObjectFactory> creators;

	public SettingsTab() {
		tabPane = new TabPane();
		creators = new ArrayList<>();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		this.setContent(tabPane);
	}

	protected void addTab(String name, OpticsObjectFactory creator) {
		Tab newTab = new Tab(name);
		newTab.setContent(creator);
		creators.add(creator);
		tabPane.getTabs().add(newTab);
	}
	
	public void addListeners(EventHandler deselect, EventHandler update) {
		tabPane.addEventHandler(MouseEvent.MOUSE_PRESSED, deselect);
	
		for(OpticsObjectFactory factory: creators) {
			factory.setSliderListener(update);
		}
	}

	public OpticsObjectFactory getCurrentOpticsObjectCreator() {
		int index = tabPane.getSelectionModel().getSelectedIndex();
		if (index >= 0) {
			return creators.get(index);
		} else {
			return creators.get(0);
		}

	}
}
