package oroscopo.controller;

import java.util.Objects;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import oroscopo.model.ArchivioPrevisioni;
import oroscopo.model.Oroscopo;
import oroscopo.model.SegnoZodiacale;


public abstract class Controller {

	public static void alert(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}

	public static final int NUMERO_SEGNI = SegnoZodiacale.values().length;

	private ArchivioPrevisioni archivio;

	public Controller(ArchivioPrevisioni archivio) {
		Objects.requireNonNull(archivio, "L'archivio previsioni non può essere null");
		this.archivio = archivio;
	}

	public abstract SegnoZodiacale[] getSegni();

	public abstract Oroscopo[] generaOroscopoAnnuale(SegnoZodiacale segno, int fortunaMin);

	public abstract Oroscopo generaOroscopoCasuale(SegnoZodiacale segno);

	protected ArchivioPrevisioni getArchivio() {
		return this.archivio;
	}

}