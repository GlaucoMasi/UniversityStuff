package cruciverba.ui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map.Entry;

import cruciverba.controller.Controller;
import cruciverba.controller.Modalita;
import cruciverba.persistence.BadFileFormatException;


public class MainPane extends BorderPane {

	private TextArea areaSchemaGiocatore, areaSchemaNumerato, areaMessaggi, areaAssociazioni;
	private Controller controller;
	private Button buttonCarica, buttonParolaIniziale, buttonApplica, buttonRimuovi;
	private RadioButton agevolata, esperta;
	private Modalita modalitaCorrente;
	private ComboBox<Character> comboCaratteri;
	private ComboBox<Integer> comboNumeri;
	private ComboBox<Entry<Integer,Character>> comboAssociazioni;
	private ToggleGroup tg;
	
	public MainPane(Controller controller, Stage stage) {
		this.controller=controller;
		
		this.modalitaCorrente = Modalita.AGEVOLATA;
		
		VBox rightBox = new VBox(5);		
			areaSchemaNumerato = new TextArea();
			areaSchemaNumerato.setPrefWidth(500);
			areaSchemaNumerato.setPrefHeight(300);
			areaSchemaNumerato.setFont(Font.font("Courier New", FontWeight.NORMAL, 12));
			areaSchemaNumerato.setEditable(false);
			rightBox.getChildren().addAll(new Label("Situazione:"), areaSchemaNumerato);
			areaSchemaGiocatore = new TextArea();
			areaSchemaGiocatore.setPrefWidth(500);
			areaSchemaGiocatore.setPrefHeight(300);
			areaSchemaGiocatore.setFont(Font.font("Courier New", FontWeight.NORMAL, 12));
			areaSchemaGiocatore.setEditable(false);
			rightBox.getChildren().addAll(new Label("Situazione gioco:"), areaSchemaGiocatore);
		this.setRight(rightBox);
		
		VBox leftBox = new VBox(5);
			buttonCarica = new Button("Carica crittocruciverba");
			buttonCarica.setPrefWidth(200);
			buttonCarica.setOnAction(_ -> caricaSchema(stage));
			leftBox.getChildren().add(buttonCarica);
			
			buttonParolaIniziale = new Button("Imposta parola iniziale");
			buttonParolaIniziale.setDisable(true);
			buttonParolaIniziale.setPrefWidth(200);
			buttonParolaIniziale.setOnAction(this::parolaIniziale);
			leftBox.getChildren().add(buttonParolaIniziale);
			
			agevolata = new RadioButton("Modalità agevolata");
			esperta = new RadioButton("Modalità esperta");
			
			tg = new ToggleGroup();
			agevolata.setToggleGroup(tg);
			esperta.setToggleGroup(tg);
			tg.selectToggle(agevolata);
			
			agevolata.setOnAction(_ -> settaModalita(Modalita.AGEVOLATA));
			esperta.setOnAction(_ -> settaModalita(Modalita.ESPERTA));
			leftBox.getChildren().addAll(agevolata, esperta);
						
			areaMessaggi = new TextArea();
			areaMessaggi.setPrefWidth(200);
			areaMessaggi.setPrefHeight(50);
			areaMessaggi.setFont(Font.font("Courier New", FontWeight.NORMAL, 12));
			areaMessaggi.setEditable(false);
			leftBox.getChildren().add(areaMessaggi);
			
			comboCaratteri = new ComboBox<>();
			comboNumeri = new ComboBox<>();
			buttonApplica = new Button("Applica");
			buttonApplica.setPrefWidth(80);
			buttonApplica.setOnAction(_ -> applicaAssociazione());
				var combos = new HBox(5);
				combos.getChildren().addAll(comboNumeri, comboCaratteri,buttonApplica);
			leftBox.getChildren().addAll(new Label("Associa numeri a lettere:"), combos);
			
			areaAssociazioni = new TextArea();
			areaAssociazioni.setPrefWidth(200);
			areaAssociazioni.setPrefHeight(200);
			areaAssociazioni.setFont(Font.font("Courier New", FontWeight.NORMAL, 12));
			areaAssociazioni.setEditable(false);
			leftBox.getChildren().addAll(new Label("Associazioni attuali:"), areaAssociazioni);
			
			comboAssociazioni = new ComboBox<>();
			comboAssociazioni.setPrefWidth(100);
			buttonRimuovi = new Button("Rimuovi");
			buttonRimuovi.setPrefWidth(80);
			buttonRimuovi.setOnAction(_ -> rimuoviAssociazione());
				var rimbox = new HBox(5);
				rimbox.getChildren().addAll(comboAssociazioni, buttonRimuovi);
			leftBox.getChildren().addAll(new Label("Rimuovi associazione:"), rimbox);
			
		this.setLeft(leftBox);
	}
	
