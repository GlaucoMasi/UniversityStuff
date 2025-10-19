package oroscopo.ui;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import oroscopo.controller.Controller;
import oroscopo.model.Oroscopo;
import oroscopo.model.SegnoZodiacale;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class MainPane extends BorderPane {
	private Controller controller = null;

	private TextArea output;
	private ComboBox<SegnoZodiacale> segniZodiacali;
	private int fortunaMin;
	private DateTimeFormatter monthFormatter;

	public MainPane(Controller controller, int fortunaMin) {
		this.controller = controller;
		this.fortunaMin = fortunaMin;
		initPane();
		monthFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.ITALY);
	}

	private void initPane() {

		output = new TextArea();
		output.setEditable(false);

		VBox panel = new VBox(2);
		{
			panel.setSpacing(10);
			panel.setPadding(new Insets(0, 20, 10, 20));

			// ---------------------

			panel.getChildren().add(new Label("Segno zodiacale: "));
			segniZodiacali = new ComboBox<SegnoZodiacale>(FXCollections.observableArrayList(controller.getSegni()));
			segniZodiacali.setEditable(false);
			// Decidiamo volutamente di non pre-selezionare alcun segno
			segniZodiacali.setOnAction(_ -> {
				Oroscopo o = controller.generaOroscopoCasuale(segniZodiacali.getValue());
				output.setText(o.toString());
			});
			panel.getChildren().add(segniZodiacali);

			// ---------------------
			panel.getChildren().add(new Label("Oroscopo mensile del segno: "));
			panel.getChildren().add(output);

			Button stampa = new Button("Stampa annuale");
			stampa.setAlignment(Pos.BASELINE_RIGHT);
			stampa.setOnAction(this::stampaButtonHandler);
			panel.getChildren().add(stampa);
		}
		this.setCenter(panel);

	}

	private void stampaButtonHandler(ActionEvent e) {
		try (PrintWriter pw = new PrintWriter("OroscopoAnnuale.txt")) {
			int fortuna = 0;
			if (segniZodiacali.getValue() == null) {
				output.setText("nessun segno selezionato");
				return;
			}
			
			SegnoZodiacale segnoSelezionato = segniZodiacali.getValue();
			pw.println(segnoSelezionato);
			
			for (int i = 0; i < segnoSelezionato.toString().length(); i++) pw.print("-");
			pw.println();
			// OPPURE:
			// var array = new char[segnoSelezionato.toString().length()];
			// Arrays.fill(array, '-');
			// pw.println(new String(array));

			Oroscopo[] annuale = controller.generaOroscopoAnnuale(segnoSelezionato, fortunaMin);
			
			for (Month m : Month.values()) {
				pw.println(monthFormatter.format(m).toUpperCase());
				pw.println(annuale[m.ordinal()]);
				fortuna += annuale[m.ordinal()].getFortuna();
			}
			pw.println("GRADO DI FORTUNA:\t" + fortuna / 12);			
			output.setText("Stampa annuale effettuata");

		} catch (FileNotFoundException e1) {
			Controller.alert("Errore di stampa", "Errore nell'apertura del file per l'oroscopo annuale", "Non è stato possibile aprire in scrittura in file 'OroscopoAnnuale.txt'");
		}

	}

}
