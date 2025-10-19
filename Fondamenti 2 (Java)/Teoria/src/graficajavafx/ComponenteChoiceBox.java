package graficajavafx;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import javafx.application.Application;
import javafx.collections.FXCollections;

public class ComponenteChoiceBox extends Application {
	private ChoiceBox<String> choicebox;
	private TextField txt1, txt2;
	private Button ba, br;
	@Override
	public void start(Stage stage) {
		stage.setTitle("Componente ChoiceBox");
		
		FlowPane panel = new FlowPane();
		txt1 = new TextField(); txt1.setPrefColumnCount(25); txt1.setEditable(false);
		txt2 = new TextField(); txt2.setPrefColumnCount(25);
		ba = new Button("Add"); br = new Button("Remove");
		
		// Aggiunta e rimozione dinamiche di elementi
		ba.setOnAction(ev -> choicebox.getItems().add(txt2.getText()));
		br.setOnAction(ev -> choicebox.getItems().remove(txt2.getText()));
		
		choicebox = new ChoiceBox<>();
		choicebox.setItems(FXCollections.observableArrayList("Rosso", "Giallo", "Verde", "Blu"));
		
		choicebox.setPrefWidth(72);
		
		choicebox.getSelectionModel().selectedItemProperty().addListener((obj, oldval, newval) -> {
			txt1.setText("Selezione: " + newval);
		});
		
		panel.getChildren().addAll(choicebox, txt1, ba, br, txt2);
		Scene scene = new Scene(panel, Color.WHITE);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
