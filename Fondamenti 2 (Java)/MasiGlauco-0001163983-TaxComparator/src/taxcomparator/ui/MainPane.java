package taxcomparator.ui;

import java.text.DecimalFormat;
import java.text.ParseException;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import taxcomparator.controller.Controller;
import taxcomparator.model.Fasce;


public class MainPane extends BorderPane {
	
	// DA COMPLETARE

	private CurrencyTextField reddito, imposta;
	private TextArea dettaglio;
	private Button calcola;
	private ComboBox<Fasce> combo;
	private Controller controller;
	
	public MainPane(Controller controller) {
		this.controller=controller;
		//
		VBox topBox = new VBox();
		reddito = new CurrencyTextField(); reddito.setPrefColumnCount(30);
		imposta = new CurrencyTextField(); imposta.setPrefColumnCount(30); imposta.setEditable(false);
		calcola = new Button("Calcola");
		combo = new ComboBox<>(FXCollections.observableArrayList(controller.getListaFasceDisponibili()));
		combo.getSelectionModel().selectFirst();
		dettaglio = new TextArea(); dettaglio.setEditable(false); dettaglio.setPrefSize(50, 200);
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().addAll(combo,calcola);
		topBox.getChildren().addAll(
				new Label("Reddito imponibile "), reddito,
				new Label(" "),
				hbox,
				new Label("Imposta dovuta     "), imposta,
				new Label("Dettaglio          "), dettaglio
			);
		this.setTop(topBox);
		calcola.setOnAction(this::calcolaImposta); 
	}

	protected void calcolaImposta(ActionEvent event) {
		controller.setFasce(combo.getValue());
		
		try {
			DecimalFormat formatter = new DecimalFormat("¤ #,##0.##");
			imposta.setText(formatter.format(controller.calcolaImposte(formatter.parse(reddito.getText()).doubleValue())));
			dettaglio.setText(controller.getLog());
		} catch(ParseException e) {
			Controller.alert("ERRORE!", "Formato dell'importo errato", "Seguire la convenzione italiana");
		}
	}

}
