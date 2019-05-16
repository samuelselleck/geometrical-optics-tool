package gui.optics_tabs;

import java.util.ArrayList;
import java.util.List;

import gui.Main;
import gui.OpticsView;
import gui.optics_object_creators.OpticsObjectCreator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import model.optics_objects.OpticsObject;

public class OpticsBox extends HBox {
	TabPane typeTab;
	OpticsObject editing;
	
	public OpticsBox(OpticsView view) {
		
		HBox.setHgrow(this, Priority.ALWAYS);
		
		typeTab = new TabPane();
		typeTab.setPrefWidth(Main.WIDTH / 4);
		
		List<OpticsTab> tabs = new ArrayList<>();
		tabs.add(new LensTab(view));
		tabs.add(new LightTab(view));
		tabs.add(new MirrorTab(view));
		tabs.add(new WallTab(view));
		
		
		
		typeTab.getTabs().addAll(tabs);
		typeTab.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		this.getChildren().add(typeTab);
		editing = null;
	}

	public OpticsObjectCreator getCurrentOpticsObjectCreator() {
		OpticsTab currentTab = (OpticsTab) typeTab.getSelectionModel().getSelectedItem();
		OpticsObjectCreator curr = currentTab.getCurrentOpticsObjectCreator();
		return curr;
	}

	public void setEditing(OpticsObject obj) {
		if(editing != null) {
			editing.undbind();
		}
		for(Tab tab : typeTab.getTabs()) {
			Tab focus = ((OpticsTab)tab).setEditing(obj);
			if(focus != null) {
				editing = obj;
				typeTab.getSelectionModel().select(focus);
				return;
			}
		}
	}
}