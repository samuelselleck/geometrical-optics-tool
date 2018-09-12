package settings;

import java.util.ArrayList;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import optics_object_generators.ObjectCreator;

public abstract class SettingsTab extends Tab {
	private TabPane tabPane;
	private ArrayList<ObjectCreator> creators;

	public SettingsTab() {
		tabPane = new TabPane();
		creators = new ArrayList<>();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		this.setContent(tabPane);
	}

	protected void addTab(String name, ObjectCreator creator) {
		Tab newTab = new Tab(name);
		newTab.setContent(creator);
		creators.add(creator);
		tabPane.getTabs().add(newTab);
	}

	public ObjectCreator getCurrentOpticsObjectCreator() {
		int index = tabPane.getSelectionModel().getSelectedIndex();
		if (index >= 0) {
			return creators.get(index);
		} else {
			return creators.get(0);
		}

	}
}
