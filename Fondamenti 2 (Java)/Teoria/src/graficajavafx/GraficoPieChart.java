package graficajavafx;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GraficoPieChart extends Application {
	@Override
	public void start(Stage stage) {
		stage.setTitle("Grafico PieChart");
		FlowPane panel = new FlowPane();
		
		ObservableList<PieChart.Data> dati = FXCollections.observableArrayList(
				new PieChart.Data("Mele", 30),
				new PieChart.Data("Pere", 15),
				new PieChart.Data("Arance", 50));
		
		PieChart chart = new PieChart(dati);
		chart.setTitle("Andamento vendite frutta");
		
		// Grafo animato
		chart.setAnimated(true);
		Button button = new Button("Aggiorna");
		button.setOnAction(ev -> {
			dati.get(0).setPieValue(20);
			dati.get(1).setPieValue(150);
			dati.get(2).setPieValue(40);
		});
		
		panel.getChildren().addAll(chart, button);
		stage.setScene(new Scene(panel));
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
