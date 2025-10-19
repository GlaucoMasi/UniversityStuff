package trainstats.ui;

import java.text.NumberFormat;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import trainstats.controller.Controller;
import trainstats.model.StatProvider;
import trainstats.model.Train;


public class MainPane extends BorderPane {
	
	private Controller controller;
	
	private ComboBox<String> trainCombo;
	private ComboBox<StatProvider> providerCombo;
	private TextArea area;
	private NumberFormat formatter;
	private Slider slider;

	public MainPane(Controller controller) {
		this.controller=controller;
		formatter = NumberFormat.getInstance(Locale.ITALY);
		formatter.setMaximumFractionDigits(2);
		formatter.setMinimumFractionDigits(2);
		//
		VBox leftBox = new VBox();
		leftBox.setPrefWidth(200);
			HBox miniBoxIniziale = new HBox(); miniBoxIniziale.setAlignment(Pos.CENTER);
			Label titolino = new Label("Statistiche ritardi treni");
			titolino.setStyle("-fx-font-weight: bold");
			miniBoxIniziale.getChildren().addAll(titolino);
			leftBox.getChildren().addAll(new Label("  "), miniBoxIniziale, new Label("  "));

			trainCombo = new ComboBox<String>();
			providerCombo = new ComboBox<StatProvider>();

			ObservableList<String> trainsList = FXCollections.observableArrayList(controller.getAvailableTrains());
			trainCombo.setItems(trainsList);
			
			trainCombo.setTooltip(new Tooltip("Scegliere il treno su cui operare"));
			trainCombo.setPrefWidth(175);
			leftBox.getChildren().addAll(new Label(" Scelta treno:  "), trainCombo);
			
			trainCombo.setOnAction(_ -> myHandle());

			ObservableList<StatProvider> providersList = FXCollections.observableArrayList(controller.getAvailableProviders());
			providerCombo.setItems(providersList);
			providerCombo.getSelectionModel().selectFirst();
			
			providerCombo.setPrefWidth(175);
			leftBox.getChildren().addAll(new Label(" Criterio di valutazione:  "), providerCombo);
			
			providerCombo.setOnAction(_ -> myHandle());

			slider = new Slider(0, 60, 5);
			
			slider.setShowTickMarks(true);
			slider.setShowTickLabels(true);
			slider.setMajorTickUnit(10);

			slider.valueProperty().addListener(_ -> myHandle());
			
			leftBox.getChildren().addAll(new Label(" Soglia (min.):  "), slider);
			//
			this.setLeft(leftBox);
		VBox rightBox = new VBox();
			rightBox.setPrefWidth(580);
			area = new TextArea();
			area.setPrefSize(580,500);
			area.setFont(Font.font("Courier New", FontWeight.NORMAL, 11));
			area.setEditable(false);
			rightBox.getChildren().addAll(area);
		this.setRight(rightBox);
	}

	private void myHandle() {
		String choosenTrain = trainCombo.getValue();
		if(choosenTrain == null) return;
		
		Train tr = controller.getTrain(choosenTrain);
		
		controller.setCurrentProvider(providerCombo.getValue());
		controller.setThreshold((int) Math.round(slider.getValue()));
		
		area.setText("Dati relativi al treno selezionato:" + System.lineSeparator() + System.lineSeparator() + 
				tr.toString() + System.lineSeparator() + System.lineSeparator() + controller.getStatsMessage(tr));
	}
}
