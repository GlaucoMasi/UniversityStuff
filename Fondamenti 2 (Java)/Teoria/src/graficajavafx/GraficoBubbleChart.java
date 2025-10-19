package graficajavafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class GraficoBubbleChart extends Application {
	@Override
	public void start(Stage stage) {
		stage.setTitle("Grafico BubbleChart");
		FlowPane panel = new FlowPane();
		
		// Il BubbleChart richiede due NumberAxis
		NumberAxis asseX = new NumberAxis(); asseX.setLabel("x");
		NumberAxis asseY = new NumberAxis(); asseY.setLabel("y");
		
		BubbleChart<Number, Number> chart = new BubbleChart<>(asseX, asseY);
		chart.setTitle("Funzioni varie");
		
		XYChart.Series<Number, Number> parabola = new XYChart.Series<>();
		parabola.setName("Parabola");
		parabola.getData().add( new XYChart.Data<>(0, 0) );
		parabola.getData().add( new XYChart.Data<>(10, 10) );
		parabola.getData().add( new XYChart.Data<>(15, 22) );
		parabola.getData().add( new XYChart.Data<>(20, 40) );
		parabola.getData().add( new XYChart.Data<>(30, 90) );
		
		XYChart.Series<Number, Number> retta45 = new XYChart.Series<>();
		retta45.setName("Bisettrice primo e terzo quadrante");
		retta45.getData().add( new XYChart.Data<>(0, 0) );
		retta45.getData().add( new XYChart.Data<>(5, 5) );
		retta45.getData().add( new XYChart.Data<>(15, 15) );
		retta45.getData().add( new XYChart.Data<>(20, 20) );
		retta45.getData().add( new XYChart.Data<>(30, 30) );
		retta45.getData().add( new XYChart.Data<>(40, 40) );
		retta45.getData().add( new XYChart.Data<>(50, 50) );
		
		chart.getData().add(parabola);
		chart.getData().add(retta45);
		panel.getChildren().add(chart);
		stage.setScene(new Scene(panel));
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
