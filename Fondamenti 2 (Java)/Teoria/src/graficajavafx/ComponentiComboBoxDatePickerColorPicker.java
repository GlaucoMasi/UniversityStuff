package graficajavafx;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ColorPicker;

import java.util.Locale;
import java.time.LocalDate;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.collections.FXCollections;

public class ComponentiComboBoxDatePickerColorPicker extends Application {
	@Override
	public void start(Stage stage) {
		stage.setTitle("Componenti derivati da ComboBoxBase");
		FlowPane panel = new FlowPane();
		
		ComboBox<String> cb = new ComboBox<>();
		cb.setPrefWidth(100);
		cb.setItems(FXCollections.observableArrayList("Rosso", "Giallo", "Verde", "Blu"));		// ANCHE DIRETTAMENTE NEL COSTRUTTORE!!
		
		TextField txt1 = new TextField();
		cb.setOnAction(ev -> txt1.setText("Opzione scelta: " + cb.getValue() + " (" + cb.getSelectionModel().getSelectedIndex() + ")"));
		
		
		
		DatePicker picker1 = new DatePicker(), picker2 = new DatePicker();
		TextField txt2 = new TextField(); txt2.setPrefColumnCount(25);
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.ITALY);
		
		picker1.setOnAction(ev -> {
			LocalDate date = picker1.getValue();
			txt2.setText("Data selezionata: " + formatter.format(date));
			picker2.setValue(picker1.getValue().plusDays(1));
		});
		
		picker2.setOnAction(ev -> {
			if(picker1.getValue() == null) return;
			long p = ChronoUnit.DAYS.between(picker1.getValue(), picker2.getValue());
			picker2.setTooltip(new Tooltip("Durata soggiorno: " + p + " notti"));
		});
		
		
		
		TextField txt3 = new TextField(), txt4 = new TextField(), txt5 = new TextField();
		txt3.setEditable(false); txt4.setEditable(false); txt5.setEditable(false);
		txt3.setPrefColumnCount(25); txt4.setPrefColumnCount(25); txt5.setPrefColumnCount(25);
		ColorPicker picker3 = new ColorPicker();
		
		picker3.setOnAction(ev -> {
			txt3.setText("Rosso: " + picker3.getValue().getRed());
			txt4.setText("Verde: " + picker3.getValue().getGreen());
			txt5.setText("Blu: " + picker3.getValue().getBlue());
		});
		
		
		
		panel.getChildren().addAll(cb, txt1, picker1, picker2, txt2, picker3, txt3, txt4, txt5);
		Scene scene = new Scene(panel, Color.WHITE);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
