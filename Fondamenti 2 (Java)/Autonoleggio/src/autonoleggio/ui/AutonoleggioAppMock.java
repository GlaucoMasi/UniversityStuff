package autonoleggio.ui;

import java.time.LocalTime;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import autonoleggio.controller.Controller;
import autonoleggio.model.Agency;
import autonoleggio.model.CarType;
import autonoleggio.model.OpeningTime;
import autonoleggio.model.Rate;
import autonoleggio.model.Slot;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class AutonoleggioAppMock extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("Autonoleggio - MOCK");
		//
		var slotMattino    = new Slot(LocalTime.of(8,0), LocalTime.of(12,30));
		var slotPomeriggio = new Slot(LocalTime.of(14,30), LocalTime.of(18,30));
		var slotUnico      = new Slot(LocalTime.of(8,30), LocalTime.of(23,59));
		var openingTimeSoloMattino = new OpeningTime(slotMattino);
		var openingTimeOrarioContinuato = new OpeningTime(slotUnico);
		var openingTimeMattinoEPomeriggio = new OpeningTime(slotMattino, slotPomeriggio);
		
		var agency1 = new Agency("Bologna", "centro1",   openingTimeOrarioContinuato, openingTimeSoloMattino, OpeningTime.CHIUSO);
		var agency2 = new Agency("Bologna", "aeroporto", openingTimeMattinoEPomeriggio, openingTimeMattinoEPomeriggio, openingTimeMattinoEPomeriggio);
		var agency3 = new Agency("Bologna", "centro2",   openingTimeMattinoEPomeriggio, OpeningTime.CHIUSO, OpeningTime.CHIUSO);

		var rate1 = new Rate(CarType.A, 45.50, 80.30,  100.0);
		var rate2 = new Rate(CarType.B, 51.66, 93.30,  120.0);
		var rate3 = new Rate(CarType.C, 57.77, 110.00, 140.0);
		var rate4 = new Rate(CarType.D, 78.80, 155.00, 180.0);
		
		var controller = new Controller(Set.of(agency1,agency2,agency3), new TreeMap<>(Map.of(
				CarType.A, rate1, CarType.B, rate2, CarType.C, rate3, CarType.D, rate4
				)));
		MainPane mainPanel = new MainPane(controller);
		//
		Scene scene = new Scene(mainPanel, 450, 280, Color.AQUAMARINE);
		stage.setScene(scene);stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	

}
