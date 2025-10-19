package contocorrente.ui;

import java.time.LocalDate;

import contocorrente.controller.Controller;
import contocorrente.model.Movimento;
import contocorrente.model.ContoCorrente;
import contocorrente.model.Tipologia;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ContoCorrenteAppMock extends Application {

	public static void alert(String title, String headerMessage, String contentMessage) {
		javafx.scene.control.Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Simulatore conto corrente - MOCK");

		Controller controller = new Controller(fakeCC());
		MainPane mainPanel = new MainPane(controller);
		//
		Scene scene = new Scene(mainPanel, 830, 500, Color.AQUAMARINE);
		stage.setScene(scene);
		stage.show();
	}

	private ContoCorrente fakeCC() {
		ContoCorrente cc = new ContoCorrente(999888777L);
		var m1 = new Movimento(LocalDate.of(2022,2,1),LocalDate.of(2022,1,31), Tipologia.ACCREDITO, 10000, "saldo iniziale");
		cc.aggiungi(m1);
		var m2 = new Movimento(LocalDate.of(2022,2,3),LocalDate.of(2022,2,3), Tipologia.ADDEBITO, -100, "prelievo bancomat");
		cc.aggiungi(m2);
		var m3 = new Movimento(LocalDate.of(2022,2,2),LocalDate.of(2022,2,1), Tipologia.ADDEBITO, -200, "prelievo bancomat");
		cc.aggiungi(m3);
		var m4 = new Movimento(LocalDate.of(2022,2,4),LocalDate.of(2022,2,4), Tipologia.SALDO, 9700, "saldo al 4/2/22");
		cc.aggiungi(m4);
		var m5 = new Movimento(LocalDate.of(2022,2,25),LocalDate.of(2022,2,25), Tipologia.ACCREDITO, 1200.43, "stipendio");
		cc.aggiungi(m5);
		var m6 = new Movimento(LocalDate.of(2022,2,26),LocalDate.of(2022,2,26), Tipologia.ACCREDITO, 99.57, "rimborso fiscale");
		cc.aggiungi(m6);
		var m7 = new Movimento(LocalDate.of(2022,2,27),LocalDate.of(2022,2,26), Tipologia.SALDO, 11000, "saldo al 27/2/22");
		cc.aggiungi(m7);
		return cc;
	}

	public static void main(String[] args) {
		launch(args);
	}
	

}