	private void aggiornaCampi() {
		comboCaratteri.setItems(FXCollections.observableArrayList(controller.lettereDisponibili()));
		comboNumeri.setItems(FXCollections.observableArrayList(controller.numeriDisponibili()));
		comboAssociazioni.setItems(FXCollections.observableArrayList(controller.associazioni()));
		
		areaSchemaNumerato.setText(controller.getSchemaNumerato().toString());
		areaAssociazioni.setText(controller.associazioniAsString());
	}
	
	
	private void applicaAssociazione() {
		// **** DA REALIZZARE ****
		// recupera gli item selezionati nelle due combo (se uno o entrambi non sono selezionati,  
		// viene emesso opportuno msg d’errore e non si fa altro) e li usa per impostare un’associazione
		// sul controller. Poi, ripopola le due combo col nuovo elenco aggiornato (che non comprende più 
		// i valori ora usati) ottenibile dai metodi del controller, aggiorna la visualizzazione dello 
		// schema numerato e infine aggiorna l’elenco delle associazioni correnti dell’apposita combo in basso; 
		// tale elenco viene anche stampato nell’area messaggi.
		
		Character c = comboCaratteri.getValue();
		Integer v = comboNumeri.getValue();
		if(c == null || v == null) Controller.alert("Assocazione fallita", "Associazione non selezionata correttamente", 
				"Selezionare un carattere e un numero e riprovare");
		else {
			try{
				controller.associa(v, c);
			} catch(UnsupportedOperationException e) {
				Controller.alert("Assocazione fallita", "L'associazione proposta è sbagliata", 
						"Selezionare la modalità esperta per non avere più aiuti");
				return;
			}
			
			aggiornaCampi();
		}
	}
	

	private void rimuoviAssociazione() {
		// **** DA REALIZZARE ****
		// agisce  simmetricamente al metodo precedente, recuperando dalla combo associazioni l’elemento selezionato 
		// (se esiste; altrimenti si ha errore, da mostrare tramite opportuna alert) usandolo per disassociare quei 
		// due  elementi. Di conseguenza, deve essere aggiornato il contenuto delle due combo numeri e lettere, che 
		// devono ora includere nuovamente gli elementi dell’associazione cancellata, e della combo associazioni stessa, 
		// che avrà un elemento in meno.
		
		Entry<Integer, Character> selection = comboAssociazioni.getValue();
		if(selection == null) Controller.alert("Rimozione fallita", "Associazione non selezionata correttamente", 
				"Selezionare un carattere e un numero e riprovare");
		else {
			controller.disassocia(selection.getKey());
			
			aggiornaCampi();
		}
	}

	
	void parolaIniziale(ActionEvent e) {
			controller.setModalita(modalitaCorrente);
			areaMessaggi.setText("Modalità " + modalitaCorrente);
			controller.impostaCaratteriIniziali();
			areaSchemaGiocatore.setText(controller.schemaNumeratoAsString());
			buttonParolaIniziale.setDisable(true);
			agevolata.setDisable(true);
			esperta.setDisable(true);
			aggiornaCampi();
	}
	
	void settaModalita(Modalita m) {
		this.modalitaCorrente = m;
		controller.setModalita(m);
	}
	
	void caricaSchema(Stage stage) {
		try {
			controller.leggiSchema();
			areaMessaggi.setText("Cruciverba caricato");
			areaSchemaNumerato.setText(controller.schemaNumeratoAsString());
			buttonCarica.setDisable(true);
			buttonParolaIniziale.setDisable(false);
		}
		catch(IOException e1) {
			Controller.alert("ERRORE", "Impossibile aprire il file", "Errore di I/O");
			return;
		}
		catch(BadFileFormatException e2) {
			Controller.alert("ERRORE", "Il file non contiene un cruciverba correttamente formattato", e2.getMessage());
			return;
		}
	}

	
}
