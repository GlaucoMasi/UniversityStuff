package graficajavafx;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import javafx.application.Application;

public class ComponenteToggle extends Application {
	@Override
	public void start(Stage stage) {
		stage.setTitle("Componente Toggle");
		
		FlowPane panel = new FlowPane();
		TextField txt1 = new TextField("Opzione disattivata"); txt1.setPrefColumnCount(25); txt1.setEditable(false);
		
		// Due posizioni stabili, a differenza del Button
		ToggleButton tg1 = new ToggleButton("Toggle");
		tg1.setOnAction(ev -> txt1.setText(
				tg1.isSelected() ? "Opzione attivata" : "Opzione disattivata"));
		
		panel.getChildren().addAll(tg1, txt1);
		Scene scene = new Scene(panel, Color.WHITE);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
