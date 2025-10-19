package autonoleggio.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import autonoleggio.controller.Controller;
import autonoleggio.model.Agency;
import autonoleggio.model.CarType;
import autonoleggio.model.Formatters;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;



public class MainPane extends BorderPane {
	
	private Controller controller;
	
	private TextArea outputArea;
	private TextField txtOrarioIniziale, txtOrarioFinale;
	private DatePicker pickerGiornoIniziale, pickerGiornoFinale;
	private Button cerca;
	private ComboBox<String> comboCities;
	private ComboBox<Agency> comboAgencies;
	private ComboBox<CarType> comboCarTypes;
	private CheckBox checkboxDropoff;

	public MainPane(Controller controller) {
		this.controller=controller;
		//
		HBox topBox = new HBox();
			topBox.setPadding(new Insets(10, 10, 10, 5));
			
			comboCities = new ComboBox<>(FXCollections.observableArrayList(controller.getCities()));
			comboCities.setTooltip(new Tooltip("Scegliere la città"));
			comboCities.setPrefWidth(130);
			comboCities.setOnAction(ev -> popolaComboAgencies());
			
			comboAgencies = new ComboBox<>();
			comboAgencies.setPrefWidth(150);
			comboAgencies.setTooltip(new Tooltip("Scegliere l'agenzia desiderata"));
			comboAgencies.setOnAction(ev -> mostraOrariApertura());
			
			topBox.getChildren().addAll(new Label("Città  "), comboCities, new Label("  Agenzie  "), comboAgencies);			
		this.setTop(topBox);
		
		VBox centerBox = new VBox();
			centerBox.setPadding(new Insets(0, 10, 10, 10));
			HBox row1 = new HBox();
			row1.setPadding(new Insets(10, 10, 10, 0));
			
			pickerGiornoIniziale = new DatePicker(LocalDate.now());
			pickerGiornoIniziale.setPrefWidth(150);
			pickerGiornoIniziale.setTooltip(new Tooltip("Scegliere la data di inizio noleggio"));
			pickerGiornoIniziale.setOnAction(ev -> impostaDataFinale());
			txtOrarioIniziale = new TextField();
			txtOrarioIniziale.setPrefWidth(100);
			txtOrarioIniziale.setTooltip(new Tooltip("Inserire l'orario nella forma HH:MM"));
			txtOrarioIniziale.setText("10:00");

			row1.getChildren().addAll(new Label("Inizio noleggio  "), pickerGiornoIniziale, txtOrarioIniziale);
			
			HBox row2 = new HBox();
			row2.setPadding(new Insets(0, 10, 10, 0));
			
			pickerGiornoFinale = new DatePicker(LocalDate.now());
			pickerGiornoFinale.setPrefWidth(150);
			pickerGiornoFinale.setTooltip(new Tooltip("Scegliere la data di fine noleggio"));
			txtOrarioFinale = new TextField();
			txtOrarioFinale.setPrefWidth(100);
			txtOrarioFinale.setTooltip(new Tooltip("Inserire l'orario nella forma HH:MM"));
			txtOrarioFinale.setText("18:00");
			
			row2.getChildren().addAll(new Label("Fine noleggio    "), pickerGiornoFinale, txtOrarioFinale);
			
			HBox row3 = new HBox();
			row3.setPadding(new Insets(0, 10, 10, 0));
			
			comboCarTypes = new ComboBox<>(FXCollections.observableArrayList(CarType.values()));
			comboCarTypes.setTooltip(new Tooltip("Scegliere il tipo di auto"));
			cerca = new Button("Cerca");
			cerca.setFont(Font.font("Arial", FontWeight.BOLD, 11));
			cerca.setOnAction(ev -> cerca());
			
			checkboxDropoff = new CheckBox("Riconsegna in altra città");
			row3.getChildren().addAll(new Label("Tipo auto           "), comboCarTypes, new Label("    "), cerca, new Label("    "), checkboxDropoff);
			
			centerBox.getChildren().addAll(row1, row2, row3);
		this.setCenter(centerBox);	
		
		HBox bottomBox = new HBox();
			bottomBox.setPadding(new Insets(0, 10, 10, 10));
			outputArea = new TextArea();
			outputArea.setPrefSize(420,100);
			outputArea.setFont(Font.font("Courier New", FontWeight.NORMAL, 14));
			outputArea.setEditable(false);
			bottomBox.getChildren().addAll(outputArea);
		this.setBottom(bottomBox);
	}


