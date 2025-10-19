package graficajavafx;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.Spinner;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.TextField;

import java.util.Locale;
import java.text.NumberFormat;

import javafx.application.Application;

public class ComponenteSpinner extends Application {
	@Override
	public void start(Stage stage) {
		stage.setTitle("Componente Spinner");
		
		FlowPane panel = new FlowPane();
		TextField txt1 = new TextField(); txt1.setPrefColumnCount(25); txt1.setEditable(false);
		NumberFormat formatter = NumberFormat.getNumberInstance(Locale.ITALY);
		formatter.setMaximumFractionDigits(2);
		
		// Lo spinner è tipizzato
		Spinner<Integer> spinner = new Spinner<Integer>(0, 10, 5); // min, max, initvalue
		
		spinner.valueProperty().addListener( (obj, oldval, newval) -> {
			txt1.setText("Valore corrente: " + formatter.format(newval) + " (era: " + formatter.format(oldval) + ")");
		});
		
		panel.getChildren().addAll(spinner, txt1);
		Scene scene = new Scene(panel, Color.WHITE);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
