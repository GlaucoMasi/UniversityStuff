package gringottbank.ui;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

import gringottbank.controller.Controller;
import gringottbank.model.Amount;
import gringottbank.model.Coin;
import gringottbank.model.Currency;
import gringottbank.model.ImpossibleWithdrawException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class MainPane extends BorderPane {
	
	private Controller controller;
	private ComboBox<String> clientiCombo;
	private ComboBox<Currency> currencyCombo;
	private TextArea area;
	private String idCliente;
	private TextField txtGalleons, txtSickles, txtKnuts, txtMoney;
	private NumberFormat formatter;
	private RadioButton coinButton, currencyButton;
	private ToggleGroup tg;
	private Button preleva;

	public MainPane(Controller controller) {
		this.controller=controller;
		formatter = NumberFormat.getInstance(Locale.ITALY);
		formatter.setMaximumFractionDigits(2);
		formatter.setMinimumFractionDigits(2);

		VBox topVBox = new VBox();
		topVBox.setPrefHeight(200);
			HBox miniBoxTitolo = new HBox(); miniBoxTitolo.setAlignment(Pos.CENTER);
			Label titolo = new Label("Gringott ATM");
			titolo.setStyle("-fx-font-weight: bold");
			miniBoxTitolo.getChildren().addAll(titolo);
			topVBox.getChildren().addAll(new Label("  "), miniBoxTitolo, new Label("  "));

			coinButton =     new RadioButton("Monete Gringott"); 
			coinButton.setStyle("-fx-font-weight: bold");
			currencyButton = new RadioButton("Altre valute         "); 
			currencyButton.setStyle("-fx-font-weight: bold");
			
			tg = new ToggleGroup();
			coinButton.setToggleGroup(tg);
			currencyButton.setToggleGroup(tg);
			tg.selectToggle(coinButton);
			coinButton.setOnAction(this::coinButtonHandle);
			currencyButton.setOnAction(this::currencyButtonHandle);
			
			clientiCombo = new ComboBox<>();
			clientiCombo.setItems(FXCollections.observableArrayList(controller.getClients()));
					
			HBox miniBoxClienti = new HBox(); miniBoxClienti.setAlignment(Pos.CENTER_LEFT);
			Label clienti = new Label("  Cliente:   ");
			clienti.setStyle("-fx-font-weight: bold");
			miniBoxClienti.getChildren().addAll(clienti, clientiCombo);
			topVBox.getChildren().addAll(new Label("  "), miniBoxClienti, new Label("  "));

			HBox miniBoxGringott = new HBox(); miniBoxGringott.setAlignment(Pos.CENTER_LEFT);			
			miniBoxGringott.getChildren().addAll(coinButton, new Label("   "));
			txtGalleons = new TextField("0"); txtGalleons.setFont(Font.font("Courier New", FontWeight.BOLD, 11));
			txtSickles = new TextField("0");  txtSickles.setFont(Font.font("Courier New", FontWeight.BOLD, 11));
			txtKnuts = new TextField("0");    txtKnuts.setFont(Font.font("Courier New", FontWeight.BOLD, 11));
			txtGalleons.setAlignment(Pos.CENTER_RIGHT); txtGalleons.setPrefWidth(50);
			txtSickles.setAlignment(Pos.CENTER_RIGHT);  txtSickles.setPrefWidth(50);
			txtKnuts.setAlignment(Pos.CENTER_RIGHT);    txtKnuts.setPrefWidth(50);
			miniBoxGringott.getChildren().addAll(
					txtGalleons, new Label(" "), new Label(Coin.GALLEON.getName()), new Label(" "), 
					txtSickles, new Label(" "), new Label(Coin.SICKLE.getName()), new Label(" "), 
					txtKnuts, new Label(" "), new Label(Coin.KNUT.getName()), new Label(" ")
					);
			topVBox.getChildren().addAll(miniBoxGringott);

			HBox miniBoxAltreValute = new HBox(); miniBoxAltreValute.setAlignment(Pos.CENTER_LEFT);
			miniBoxAltreValute.getChildren().addAll(currencyButton,new Label("   "));
			txtMoney = new TextField("0"); txtMoney.setFont(Font.font("Courier New", FontWeight.BOLD, 11));
			txtMoney.setAlignment(Pos.CENTER_RIGHT); txtMoney.setPrefWidth(50);
			txtMoney.setEditable(false);
			
			currencyCombo = new ComboBox<>();
			currencyCombo.setItems(FXCollections.observableArrayList(Currency.values()));
			currencyCombo.getSelectionModel().selectFirst();
			
			miniBoxAltreValute.getChildren().addAll(txtMoney, new Label(" "), currencyCombo);
			topVBox.getChildren().addAll(miniBoxAltreValute);

			HBox miniBoxPreleva = new HBox(); miniBoxPreleva.setAlignment(Pos.CENTER);
			preleva = new Button("Preleva");
			preleva.setStyle("-fx-font-weight: bold");
			preleva.setOnAction(this::myHandle);
			miniBoxPreleva.getChildren().addAll(preleva);
			topVBox.getChildren().addAll(new Label(" "), miniBoxPreleva);
			this.setTop(topVBox);
		VBox bottomVBox = new VBox();
			bottomVBox.setPrefHeight(200);
			area = new TextArea();
			area.setPrefSize(580,200);
			area.setFont(Font.font("Courier New", FontWeight.NORMAL, 11));
			area.setEditable(false);
			Label esito = new Label("Esito del prelievo:");
			esito.setStyle("-fx-font-weight: bold");
			bottomVBox.getChildren().addAll(esito, area);
		this.setBottom(bottomVBox);
	}
	
	private void myHandle(ActionEvent ev) {
		//
		// ***** DA IMPLEMENTARE *****
		//
		//
		// - emettere nell’area di testo l’esito del prelievo, specificando innanzitutto se esso sia stato autorizzato 
		//   e, in questo caso, l’importo effettivamente emesso
		//
		
		idCliente = clientiCombo.getValue();
		if(idCliente == null) {
			Controller.alert("Prelievo fallito", "Nessun cliente selezionato", "Selezionare un cliente e riprovare il prelievo");
			return;
		}
		
		Optional<Amount> autorizzato = Optional.empty();
		
		try {
			if(tg.getSelectedToggle().equals(coinButton)) {
				int galleons = Integer.parseInt(txtGalleons.getText());
				int sickles = Integer.parseInt(txtSickles.getText());
				int knuts = Integer.parseInt(txtKnuts.getText());
				autorizzato = Optional.of(controller.withdraw(idCliente, galleons, sickles, knuts));
			}else {
				int value = Integer.parseInt(txtMoney.getText());
				Currency currency = currencyCombo.getValue();
				if(currency == null) {
					Controller.alert("Prelievo fallito", "Nessuna valuta selezionata", "Selezionare una valuta e riprovare il prelievo");	
				}else {
					autorizzato = Optional.of(controller.withdraw(idCliente, value, currencyCombo.getValue()));
				}
			}
		} catch(IllegalArgumentException e) {
			Controller.alert("Prelievo fallito", "Formato numerico invalido", "Inserire numeri interi e positivi per le quantità di monete");
		} catch(ImpossibleWithdrawException e) {
			Controller.alert("Prelievo fallito", "Prelievo non autorizzato", e.getMessage());
		} 
		
		area.setText("Prelievo del cliente " + idCliente + (autorizzato.isPresent() ? "" : " non") + " autorizzato" + System.lineSeparator());
		if(autorizzato.isPresent()) {
			area.appendText("Importo erogato:" + System.lineSeparator() + autorizzato.get().toString());
		}
	}
	
	private void coinButtonHandle(ActionEvent ev) {
		txtMoney.setEditable(false); txtMoney.setText("0");
		txtGalleons.setEditable(true); txtSickles.setEditable(true); txtKnuts.setEditable(true);
	}

	private void currencyButtonHandle(ActionEvent ev) {
		txtGalleons.setEditable(false); txtSickles.setEditable(false); txtKnuts.setEditable(false);
		txtGalleons.setText("0"); txtSickles.setText("0"); txtKnuts.setText("0"); 
		txtMoney.setEditable(true);
	}
	
}
