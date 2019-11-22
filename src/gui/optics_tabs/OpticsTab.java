package gui.optics_tabs;

import java.util.ArrayList;

import gui.optics_object_creators.OpticsObjectCreator;
import javafx.beans.InvalidationListener;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);	
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		
		scrollPane.setContent(creator);
		newTab.setContent(scrollPane);
		
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
		for(int i = 0; i < creators.size(); i++) {
			OpticsObjectCreator creator = creators.get(i);
			if(creator.editsOpticsObject(obj)) {
				tabPane.getSelectionModel().select(i);
				creator.bind(obj);
				
				return this;
			}
		}
		return null;
	}

	public void onUpdated(InvalidationListener updated) {
		creators.forEach(c -> {
			c.onUpdated(updated);
		});
	}

	public void unbindAll() {
		for(OpticsObjectCreator c : creators) {
			c.unbind();
		}
	}
}
