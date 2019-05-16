package gui.optics_tabs;

import java.util.ArrayList;

import gui.optics_object_creators.OpticsObjectCreator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.optics_objects.OpticsObject;

public abstract class OpticsTab extends Tab {
	private TabPane tabPane;
	private ArrayList<OpticsObjectCreator> creators;

	public OpticsTab() {
		tabPane = new TabPane();
		creators = new ArrayList<>();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		this.setContent(tabPane);
	}

	protected void addTab(String name, OpticsObjectCreator creator) {
		Tab newTab = new Tab(name);
		newTab.setContent(creator);
		creators.add(creator);
		tabPane.getTabs().add(newTab);
	}

	public OpticsObjectCreator getCurrentOpticsObjectCreator() {
		int index = tabPane.getSelectionModel().getSelectedIndex();
		if (index >= 0) {
			return creators.get(index);
		} else {
			return creators.get(0);
		}
	}
	
	public Tab setEditing(OpticsObject obj) {
		for(Tab t : tabPane.getTabs()) {
			OpticsObjectCreator factory = (OpticsObjectCreator)(t.getContent());
			if(factory.editsOpticsObject(obj)) {
				tabPane.getSelectionModel().select(t);
				factory.bind(obj);
				return this;
			}
		}
		return null;
	}
}
