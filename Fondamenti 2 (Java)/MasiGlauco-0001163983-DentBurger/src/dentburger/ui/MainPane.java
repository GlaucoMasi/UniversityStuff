package dentburger.ui;

import dentburger.controller.Controller;
import dentburger.model.Categoria;
import dentburger.model.Prodotto;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class MainPane extends BorderPane {
	
	private Controller controller;
	
	private ComboBox<String> genereCombo;
	private ComboBox<Categoria> catCombo;
	private ComboBox<Prodotto> prodCombo;
	private TextArea outputArea;
	private Button aggiungi, rimuovi;
	

	public MainPane(Controller controller) {
		this.controller=controller;
		//
		VBox leftBox = new VBox();
		leftBox.setPrefWidth(200);
			HBox miniBoxIniziale = new HBox(); miniBoxIniziale.setAlignment(Pos.CENTER);
			Label titolino = new Label("Dent Burger");
			titolino.setStyle("-fx-font-weight: bold");
			miniBoxIniziale.getChildren().addAll(titolino);
			leftBox.getChildren().addAll(new Label("  "), miniBoxIniziale, new Label("  "));
			
			catCombo = new ComboBox<>();
			catCombo.setItems(FXCollections.observableArrayList(this.controller.getCategorie()));
			catCombo.setTooltip(new Tooltip("Scegliere la categoria"));
			catCombo.setPrefWidth(175);
			leftBox.getChildren().addAll(new Label(" Categoria:  "), catCombo);
			catCombo.setOnAction(ev -> popolaComboGeneri());
			
			genereCombo = new ComboBox<>();
			genereCombo.setTooltip(new Tooltip("Scegliere il genere"));
			genereCombo.setPrefWidth(175);
			leftBox.getChildren().addAll(new Label(" Genere:  "), genereCombo);
			genereCombo.setOnAction(ev -> popolaComboProdotti());

			prodCombo = new ComboBox<>();
			prodCombo.setTooltip(new Tooltip("Scegliere il prodotto"));
			prodCombo.setPrefWidth(175);
			leftBox.getChildren().addAll(new Label(" Prodotto:  "), prodCombo);
			//
			aggiungi = new Button("Aggiungi");
			rimuovi = new Button("Rimuovi");
			aggiungi.setOnAction(ev -> { modificaOrdine(Operazione.AGGIUNGI); });
			rimuovi.setOnAction(ev -> { modificaOrdine(Operazione.RIMUOVI); });
			leftBox.getChildren().addAll(new Label(" "), aggiungi,rimuovi);
			//
			this.setLeft(leftBox);
			
		VBox rightBox = new VBox();
			rightBox.setPrefWidth(580);
			outputArea = new TextArea();
			outputArea.setPrefSize(580,500);
			outputArea.setFont(Font.font("Courier New", FontWeight.NORMAL, 11));
			outputArea.setEditable(false);
			rightBox.getChildren().addAll(outputArea);
		this.setRight(rightBox);
	}

	private void popolaComboGeneri() {
		Categoria c = catCombo.getValue();
		if(c == null) return;
		var lista = this.controller.getGeneriPerCategoria(c);
		genereCombo.setItems(FXCollections.observableArrayList(lista));
		if(!lista.isEmpty()) genereCombo.getSelectionModel().select(0);
	}

	private void popolaComboProdotti() {
		Categoria c = catCombo.getValue();
		String g = genereCombo.getValue();
		if(c == null || g == null) return;
		var lista = this.controller.getProdottiPerGenereCategoria(c, g);
		prodCombo.setItems(FXCollections.observableArrayList(lista));
		if(!lista.isEmpty()) prodCombo.getSelectionModel().select(0);
	}
	
	private void modificaOrdine(Operazione op) {
		Prodotto p = prodCombo.getValue();
		if(p == null) {
			Controller.alert("ERRORE!", "Nessun prodotto selezionato", "Selezionare un prodotto per aggiungerlo o per rimuoverlo, se presente");
			return;
		}
		
		if(op == Operazione.AGGIUNGI) this.controller.aggiungiProdotto(p);
		else if(op == Operazione.RIMUOVI) this.controller.rimuoviProdotto(p);
		
		outputArea.setText(this.controller.getOrdine().toString());
	}
	
}
