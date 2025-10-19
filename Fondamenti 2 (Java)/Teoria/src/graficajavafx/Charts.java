package graficajavafx;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.chart.BarChart;
import javafx.scene.layout.FlowPane;
import javafx.scene.chart.NumberAxis;
import javafx.application.Application;
import javafx.scene.chart.CategoryAxis;

public class Charts extends Application {
	/*
	 * La maggior parte dei grafici, ereditati da XYChart, hanno una CategoryAxis e una NumberAxis
	 * Procedimento per la creazione:
	 * 1) Predisporre gli assi
	 * 2) Creare l'oggetto Chart
	 * 3) Predisporre una o più serie di dati
	 * 4) Popolare le serie con coppie di dati
	 * 5) Aggiunge le serie al grafico
	 */
	@Override
	public void start(Stage stage) {
		stage.setTitle("Grafici");
		FlowPane panel = new FlowPane();
		
		CategoryAxis asseOrizz = new CategoryAxis();
		asseOrizz.setLabel("Tipi di futta");
		
		NumberAxis asseVert = new NumberAxis();
		asseVert.setLabel("Vendite");
		
		// BarChart, StackedBarChart, ScatterChart, AreaChart, LineChart
		BarChart<String, Number> chart = new BarChart<>(asseOrizz, asseVert);
		chart.setTitle("Andamento vendite frutta");
		
		XYChart.Series<String, Number> modena = new XYChart.Series<>();
		modena.setName("Modena");
		modena.getData().add( new XYChart.Data<>("Mele", 30) );
		modena.getData().add( new XYChart.Data<>("Pere", 15) );
		modena.getData().add( new XYChart.Data<>("Arance", 50) );
		
		XYChart.Series<String, Number> imola = new XYChart.Series<>();
		imola.setName("Imola");
		imola.getData().add( new XYChart.Data<>("Mele", 20) );
		imola.getData().add( new XYChart.Data<>("Pere", 5) );
		imola.getData().add( new XYChart.Data<>("Arance", 30) );
		
		XYChart.Series<String, Number> bologna = new XYChart.Series<>();
		bologna.setName("Bologna");
		bologna.getData().add( new XYChart.Data<>("Mele", 60) );
		bologna.getData().add( new XYChart.Data<>("Pere", 25) );
		bologna.getData().add( new XYChart.Data<>("Arance", 20) );
		
		XYChart.Series<String, Number> ferrara = new XYChart.Series<>();
		ferrara.setName("Ferrara");
		ferrara.getData().add( new XYChart.Data<>("Mele", 25) );
		ferrara.getData().add( new XYChart.Data<>("Pere", 60) );
		ferrara.getData().add( new XYChart.Data<>("Arance", 15) );
		
		chart.getData().add(modena);
		chart.getData().add(imola);
		chart.getData().add(bologna);
		chart.getData().add(ferrara);
		
		// Grafo animato
		chart.setAnimated(true);
		Button button = new Button("Aggiorna");
		button.setOnAction(ev -> {
			ferrara.getData().get(0).setYValue(50);
			ferrara.getData().get(1).setYValue(10);
			ferrara.getData().get(2).setYValue(30);
		});
		
		panel.getChildren().addAll(chart, button);
		stage.setScene(new Scene(panel));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
