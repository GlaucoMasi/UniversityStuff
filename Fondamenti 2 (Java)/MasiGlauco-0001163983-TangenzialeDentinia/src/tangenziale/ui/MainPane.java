package tangenziale.ui;

import java.text.NumberFormat;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tangenziale.controller.Controller;
import tangenziale.model.Percorso;


public class MainPane extends BorderPane {

	private TextArea outputArea;
	private Controller controller;
	private ComboBox<String> comboEntrata, comboUscita;
	private Button calcola;
	private NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.ITALY);
	
	public MainPane(Controller controller) {
		this.controller = controller;
		
		this.formatter = NumberFormat.getCurrencyInstance(Locale.ITALY);

		HBox topPane = new HBox();
			comboEntrata = new ComboBox<>(FXCollections.observableArrayList(controller.elencoCaselli()));
			comboUscita = new ComboBox<>(FXCollections.observableArrayList(controller.elencoCaselli()));
		
		topPane.getChildren().addAll(
				new Label("Entrata "), comboEntrata, new Label("   "),
				new Label("Uscita "), comboUscita 
				);
		this.setTop(topPane);
		
		HBox centerPane = new HBox();
			centerPane.setAlignment(Pos.CENTER);
			calcola = new Button("Calcola percorso");
			calcola.setOnAction(this::calcolaPercorso);
			centerPane.getChildren().add(calcola);
		this.setCenter(centerPane);

		VBox bottomPane = new VBox();
			outputArea = new TextArea();
			outputArea.setEditable(false);
			outputArea.setPrefWidth(450);
			outputArea.setPrefHeight(150);
			outputArea.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
			outputArea.setText("");
			bottomPane.getChildren().addAll(
					new Label("Percorso e costi"),
					outputArea);
		this.setBottom(bottomPane);
	}

	private void calcolaPercorso(ActionEvent event) {
		String entrata = comboEntrata.getValue(), uscita = comboUscita.getValue();
		
		if(entrata == null || uscita == null) {
			Controller.alert("Errore", "Uno o più caselli non definiti", 
					"Occorre scegliere entrambi i caselli di entrata e di uscita");
			return;
		}
		
		Percorso percorso = controller.trovaPercorso(entrata, uscita);
		outputArea.setText(percorso.toString() + "\nCosto: " + formatter.format(percorso.costo()));
	}

}
