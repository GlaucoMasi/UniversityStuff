package battleship.ui;

import java.util.Arrays;

import battleship.controller.Controller;
import battleship.model.ShipItem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainPane extends BorderPane {

	private ComboBox<ShipItem> combo;
	private BattleshipGrid grid;
	private Button verifica;
	private Controller controller;

	public MainPane(Controller controller, Stage stage) {
		this.controller = controller;
		HBox topPane = new HBox();
		this.setTop(topPane);
		Insets insets = new Insets(20);
		//
		VBox leftPane = new VBox();
		combo = new ComboBox<>();
		combo.setItems(FXCollections.observableArrayList(Arrays.asList(ShipItem.values())));
		combo.setValue(ShipItem.SEA);
		//
		leftPane.getChildren().add(new Label("Scegli un elemento dalla combo"));
		leftPane.getChildren().add(new Label("e premi il pulsante nella griglia"));
		leftPane.getChildren().add(combo);
		VBox.setMargin(combo, insets);
		leftPane.getChildren().add(new Label("Elementi da inserire:"));
		leftPane.getChildren().add(new Label(this.controller.getShipList()));
		verifica = new Button("VERIFICA");
		verifica.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
		verifica.setOnAction(this::myHandle);
		VBox.setMargin(verifica, insets);
		leftPane.getChildren().add(verifica);
		setLeft(leftPane);
		//
		insets = new Insets(10);
		setMargin(leftPane, insets);
		grid = new BattleshipGrid(this.controller, combo);
		this.setCenter(grid);
		setMargin(grid, insets);
	}

	private void myHandle(ActionEvent e) {
		int wrong = this.controller.verify();
		FxUtils.alert("Esito della verifica", controller.isGameOver() ? "Game over"
				: wrong == 0 ? "Nessuna casella errata" : "Trovate " + wrong + " caselle errate", "");
		if (wrong > 0) {
			grid.updateGridStatus();
		}
	}

}
