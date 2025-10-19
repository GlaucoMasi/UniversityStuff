package autonoleggio.ui;

import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.SortedMap;

import autonoleggio.controller.Controller;
import autonoleggio.model.Agency;
import autonoleggio.model.CarType;
import autonoleggio.model.Rate;
import autonoleggio.persistence.BadFileFormatException;
import autonoleggio.persistence.MyAgencyReader;
import autonoleggio.persistence.MyRateReader;
import autonoleggio.persistence.RateReader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class AutonoleggioApp extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("Autonoleggio");

		MyAgencyReader agencyReader = new MyAgencyReader();
		RateReader rateReader = new MyRateReader();
		
		Set<Agency> agencies = null;
		try {
			agencies = agencyReader.readAllAgencies(new FileReader("Agencies.txt"));
		} catch (BadFileFormatException | IOException e) {
			Controller.alert("Agencies.txt: errore di lettura o formato del file errato", "Impossibile leggere i dati",
					"Dettagli:\n" + e.getMessage());
		}

		SortedMap<CarType, Rate> rates = null;
		try {
			rates = rateReader.readAllRates(new FileReader("Rates.txt"));
		} catch (BadFileFormatException | IOException e) {
			Controller.alert("Rates.txt: errore di lettura o formato del file errato", "Impossibile leggere i dati",
					"Dettagli:\n" + e.getMessage());
		}

		var controller = new Controller(agencies, rates);
		MainPane mainPanel = new MainPane(controller);
		//
		Scene scene = new Scene(mainPanel, 450, 280, Color.AQUAMARINE);
		stage.setScene(scene);stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
