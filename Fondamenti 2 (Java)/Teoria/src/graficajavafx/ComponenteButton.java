package graficajavafx;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.application.Application;

public class ComponenteButton extends Application {
	@Override
	public void start(Stage stage) {
		stage.setTitle("Rosso/Blu");
		
		FlowPane panel = new FlowPane();
		
		// Creo un bottone e il suo listener che aspetta l'evento
		Button b1 = new Button("Rosso");
		Button b2 = new Button("Blu");
		
		// Il listener implementa l'interfaccia EventHandle<ActionEvent>
		b1.setOnAction(ev -> panel.setStyle("-fx-background: red;"));
		b2.setOnAction(ev -> panel.setStyle("-fx-background: blue;"));
	
		panel.getChildren().addAll(b1, b2);
		Scene scene = new Scene(panel, Color.WHITE);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
