package graficajavafx;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.TextField;

import java.io.File;

import javafx.application.Application;

public class ComponenteFileChooser extends Application {
	// Dichiarate all'esterno, sono finals o effectively-finals così posso modificarle dentro la lambda
	private FileChooser chooser;
	private File selectedFile;

	@Override
	public void start(Stage stage) {
		stage.setTitle("Componente FileChooser");
		
		FlowPane panel = new FlowPane();
		TextField txt1 = new TextField(), txt2 = new TextField();
		txt1.setPrefColumnCount(35); txt1.setEditable(false);
		txt2.setPrefColumnCount(50); txt2.setEditable(false);
		
		Button button = new Button("Scegli un file");
		button.setOnAction(ev -> {
			chooser = new FileChooser();
			chooser.setTitle("Apri file");
			
			chooser.getExtensionFilters().addAll(
					new ExtensionFilter("Text Files", "*.txt"),
					new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
					new ExtensionFilter("Audio Files", "*.wav", "*mp3", "*aac"),
					new ExtensionFilter("All Files", "*.*"));
			
			selectedFile = chooser.showOpenDialog(stage); // Oppure showSaveDialog per salvataggio
			// selectedFile = chooser.showOpenMultipleDialog(stage); Per selezione multipla
			
			// Se l'utente spinge annulla, restituisce null
			if(selectedFile == null) return;
			
			txt1.setText("File name: " + selectedFile.getName());
			txt2.setText("File path: " + selectedFile.getPath());
		});
		
		panel.getChildren().addAll(button, txt1, txt2);
		Scene scene = new Scene(panel, Color.WHITE);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
