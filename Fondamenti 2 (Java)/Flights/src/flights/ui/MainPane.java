package flights.ui;

import flights.model.Airport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

import flights.controller.Controller;

public class MainPane extends BorderPane{
	private ComboBox<Airport> departureAirportComboBox, arrivalAirportComboBox;
	private DatePicker departureDatePicker;
	private FlightScheduleListPanel flightScheduleListPanel;
	private Controller controller;
	Button find;
	VBox left;
	
	public MainPane(Controller controller) {
		this.controller = controller;
		this.initPane();

		left.setSpacing(10);
		left.setPadding(new Insets(0, 20, 10, 20));
		
		ObservableList<Airport> observableAirports = FXCollections.observableArrayList(this.controller.getSortedAirports());
		departureAirportComboBox.setItems(observableAirports);
		departureAirportComboBox.setEditable(false);
		departureAirportComboBox.getSelectionModel().select(0);
		arrivalAirportComboBox.setItems(observableAirports);
		arrivalAirportComboBox.setEditable(false);
		arrivalAirportComboBox.getSelectionModel().select(0);
		
		Label departure = new Label("Departure Airport"), arrival = new Label("Arrival Airports"), date = new Label("Departure Date");
		
	    find.setOnAction(ev -> {
	    	Airport start = departureAirportComboBox.getValue();
	    	Airport end = arrivalAirportComboBox.getValue();
	    	LocalDate choosenDate = departureDatePicker.getValue();
	    	
	    	if(start == null || end == null || choosenDate == null) return;
	    	flightScheduleListPanel.setFlightSchedules(this.controller.searchFlights(start, end, choosenDate));
	    });
	    
	    departureDatePicker.setValue(LocalDate.now());

		left.setAlignment(Pos.BASELINE_RIGHT);
		left.getChildren().addAll(departure, departureAirportComboBox, new Label("   "), arrival, arrivalAirportComboBox, new Label("   "), date, departureDatePicker, new Label("   "), find);
		this.setLeft(left);
		
		this.setCenter(flightScheduleListPanel);
	}
	
	private void initPane() {
		left = new VBox();
		departureAirportComboBox = new ComboBox<Airport>();
		arrivalAirportComboBox = new ComboBox<Airport>();
		departureDatePicker = new DatePicker();
	    flightScheduleListPanel = new FlightScheduleListPanel();
	    find = new Button("Find");
	}
}
