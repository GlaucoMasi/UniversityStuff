package contocorrente.ui;

import java.io.FileReader;
import java.io.IOException;

import contocorrente.controller.Controller;
import contocorrente.model.ContoCorrente;
import contocorrente.persistence.MyCCReader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class ContoCorrenteApp extends Application {
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Simulatore conto corrente");
		try {
				ContoCorrente cc = new MyCCReader().readCC(new FileReader("Movimenti.txt"));
				//
				Controller controller = new Controller(cc);
				MainPane mainPanel = new MainPane(controller);
				//
				Scene scene = new Scene(mainPanel, 830, 500, Color.AQUAMARINE);
				stage.setScene(scene);
				stage.show();
				
		} catch (IOException e) {
			Controller.alert(
					"ERRORE DI I/O",
					"Errore di lettura: impossibile leggere i dati",
					"Dettagli: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			Controller.alert(
					"ERRORE DI I/O",
					"Formato dei file errato: impossibile leggere i dati",
					"Dettagli: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
