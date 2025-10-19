package graficajavafx;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import javafx.application.Application;

public class ComponenteRadioButton extends Application {
	// Il RadioButton è usato per opzioni mutualmente exclusive. Ce ne sono almeno due, quindi si una ToggleGroup
	private RadioButton b1, b2, b3;
	private ToggleGroup tg;
	private TextField txt1;
	@Override
	public void start(Stage stage) {
		stage.setTitle("Componente RadioButton");
		
		FlowPane panel = new FlowPane();
		txt1 = new TextField(); txt1.setPrefColumnCount(25); txt1.setEditable(false);
		
		tg = new ToggleGroup();
		b1 = new RadioButton("Pere");    b1.setToggleGroup(tg);
		b2 = new RadioButton("Mele");    b2.setToggleGroup(tg);
		b3 = new RadioButton("Arance");  b3.setToggleGroup(tg);
		
		tg.selectedToggleProperty().addListener( (obj, oldval, newval) -> {
			txt1.setText("Opzione selezionata: " + ((RadioButton) newval).getText());
		});
		
		panel.getChildren().addAll(b1, b2, b3, txt1);
		Scene scene = new Scene(panel, Color.WHITE);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
