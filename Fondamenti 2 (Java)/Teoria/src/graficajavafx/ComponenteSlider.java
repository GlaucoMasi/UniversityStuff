package graficajavafx;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.Slider;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.TextField;

import java.util.Locale;
import java.text.NumberFormat;

import javafx.application.Application;

public class ComponenteSlider extends Application {
	@Override
	public void start(Stage stage) {
		stage.setTitle("Componente Slider");
		
		FlowPane panel = new FlowPane();
		TextField txt1 = new TextField(); txt1.setPrefColumnCount(25); txt1.setEditable(false);
		NumberFormat formatter = NumberFormat.getNumberInstance(Locale.ITALY);
		formatter.setMaximumFractionDigits(2);
		
		Slider slider = new Slider(0, 10, 5); // min, max, initvalue
		
		slider.valueProperty().addListener( (obj, oldval, newval) -> {
			txt1.setText("Valore corrente: " + formatter.format(newval) + " (era: " + formatter.format(oldval) + ")");
		});
		
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(1);
		slider.setMinorTickCount(3);
		slider.setBlockIncrement(1);
		slider.setSnapToTicks(true);
		
		panel.getChildren().addAll(slider, txt1);
		Scene scene = new Scene(panel, Color.WHITE);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
