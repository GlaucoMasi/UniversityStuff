package cruciverba.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;

import cruciverba.controller.MyController;


public class CruciverbApp extends Application {

	public void start(Stage stage) {
		stage.setTitle("CrittoCruciverba 2024");
		MainPane root = new MainPane(new MyController(new File("schema.txt")), stage);
		Scene scene = new Scene(root, 750, 500, Color.ALICEBLUE);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
