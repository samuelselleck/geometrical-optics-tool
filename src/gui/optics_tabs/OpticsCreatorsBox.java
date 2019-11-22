package gui.optics_tabs;

import java.util.ArrayList;
import java.util.List;

import gui.optics_object_creators.OpticsObjectCreator;
import javafx.beans.InvalidationListener;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.optics_objects.OpticsObject;

public class OpticsCreatorsBox extends TabPane {
	
	OpticsObject editing;
	
	public OpticsCreatorsBox() {
		
		List<OpticsTab> tabs = new ArrayList<>();
		tabs.add(new LensTab());
		tabs.add(new LightTab());
		tabs.add(new MirrorTab());
		tabs.add(new WallTab());
		tabs.add(new OtherTab());
		
		this.getTabs().addAll(tabs);
		this.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		editing = null;
	}

	public OpticsObjectCreator getCurrentOpticsObjectCreator() {
		OpticsTab currentTab = (OpticsTab) this.getSelectionModel().getSelectedItem();
		OpticsObjectCreator curr = currentTab.getCurrentOpticsObjectCreator();
		return curr;
	}

	public void setEditing(OpticsObject obj) {
		if(editing != null) {
			editing.undbind();
		}
		for(Tab tab : this.getTabs()) {
			Tab focus = ((OpticsTab)tab).setEditing(obj);
			if(focus != null) {
				editing = obj;
			    this.getSelectionModel().select(focus);
				return;
			}
		}
	}

	public void onUpdated(InvalidationListener updated) {
		this.getTabs().forEach(t -> {
			((OpticsTab)t).onUpdated(updated);
		});
	}
}
