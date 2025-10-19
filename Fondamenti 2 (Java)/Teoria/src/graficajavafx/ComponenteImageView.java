package graficajavafx;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;

public class ComponenteImageView extends Application {
	@Override
	public void start(Stage stage) {
		stage.setTitle("Componente ImageView");
		
		// StackPane, HBox, VBox, TilePane, FlowPane, AnchorPane, BorderPane, GridPane
		BorderPane panel = new BorderPane();
		
		//panel.setCenter(new ImageView(new Image("alberi.jpg", 400, 300, true, false)));
		panel.setCenter(new ImageView(new Image("https://www.brianzapiu.it/wp-content/uploads/2020/02/albero_autunno-450x338.jpg", 400, 300, true, false)));
		
		Scene scene = new Scene(panel, Color.WHITE);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
