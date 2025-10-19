package graficajavafx;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class ComponentiTextFieldPasswordField extends Application {
	@Override
	public void start(Stage stage) {
		stage.setTitle("Componenti TextField e PasswordField");
		FlowPane panel = new FlowPane();
		
//		TextField txt1 = new TextField("Scrivi qui il tuo messaggio");
		PasswordField txt1 = new PasswordField();
		txt1.setPrefColumnCount(25);
		txt1.setTooltip(new Tooltip("Inserire qui la nuova password"));
		
		TextField txt2 = new TextField(); txt2.setEditable(false);
		txt2.setPrefColumnCount(25);
		
		Button b1 = new Button("Svela");
		b1.setOnAction(ev -> txt2.setText(txt1.getText()));
		
		panel.getChildren().addAll(txt1, txt2, b1);
		Scene scene = new Scene(panel, Color.WHITE);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
