package contocorrente.controller;

import contocorrente.model.Movimento;
import contocorrente.model.ContoCorrente;
import contocorrente.model.Analizzatore;
import contocorrente.model.MyAnalizzatore;
import contocorrente.model.Tipologia;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.time.LocalDate;
import java.util.List;


public class Controller {

	private Analizzatore checker;
	
	public static void alert(String title, String headerMessage, String contentMessage) {
		javafx.scene.control.Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
	//--------------
	
	private ContoCorrente cc;

	public Controller(ContoCorrente cc) {
		this.cc=cc;
		checker = new MyAnalizzatore(cc);
	}

	public List<Movimento> movimenti() {
		return cc.getMovimenti();
	}

	public long ccId() {
		return cc.getId();
	}

	public String ccIntestatario() {
		return cc.getIntestatario();
	}

	public double saldoAl(LocalDate dataContabile) {
		return checker.getSaldo(dataContabile);
	}
	
	public LocalDate dataPrimoMovimento() {
		return movimenti().get(0).getDataContabile();
	}
	
	public LocalDate dataUltimoMovimento() {
		return movimenti().get(movimenti().size()-1).getDataContabile();
	}
	
	public double getTotale(LocalDate dataContabile, Tipologia t) {
		return checker.getSommaPerTipologia(dataContabile, t);
	}
}
