package timesheet.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import timesheet.controller.Controller;
import timesheet.model.Formatters;


public class MainPane extends BorderPane {
	
	private Controller controller;
	
	private TextArea outputArea;
	private TextField txtOreTotaliProgetto, txtOreLavorate;
	private DatePicker pickerData;
	private Button applica, dettagli, sintesi;
	private Spinner<String> spinnerProgetti;
	private Label sab, dom;

	public MainPane(Controller controller) {
		this.controller=controller;
		//
		HBox topBox = new HBox();
			topBox.setPadding(new Insets(10, 10, 10, 10));			
			sab = new Label("Lavoro sabato: "   + controller.isSaturdayWorkingDay());
			dom = new Label("Lavoro domenica: " + controller.isSundayWorkingDay());
			topBox.getChildren().addAll(sab, new Label("     "), dom, new Label("     "), new Label("Max ore giornaliere: " + String.valueOf(controller.getMaxHoursPerDay())));			
		this.setTop(topBox);
		
		VBox centerBox = new VBox();
			centerBox.setPadding(new Insets(0, 10, 10, 10));
			HBox row1 = new HBox();
			row1.setPadding(new Insets(10, 10, 10, 0));

			spinnerProgetti = new Spinner<>(FXCollections.observableArrayList(controller.getProjectNames()));
			spinnerProgetti.setTooltip(new Tooltip("Scegliere il progetto"));
			spinnerProgetti.setPrefWidth(180);
			spinnerProgetti.valueProperty().addListener( (obj, oldval, newval) ->  aggiornaMaxOreProgetto(newval));
			
			txtOreTotaliProgetto = new TextField();
			txtOreTotaliProgetto.setEditable(false);
			txtOreTotaliProgetto.setAlignment(Pos.CENTER_RIGHT);
			txtOreTotaliProgetto.setPrefWidth(35);
			txtOreTotaliProgetto.setTooltip(new Tooltip("Ore totali previste nel progetto"));
			txtOreTotaliProgetto.setText(String.valueOf(controller.getMaxHoursPerProject(spinnerProgetti.getValue())));

			row1.getChildren().addAll(new Label("Progetto "), spinnerProgetti, new Label("   Ore previste "), txtOreTotaliProgetto);
			

			HBox row2 = new HBox();
			row2.setPadding(new Insets(0, 10, 10, 0));
			
			pickerData = new DatePicker(LocalDate.now());
			pickerData.setPrefWidth(100);
			pickerData.setTooltip(new Tooltip("Scegliere il giorno da modificare"));
			pickerData.setOnAction(ev -> giorniCorretti());
			
			txtOreLavorate = new TextField();
			txtOreLavorate.setPrefWidth(50);
			txtOreLavorate.setTooltip(new Tooltip("Inserire le ore lavorate nella forma HH:MM"));
			txtOreLavorate.setAlignment(Pos.CENTER);
			txtOreLavorate.setText("00:00");
			
			row2.getChildren().addAll(new Label("Data "), pickerData, new Label("   Ore lavorate "), txtOreLavorate);
			
			HBox row3 = new HBox();
			row3.setPadding(new Insets(0, 10, 10, 0));
			
			applica = new Button("Applica");
			applica.setFont(Font.font("Arial", FontWeight.BOLD, 11));
			applica.setOnAction(ev -> applica());
			
			dettagli = new Button("Dettaglio ore");
			dettagli.setFont(Font.font("Arial", FontWeight.BOLD, 11));
			dettagli.setOnAction(ev -> mostraDettaglioOre());
			
			sintesi = new Button("Sintesi ore");
			sintesi.setFont(Font.font("Arial", FontWeight.BOLD, 11));
			sintesi.setOnAction(ev -> mostraSintesiOre());
			
			row3.getChildren().addAll(new Label("    "), applica, new Label("    "), dettagli, new Label("    "), sintesi);
			
			centerBox.getChildren().addAll(row1, row2, row3);
		this.setCenter(centerBox);	
		
		HBox bottomBox = new HBox();
			bottomBox.setPadding(new Insets(0, 10, 10, 10));
			outputArea = new TextArea();
			outputArea.setPrefSize(700, 270);
			outputArea.setFont(Font.font("Courier New", FontWeight.NORMAL, 14));
			outputArea.setEditable(false);
			bottomBox.getChildren().addAll(outputArea);
		this.setBottom(bottomBox);
	}

	private void applica() {
		LocalTime oreLavorate = null;
		try {
			oreLavorate = LocalTime.parse(txtOreLavorate.getText().trim(), Formatters.timeFormatter);
		} catch(DateTimeParseException e) {
			Controller.alert("Numero di ore malformato", "Il numero fornito di ore svolte è malformato", 
					"Il numero di ore deve essere formattato come hh:mm");
			return;
		}
		
		if(!giorniCorretti()) return;
		
		LocalDate choosen = pickerData.getValue();
		String progetto = spinnerProgetti.getValue();
		
		int minutiLavorati = controller.getWorkedTime(choosen)-controller.getWorkedTimePerProject(choosen, progetto);
		int minutiOra = oreLavorate.getHour()*60+oreLavorate.getMinute();
		if(minutiLavorati+minutiOra > controller.getMaxHoursPerDay()*60) {
			Controller.alert("Massimo ore superato", "Non è possibile inserire ore in questo giorno", 
					"Il numero massimo di ore giornaliere è stato superato");
			return;
		}
		
		controller.setWorkedTimePerProject(choosen, minutiOra, progetto);
		outputArea.setText("Inserite " + Formatters.getTimeAsString(minutiOra) + " per il " + choosen.format(Formatters.dateFormatter));
	}
	
	private void mostraSintesiOre() {
		outputArea.setText(controller.annualSynthesis());
	}

	private void mostraDettaglioOre() {
		outputArea.setText(controller.annualDetail());
	}

	private boolean giorniCorretti() {
		LocalDate choosen = pickerData.getValue();
		
		if(!switch(choosen.getDayOfWeek()) {
		case SATURDAY -> controller.isSaturdayWorkingDay().getValue();
		case SUNDAY -> controller.isSundayWorkingDay().getValue();
		default -> true;
		}) {
			Controller.alert("Giorno non lavorativo", "Non è possibile inserire ore in questo giorno", 
					"Le impostazioni non ammettono il lavoro di " + choosen.getDayOfWeek().toString().toLowerCase());
			return false;
		}
		
		return true;
	}

	private void aggiornaMaxOreProgetto(String projectName) {
		txtOreTotaliProgetto.setText(String.valueOf(controller.getMaxHoursPerProject(projectName)));
	}
	
}
