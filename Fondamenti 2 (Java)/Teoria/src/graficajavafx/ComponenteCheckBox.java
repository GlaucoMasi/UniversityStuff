package graficajavafx;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import javafx.application.Application;

public class ComponenteCheckBox extends Application {
	// Variabili a livello di classe, visibili anche fuori dal metodo start
	private CheckBox ck1, ck2;
	private TextField txt1, txt2;
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Componente CheckBox");
		
		FlowPane panel = new FlowPane();
		
		// Il primo riporta l'ultima modifica, il secondo la situazione complessiva
		txt1 = new TextField("Opzione disattivata"); txt1.setPrefColumnCount(25); txt1.setEditable(false);
		txt2 = new TextField("Opzione disattivata"); txt1.setPrefColumnCount(25); txt1.setEditable(false);
		
		ck1 = new CheckBox("Pere"); ck2 = new CheckBox("Mele");
		
		ck1.setOnAction(ev -> {
			txt1.setText("Sono cambiate le pere");
			GeneralUpdater();
		});
		
		ck2.setOnAction(ev -> {
			txt2.setText("Sono cambiate le mele");
			GeneralUpdater();
		});
		
		panel.getChildren().addAll(ck1, ck2, txt1, txt2);
		Scene scene = new Scene(panel, Color.WHITE);
		stage.setScene(scene);
		stage.show();
	}
	
	private void GeneralUpdater() {
		txt2.setText((ck1.isSelected() ? "Pere " : "") + (ck2.isSelected() ? "Mele" : ""));
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
