package cityparking.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import cityparking.controller.BadTimeFormatException;
import cityparking.controller.Controller;
import cityparking.model.BadTimeIntervalException;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CityParkingPane extends BorderPane {

	private TextArea outputArea;
	private Controller controller;
	private Button calcButton;
	private VBox leftPane, centerPane, bottomPane;
	private DatePicker startPicker, endPicker;
	private TextField startTime, endTime;
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
	
	public CityParkingPane(Controller controller) {
		this.controller = controller;
		Insets insets = new Insets(10);
		//
		leftPane = new VBox();
		leftPane.setPrefWidth(150);
		leftPane.getChildren().add(new Label(" Inizio sosta: "));

		startPicker = new DatePicker(LocalDate.now());
		startTime = new TextField(formatter.format(LocalTime.now()));
		
		leftPane.getChildren().addAll(startPicker,startTime);
		this.setLeft(leftPane);
		BorderPane.setMargin(leftPane, insets);
		//
		centerPane = new VBox();
		centerPane.setPrefWidth(150);
		centerPane.getChildren().add(new Label(" Fine sosta: "));

		endPicker = new DatePicker(LocalDate.now());
		endTime = new TextField(formatter.format(LocalTime.now()));
		
		centerPane.getChildren().addAll(endPicker,endTime);
		this.setCenter(centerPane);
		BorderPane.setMargin(centerPane, insets);
		//
		bottomPane = new VBox();
		var buttonBox = new HBox();
		buttonBox.setAlignment(Pos.CENTER);
		calcButton = new Button("Calcola costo");
		
		calcButton.setOnAction(this::calcolaCosto);
		
		buttonBox.getChildren().add(calcButton);
		bottomPane.getChildren().add(buttonBox);
		outputArea = new TextArea();
		outputArea.setEditable(false);
		outputArea.setPrefHeight(100);
		outputArea.setFont(Font.font("Courier New", FontWeight.BOLD, 12));
		outputArea.setText("");
		bottomPane.getChildren().addAll(new Label(" Costo sosta: "), outputArea);
		this.setBottom(bottomPane);
		BorderPane.setMargin(bottomPane, insets);
	}

	private void calcolaCosto(ActionEvent event) {
		outputArea.setText("");
		LocalDate dataInizio = startPicker.getValue(), dataFine = endPicker.getValue();
		String strOraInizio = startTime.getText(), strOraFine = endTime.getText();
		
		try {
			outputArea.setText(controller.calcolaSosta(dataInizio, strOraInizio, dataFine, strOraFine).toString());
		} catch(BadTimeFormatException e) {
			Controller.alert("ERRORE!", "Calcolo del costo della sosta fallito", 
					"Assicurarsi che l'orario di inizio e di fine sosta siano nel formato hh:mm");
		} catch (BadTimeIntervalException e) {
			Controller.alert("ERRORE!", "Calcolo del costo della sosta fallito", 
					"Assicurarsi che l'orario di fine sosta non preceda quello di inizio sosta");
		}
	}


}
