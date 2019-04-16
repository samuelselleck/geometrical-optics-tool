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

/*
 * Huvudtab
 * 	- (Om valt objekts undertab ligger under, behåll val, annars ta bort)
 *  - nu: Ta bort val
 * Undertab
 *  - (Om valt objekt: behåll, annars ta bort)
 *  - nu: Ta bort val
 * Slider
 *  - Om valt objekt: uppdatera, annars ta bort
 *  
 *  När objekt väljs: Gå till rätt flik och sätt rätt värden
 * */

public class BigSettingsBox extends HBox {
	TabPane typeTab;

	public BigSettingsBox(OpticsController opticsHandler) {
		
		HBox.setHgrow(this, Priority.ALWAYS);
		opticsHandler.setSettingsBox(this);
		
		typeTab = new TabPane();
			
		typeTab.setPrefWidth(Main.WIDTH / 4);
		
		List<SettingsTab> tabs = new ArrayList<>();
		tabs.add(LENS_SETTINGS, new LensSettingsTab().setId(LENS_SETTINGS));
		tabs.add(LIGHT_SETTINGS, new LightSettingsTab().setId(LIGHT_SETTINGS));
		tabs.add(MIRROR_SETTINGS, new MirrorSettingsTab().setId(MIRROR_SETTINGS));
		tabs.add(WALL_SETTINGS, new WallSettingsTab().setId(WALL_SETTINGS));
		
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
		
	}

	public OpticsObjectFactory getCurrentOpticsObjectCreator() {
		SettingsTab currentTab = (SettingsTab) typeTab.getSelectionModel().getSelectedItem();
		OpticsObjectFactory curr = currentTab.getCurrentOpticsObjectCreator();
		return curr;
	}
	
	public void selectSettingsTab(OpticsObject select) {
		typeTab.getSelectionModel().select(select.getSettingId());
		((SettingsTab) typeTab.getSelectionModel().getSelectedItem()).selectCreator(select.getCreatorId());
	}
	
	public static final int LENS_SETTINGS = 0;
	public static final int LIGHT_SETTINGS = 1;
	public static final int MIRROR_SETTINGS = 2;
	public static final int WALL_SETTINGS = 3;
}
