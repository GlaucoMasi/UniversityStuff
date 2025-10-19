package ui;

import controller.Controller;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CounterFXApp extends Application {
	private int choosenSize;
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("CounterFX");
		
		BorderPane startPanel = new BorderPane();
		
		HBox topBox = new HBox();
		Label label = new Label();
		label.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
		label.setText(" Selezionare il numero di Counters ");
		topBox.getChildren().add(label);
		startPanel.setTop(topBox);
		
		HBox centerBox = new HBox(10);
		centerBox.setAlignment(Pos.CENTER);
		
		Spinner<Integer> spinner = new Spinner<Integer>(1, 6, 3);
		
		Button button = new Button("Enter");
		button.setOnAction(ev -> {
			choosenSize = spinner.getValue();
			Controller controller = new Controller(choosenSize);
			MainPane panel = new MainPane(controller);
			stage.setScene(new Scene(panel));
		});
		
		centerBox.getChildren().addAll(spinner, button);
		startPanel.setCenter(centerBox);
		
		stage.setScene(new Scene(startPanel));
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
