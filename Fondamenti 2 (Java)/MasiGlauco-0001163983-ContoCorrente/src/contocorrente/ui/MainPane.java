package contocorrente.ui;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import contocorrente.controller.Controller;
import contocorrente.model.Movimento;
import contocorrente.model.Tipologia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainPane extends BorderPane {
	
	private Controller controller;
	private ListView<Movimento> listView;
	private TextField txtSaldoCorrente, txtDataCorrente, txtTotAccrediti, txtTotAddebiti;
	private PieChart chart;
	private LocalDate dataCorrente;
	private DateTimeFormatter dateFormatter;
	private NumberFormat currencyFormatter;
	private ObservableList<PieChart.Data> dati;
	
	public MainPane(Controller controller) {
		this.controller=controller;
		
		currencyFormatter = NumberFormat.getCurrencyInstance(Locale.ITALY);
		dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.ITALY);
		dataCorrente = LocalDate.now();
		
		HBox topBox = new HBox(10);
		topBox.setPrefWidth(400);
		topBox.getChildren().addAll(new Label("Conto corrente n° " + this.controller.ccId()) );
		topBox.getChildren().addAll(new Label("Intestatario: " + this.controller.ccIntestatario()) );
		this.setTop(topBox);		
		// ----- left box -----
		listView = new ListView<>();
		listView.setItems(FXCollections.observableArrayList(this.controller.movimenti()));		
		listView.setPrefWidth(500);
		listView.setPrefHeight(listView.getItems().size()*24+ 2);
		this.setLeft(listView);	
		
		listView.getSelectionModel().selectedItemProperty().addListener((obj, oldVal, newVal) -> myHandler(newVal));
		
		VBox rightBox = new VBox(10);
		rightBox.setPrefWidth(300);
			//---
			txtDataCorrente = new TextField();
			txtDataCorrente.setEditable(false);
			txtDataCorrente.setMaxWidth(70);
			//---
			txtSaldoCorrente = new TextField();
			txtSaldoCorrente.setEditable(false);
			txtSaldoCorrente.setMaxWidth(150);
			txtSaldoCorrente.setAlignment(Pos.CENTER_RIGHT);
			//---
			txtTotAccrediti = new TextField();
			txtTotAccrediti.setEditable(false);
			txtTotAccrediti.setMaxWidth(100);
			txtTotAccrediti.setAlignment(Pos.CENTER_RIGHT);
			//---
			txtTotAddebiti = new TextField();
			txtTotAddebiti.setEditable(false);
			txtTotAddebiti.setMaxWidth(100);
			txtTotAddebiti.setAlignment(Pos.CENTER_RIGHT);
			//---
			HBox miniBoxData = new HBox(10);
			txtDataCorrente.setText(dateFormatter.format(dataCorrente));
			miniBoxData.getChildren().addAll(new Label("Dati al"), txtDataCorrente);
			rightBox.getChildren().addAll(miniBoxData);
			//---		
			HBox miniBoxAccrediti = new HBox(10);
			miniBoxAccrediti.getChildren().addAll(new Label("Totale accrediti"), txtTotAccrediti);
			rightBox.getChildren().addAll(miniBoxAccrediti);
			//---		
			HBox miniBoxAddebiti = new HBox(10);
			miniBoxAddebiti.getChildren().addAll(new Label("Totale addebiti"), txtTotAddebiti);
			rightBox.getChildren().addAll(miniBoxAddebiti);
			//---
			HBox miniBoxSaldo = new HBox(10);
			miniBoxSaldo.getChildren().addAll(new Label("Saldo"), txtSaldoCorrente);
			rightBox.getChildren().addAll(miniBoxSaldo);
			//---		
			double totaleAddebiti  = controller.getTotale(dataCorrente, Tipologia.ADDEBITO);
			double totaleAccrediti = controller.getTotale(dataCorrente, Tipologia.ACCREDITO);
			chart = makePieChart(totaleAccrediti,totaleAddebiti);
			rightBox.getChildren().addAll(chart);
		this.setRight(rightBox);
	}

	private void myHandler(Movimento newMov) {
		LocalDate dataContabile = newMov.getDataContabile();
		txtDataCorrente.setText(dateFormatter.format(dataContabile));
		txtSaldoCorrente.setText(currencyFormatter.format(controller.saldoAl(dataContabile)));
		
		double totaleAddebiti  = controller.getTotale(dataContabile, Tipologia.ADDEBITO);
		double totaleAccrediti = controller.getTotale(dataContabile, Tipologia.ACCREDITO);
		txtTotAddebiti.setText(currencyFormatter.format(-totaleAddebiti));
		txtTotAccrediti.setText(currencyFormatter.format(totaleAccrediti));
		
		dati.get(0).setPieValue(totaleAccrediti);
		dati.get(1).setPieValue(totaleAddebiti);
	}

	private PieChart makePieChart(double totaleAccrediti, double totaleAddebiti) {
		dati = FXCollections.observableArrayList(
				new PieChart.Data("Accrediti", totaleAccrediti),
				new PieChart.Data("Addebiti", -totaleAddebiti));
		PieChart chart = new PieChart(dati);
		chart.setPrefSize(250, 250);
		chart.setLabelsVisible(false);
		return chart;
	}
	
}
