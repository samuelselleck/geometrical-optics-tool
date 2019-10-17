package gui.subviews;

import java.util.stream.Stream;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import model.LensMaterial;
import model.optics_objects.Lens;

public class AddMaterialWindow extends Stage{
	
	public AddMaterialWindow() {
		BorderPane root = new BorderPane();
		
		root.setStyle(
				"-fx-base: #222222;" +
				"-fx-faint-focus-color: white;"
				);
		
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
		Insets insets = new Insets(5);
		nameColorBox.getChildren().forEach(e -> {
			HBox.setMargin(e, insets);
			HBox.setHgrow(e, Priority.ALWAYS);
		});
		
		HBox coeffsBox = new HBox();

		TextField[] coeffFields = new TextField[6];
		for(int i = 0; i < 6; i++) {
			char c = (char)('b' + i/3);
			int index = i % 3 + 1;
			coeffsBox.getChildren().add(new Label(c + (index + ":")));
			coeffFields[i] = new TextField("0");
			coeffFields[i].setPrefWidth(pwidth/2);
			coeffsBox.getChildren().add(coeffFields[i]);
		}
		coeffsBox.getChildren().forEach(e -> {
			HBox.setMargin(e, insets);
			HBox.setHgrow(e,  Priority.ALWAYS);
		});
		
		HBox addBox = new HBox();
		Button addMaterialButton = new Button("Add Material");
		addMaterialButton.setOnAction(e -> {
			LensMaterial newLensMaterial = new LensMaterial(
					nameField.getText(),
					new double[] { 
							Integer.parseInt(rField.getText())/255.0,
							Integer.parseInt(gField.getText())/255.0,
							Integer.parseInt(bField.getText())/255.0},
					Stream.of(coeffFields)
					.mapToDouble(t -> 
					Double.parseDouble(t.getText().replace(',', '.')))
					.toArray());
			
			
			Lens.MATERIALS.add(newLensMaterial);
		});
		
		addBox.getChildren().add(addMaterialButton);
		HBox.setMargin(addMaterialButton, insets);
		
		root.setTop(nameColorBox);
		root.setCenter(coeffsBox);
		root.setBottom(addBox);
		Scene scene = new Scene(root);
		this.setScene(scene);
		this.setTitle("Add Material");
		this.show();
	}
}
