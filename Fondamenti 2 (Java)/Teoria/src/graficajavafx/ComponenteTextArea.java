package graficajavafx;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.application.Application;
import javafx.scene.control.TextArea;

public class ComponenteTextArea extends Application {
	@Override
	public void start(Stage stage) {
		stage.setTitle("Componente TextArea");
		FlowPane panel = new FlowPane();
		
		TextArea area = new TextArea();
		Label label = new Label("Caratteri: 0");
		
		Button b1 = new Button("Aggiorna");
		b1.setOnAction(ev -> label.setText("Caratteri: " + area.getText().length()));
		
		panel.getChildren().addAll(area, b1, label);
		Scene scene = new Scene(panel, Color.WHITE);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
