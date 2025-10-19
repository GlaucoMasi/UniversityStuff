package cruciverba.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import cruciverba.controller.MockController;



public class CruciverbAppMock extends Application {

	public void start(Stage stage) {
		stage.setTitle("CrittoCruciverba 2024 -- MOCK");
		MainPane root = new MainPane(new MockController(), stage);
		Scene scene = new Scene(root, 750, 500, Color.ALICEBLUE);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
