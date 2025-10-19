package oroscopo.ui;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import oroscopo.controller.Controller;
import oroscopo.controller.MyController;
import oroscopo.controller.MyStrategiaSelezione;
import oroscopo.model.ArchivioPrevisioni;
import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;


public class OroscopoApplicationMock extends OroscopoApplication {

	private int FORTUNAMIN = 2;

	@Override
	public void start(Stage stage) {
		stage.setTitle("Vedo, prevedo, stravedo - MOCK");

		ArchivioPrevisioni archivio;
		Controller controller;

		// ------------------------ lettura file ----------------------
		archivio = new ArchivioPrevisioni(Map.of(
				"AMORE", List.of(
						new Previsione("Nuove occasioni per i single", 4, Set.of(SegnoZodiacale.TORO, SegnoZodiacale.LEONE)),
						new Previsione("grande intimità", 9)
				 ),
				"LAVORO", List.of(
						new Previsione("Grandi soddisfazioni", 6),
						new Previsione("diffidate di un collega invidioso",	2),
						new Previsione("impegnatevi di più", 6, Set.of(SegnoZodiacale.BILANCIA, SegnoZodiacale.CANCRO))
				 ),
				"SALUTE", List.of(
						new Previsione("attenzione alle piccole infiammazioni", 1)
				 )
				));
		// ------------------------ creazione controller --------------
		controller = new MyController(archivio, new MyStrategiaSelezione());
		// ------------------------ creazione main pane ---------------
		MainPane mainPanel = new MainPane(controller, FORTUNAMIN);
		// ------------------------ attivazione scena -----------------
		Scene scene = new Scene(mainPanel, 980, 480, Color.GRAY);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
