package graficajavafx;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.application.Application;

public class ComponenteLabel extends Application {
	@Override
	public void start(Stage stage) {
		stage.setTitle("Componente Panel");
		
		// StackPane, HBox, VBox, TilePane, FlowPane, AnchorPane, BorderPane, GridPane
		FlowPane panel = new FlowPane();
		
		// Label è in Region
		Label l1 = new Label("Label 1");
		Label l2 = new Label("Label 2");
		l1.setFont(Font.font("Courier New", FontWeight.BOLD, 24));
		l2.setFont(Font.font("Times New Roman", FontPosture.ITALIC, 18));
		
		// Creo la scena con i pannelli e la metto sul palco
		panel.getChildren().addAll(l1, l2);
		Scene scene = new Scene(panel, Color.WHITE);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}