	private void impostaDataFinale() {
		pickerGiornoFinale.setValue(pickerGiornoIniziale.getValue());
	}

	private void popolaComboAgencies() {
		var city = comboCities.getValue();
		try {
			comboAgencies.setItems(FXCollections.observableArrayList(controller.getAgencies(city)));
		}
		catch(IllegalArgumentException e) {
			Controller.alert("Errore nella selezione della città", "Città inesistente",
					"Dettagli:\n" + e.getMessage());
		}
	}
	
	private void mostraOrariApertura() {
		var agency = comboAgencies.getValue();	
		if (agency==null) { 
			Controller.alert("Agenzia non selezionata", "Occorre prima selezionare l'agenzia", "Selezionare l'agenzia dalla combo a destra\n");
			return;
		}
		outputArea.setText(
				"Orario di apertura dell'agenzia di " + agency.agencyName() + System.lineSeparator() +
				"- da lunedì a venerdì: " + agency.openingTimeMonFri() + System.lineSeparator() +
				"- sabato: " + agency.openingTimeSat() + System.lineSeparator() +
				"- domenica: " + agency.openingTimeSun() + System.lineSeparator()
				);
	}

	private void cerca() {
		var city = comboCities.getValue();	
		if (city==null) { 
			Controller.alert("Città non selezionata", "Occorre prima selezionare la città", "Selezionare la città dalla combo a sinistra\n");
			return;
		}
		var agency = comboAgencies.getValue();	
		if (agency==null) { 
			Controller.alert("Agenzia non selezionata", "Occorre prima selezionare l'agenzia", "Selezionare l'agenzia dalla combo a destra\n");
			return;
		}
		var startDate = this.pickerGiornoIniziale.getValue();	
		if (startDate==null) { 
			Controller.alert("Data iniziale non selezionata", "Occorre prima selezionare il giorno di inizio noleggio", "Selezionare l'agenzia dal calendario a sinistra\n");
			return;
		}
		var endDate = this.pickerGiornoFinale.getValue();	
		if (endDate==null) { 
			Controller.alert("Data finale non selezionata", "Occorre prima selezionare il giorno di fine noleggio", "Selezionare la data dal calendario a destra\n");
			return;
		}
		LocalTime startTime = LocalTime.of(0, 0); // fake default
		LocalTime endTime = LocalTime.of(23, 59); // fake default
		try {
			startTime = LocalTime.parse(txtOrarioIniziale.getText(), Formatters.timeFormatter);
			endTime = LocalTime.parse(txtOrarioFinale.getText(), Formatters.timeFormatter);
		}
		catch(DateTimeParseException e) {
			Controller.alert("Errore nella lettura dell'orario", "Uno degli orari non segue il formato HH:MM", "Dettagli:\n" + e.getMessage());
			return;
		}
		var carType = comboCarTypes.getValue();	
		if (carType==null) { 
			Controller.alert("Tipo auto non selezionato", "Occorre prima selezionare il tipo di auto desiderata", "Selezionare il tipo di auto dalla combo apposita\n");
			return;
		}
		//--------------- check apertura agenzia -------------
		if (!agency.isOpenAt(LocalDateTime.of(startDate, startTime))) {
			Controller.alert("Agenzia di noleggio chiusa", "L'agenzia di noleggio prescelta è chiusa nel giorno/orario indicato", "Scegliere una diversa agenzia o cambiare giorno / ora");
			return;
		}
		//--------------- business logic -------------
		var result = controller.calcolaTariffa(city, agency, 
				LocalDateTime.of(startDate, startTime), LocalDateTime.of(endDate, endTime), carType, checkboxDropoff.isSelected());
		outputArea.setText("Tariffa calcolata il " + Formatters.dateTimeFormatter.format(LocalDateTime.now()) + System.lineSeparator() 
			+ result.msg() + System.lineSeparator()
			+ "Il costo è " + Formatters.euroFormatter.format(result.importo()));
	}
	
}
