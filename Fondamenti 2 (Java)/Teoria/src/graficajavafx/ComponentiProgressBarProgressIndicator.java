package graficajavafx;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

import java.util.Locale;
import java.text.NumberFormat;

import javafx.application.Application;

public class ComponentiProgressBarProgressIndicator extends Application {
	@Override
	public void start(Stage stage) {
		stage.setTitle("Componenti ProgressBar e ProgressIndicator");
		
		FlowPane panel = new FlowPane();
		TextField txt1 = new TextField(); txt1.setPrefColumnCount(25); txt1.setEditable(false);
		NumberFormat formatter = NumberFormat.getPercentInstance(Locale.ITALY);
		formatter.setMaximumFractionDigits(2);
		
		ProgressBar bar = new ProgressBar(0.25);
		ProgressIndicator pid = new ProgressIndicator(0.25);
		
		// progressProperty, invece di valueProperty
		bar.progressProperty().addListener( (obj, oldval, newval) -> {
			pid.setProgress(newval.doubleValue());
		});
		
		Button b1 = new Button("-"), b2 = new Button("+");
		b1.setOnAction(ev -> bar.setProgress(Math.max(bar.progressProperty().get()-0.25, 0)));
		b2.setOnAction(ev -> bar.setProgress(Math.min(bar.progressProperty().get()+0.25, 1)));
		
		panel.getChildren().addAll(bar, pid, b1, b2);
		Scene scene = new Scene(panel, Color.WHITE);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
