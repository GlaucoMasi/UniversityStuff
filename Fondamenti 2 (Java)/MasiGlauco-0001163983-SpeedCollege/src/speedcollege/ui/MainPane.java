package speedcollege.ui;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import speedcollege.controller.Controller;


public class MainPane extends BorderPane {
	
	private Controller controller;
	private ComboBox<String> carriereCombo, funzioniCombo;
	private TextArea area;
	private String idCarriera, idFunzione;
	private TextField txtMediaPesata, txtCreditiAcquisiti;
	private NumberFormat formatter;
	private DatePicker picker;

	public MainPane(Controller controller) {
		this.controller=controller;
		
		formatter = NumberFormat.getNumberInstance(Locale.ITALY);
		
		VBox leftBox = new VBox();
		leftBox.setPrefWidth(200);
			HBox miniBoxIniziale = new HBox(); miniBoxIniziale.setAlignment(Pos.CENTER);
			Label titolino = new Label("Carriere studenti");
			titolino.setStyle("-fx-font-weight: bold");
			miniBoxIniziale.getChildren().addAll(titolino);
			leftBox.getChildren().addAll(new Label("  "), miniBoxIniziale, new Label("  "));
			
			carriereCombo = new ComboBox<>(FXCollections.observableArrayList(controller.getListaIdCarriere()));
			carriereCombo.setOnAction(this::myHandle);
			leftBox.getChildren().addAll(new Label("Scelta carriera:"), carriereCombo);
			
			funzioniCombo = new ComboBox<>(FXCollections.observableArrayList(controller.elencoFunzioni()));
			funzioniCombo.getSelectionModel().selectFirst();
			funzioniCombo.setOnAction(this::myHandle);
			leftBox.getChildren().addAll(new Label("Funzione di decadimento:"), funzioniCombo);
			
			picker = new DatePicker(LocalDate.of(2020, 1, 1));
			picker.setOnAction(this::myHandle);
			leftBox.getChildren().addAll(new Label("Attualizzare al:"), picker);
			

			txtMediaPesata = new TextField();
			txtCreditiAcquisiti = new TextField();
			txtMediaPesata.setFont(Font.font("Courier New", FontWeight.BOLD, 11));
			txtCreditiAcquisiti.setFont(Font.font("Courier New", FontWeight.BOLD, 11));
			txtMediaPesata.setAlignment(Pos.CENTER_RIGHT);
			txtMediaPesata.setMaxWidth(175);
			txtCreditiAcquisiti.setAlignment(Pos.CENTER_RIGHT);
			txtCreditiAcquisiti.setMaxWidth(175);
			txtMediaPesata.setEditable(false);
			txtCreditiAcquisiti.setEditable(false);
			leftBox.getChildren().addAll(new Label(" Media pesata attualizzata:  "), txtMediaPesata);
			leftBox.getChildren().addAll(new Label(" Crediti acquisiti:  "), txtCreditiAcquisiti);
			this.setLeft(leftBox);
		VBox rightBox = new VBox();
			rightBox.setPrefWidth(580);
			area = new TextArea();
			area.setPrefSize(580,500);
			area.setFont(Font.font("Courier New", FontWeight.NORMAL, 11));
			area.setEditable(false);
			rightBox.getChildren().addAll(area);
		this.setRight(rightBox);
	}
	
	private void myHandle(ActionEvent ev) {
		LocalDate date = picker.getValue();
		idCarriera = carriereCombo.getValue();
		idFunzione = funzioniCombo.getValue();
		if(date == null || idCarriera == null || idFunzione == null) return;
		
		controller.setCarriera(idCarriera);
		controller.setDataDiAttualizzazione(date);
		controller.setFunction(controller.getFunction(idFunzione));

		area.setText(controller.toString());
		txtMediaPesata.setText(formatter.format(controller.getMediaPesata())+"/30");
		txtCreditiAcquisiti.setText(formatter.format(controller.getCreditiAcquisiti(idCarriera))+"/180");
	}
	
}
