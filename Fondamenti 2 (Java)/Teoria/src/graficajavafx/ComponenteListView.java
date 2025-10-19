package graficajavafx;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.TextField;

import java.util.StringJoiner;

import javafx.application.Application;
import javafx.collections.FXCollections;

public class ComponenteListView extends Application {
	private ListView<String> listview;
	private TextField txt1, txt2;
	private Button ba, br;
	@Override
	public void start(Stage stage) {
		stage.setTitle("Componente ListView");
		
		FlowPane panel = new FlowPane();
		txt1 = new TextField(); txt1.setPrefColumnCount(25); txt1.setEditable(false);
		txt2 = new TextField(); txt2.setPrefColumnCount(25);
		ba = new Button("Add"); br = new Button("Remove");
		
		// Aggiunta e rimozione dinamiche di elementi
		ba.setOnAction(ev -> listview.getItems().add(txt2.getText()));
		br.setOnAction(ev -> listview.getItems().remove(txt2.getText()));
		
		listview = new ListView<>();
		listview.setItems(FXCollections.observableArrayList("Rosso", "Giallo", "Verde", "Blu"));
		
		// Permette selezione multipla con CTRL/SHIFT
		listview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		listview.setPrefHeight(listview.getItems().size()*24+2);
		listview.setPrefWidth(72);
		
		listview.getSelectionModel().selectedItemProperty().addListener((obj, oldval, newval) -> {
			StringJoiner sj = new StringJoiner(", ");
			for(String item : listview.getSelectionModel().getSelectedItems()) sj.add(item);
			txt1.setText("Selezione: " + sj);
		});
		
		panel.getChildren().addAll(listview, txt1, ba, br, txt2);
		Scene scene = new Scene(panel, Color.WHITE);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
