package gui.subviews;

import gui.Main;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class AddMaterialWindow extends Stage{
	
	public AddMaterialWindow() {
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: #222222; -fx-base: black; -fx-fill: black;" +
				" -fx-focus-color: gray; -fx-faint-focus-color: white;");
		
		double pwidth = 80;
		
		HBox nameColorBox = new HBox();
		
		Label nameLabel = new Label("Name:");
		TextField nameField = new TextField();
		nameField.setPrefWidth(1.5*pwidth);
		
		Label colorLabel = new Label("Color (rgb):");
		TextField rField = new TextField("0");
		rField.setPrefWidth(pwidth);
		TextField gField = new TextField("0");
		gField.setPrefWidth(pwidth);
		TextField bField = new TextField("0");
		bField.setPrefWidth(pwidth);
		
		
		nameColorBox.getChildren().addAll(nameLabel, nameField, colorLabel, rField, gField, bField);
		Insets insets = new Insets(10);
		nameColorBox.getChildren().forEach(e -> {
			HBox.setMargin(e, insets);
			HBox.setHgrow(e, Priority.ALWAYS);
		});
		root.setTop(nameColorBox);
		Scene scene = new Scene(root, Main.getIntProperty("width")/2, Main.getIntProperty("height")/3);
		this.setScene(scene);
		this.setTitle("Add Material");
		this.show();
	}
}
