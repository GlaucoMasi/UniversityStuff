package spesesanitarie.ui;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import spesesanitarie.controller.Controller;
import spesesanitarie.model.Formatters;
import spesesanitarie.model.DocumentoDiSpesa;
import spesesanitarie.model.Tipologia;


public class MainPane extends BorderPane {
	
	private Controller controller;
	private ComboBox<Tipologia> combo;
	private TextArea areaDettagli, areaSommario;
	private Tipologia tipologia;
	
	public MainPane(Controller controller) {
		this.controller=controller;
		//
		VBox leftBox = new VBox();
		leftBox.setPrefWidth(200);
			HBox miniBoxIniziale = new HBox(); miniBoxIniziale.setAlignment(Pos.CENTER);
			Label titolino = new Label("Analisi spese sanitarie");
			titolino.setStyle("-fx-font-weight: bold");
			miniBoxIniziale.getChildren().addAll(titolino);
			leftBox.getChildren().addAll(new Label("  "), miniBoxIniziale, new Label("  "));
			
			combo = new ComboBox<>(FXCollections.observableArrayList(controller.getTipologie()));
			
			combo.setTooltip(new Tooltip("Scegliere la tipologia di spesa"));
			combo.setPrefWidth(175);
			leftBox.getChildren().addAll(new Label(" Tipologie:  "), combo);
			
			combo.setOnAction(ev -> myHandle());
			
			areaSommario = new TextArea();
			areaSommario.setPrefSize(175,70);
			areaSommario.setFont(Font.font("Courier New", FontWeight.NORMAL, 11));
			areaSommario.setEditable(false);
			impostaAreaSommario();
			leftBox.getChildren().addAll(new Label(" "), areaSommario);
			//
			this.setLeft(leftBox);
		VBox rightBox = new VBox();
			rightBox.setPrefWidth(580);
			areaDettagli = new TextArea();
			areaDettagli.setPrefSize(580,500);
			areaDettagli.setFont(Font.font("Courier New", FontWeight.NORMAL, 11));
			areaDettagli.setEditable(false);
			rightBox.getChildren().addAll(areaDettagli);
		this.setRight(rightBox);
	}

	private void myHandle() {
		tipologia = combo.getValue();
		List<DocumentoDiSpesa> documenti = controller.filtraPer(tipologia);
		double totale = controller.totaleSpesePer(tipologia);
		
		areaDettagli.setText(
				"Totale spese per questa tipologia: " + Formatters.itPriceFormatter.format(totale) + "\n\n"
				+ "Dettaglio spese che contengono voci per questa tipologia:\n\n"
				+ documenti.stream().map(DocumentoDiSpesa::toString).collect(Collectors.joining("\n")));
	}
	
	private void impostaAreaSommario() {
		double detr = controller.totaleSpeseDetraibili();
		double nonDetr = controller.totaleSpeseNonDetraibili();
		areaSommario.setText(   "Totale spese:    " + Formatters.itPriceFormatter.format(detr+nonDetr) +"\n");
		areaSommario.appendText("di cui:\n");

		String formattedDetr = Formatters.itPriceFormatter.format(detr);
		String formattedNonDetr = Formatters.itPriceFormatter.format(nonDetr);

		int max = Math.max(formattedDetr.length(), formattedNonDetr.length());
		formattedDetr = padToMax(formattedDetr, max);
		formattedNonDetr = padToMax(formattedNonDetr, max);
		areaSommario.appendText(" detraibili:     " + formattedDetr +"\n");
		areaSommario.appendText(" non detraibili: " + formattedNonDetr);
	}
	
	private String padToMax(String s, int max) {
		return s.length()<max ? " ".repeat(max-s.length()) + s : s;
	}
	
}
