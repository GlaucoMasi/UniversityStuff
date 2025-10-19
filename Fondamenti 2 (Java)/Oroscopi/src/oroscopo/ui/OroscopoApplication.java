package oroscopo.ui;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import oroscopo.controller.Controller;
import oroscopo.controller.MyController;
import oroscopo.controller.MyStrategiaSelezione;
import oroscopo.model.ArchivioPrevisioni;
import oroscopo.persistence.BadFileFormatException;
import oroscopo.persistence.MyOroscopoReader;


public class OroscopoApplication extends Application {

	private int FORTUNAMIN = 2;
	private String NOMEFILE = "FrasiOroscopo.txt";
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Vedo, prevedo e stravedo");

		ArchivioPrevisioni archivio;
		Controller controller;

		try (Reader inputReader = new FileReader(NOMEFILE)) {
			// ------------------------ lettura file ----------------------
			archivio = new MyOroscopoReader().leggiPrevisioni(inputReader);
			// ------------------------ creazione controller --------------
			controller = new MyController(archivio, new MyStrategiaSelezione());
			// ------------------------ creazione main pane ---------------
			MainPane mainPanel = new MainPane(controller, FORTUNAMIN);
			// ------------------------ attivazione scena -----------------
			Scene scene = new Scene(mainPanel, 980, 480, Color.GRAY);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			Controller.alert("Errore di I/O", "Non è stato possibile aprire il file di testo", "Il file " + NOMEFILE + " non esiste.");
			System.exit(1);
		} catch (BadFileFormatException e) {
			Controller.alert("Errore di Formato", "Non è stato possibile elaborare il file di testo", "Il file " + NOMEFILE + " non è correttamente formattato.");
			System.exit(2);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
