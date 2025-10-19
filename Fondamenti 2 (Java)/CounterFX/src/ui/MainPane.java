package ui;

import java.util.function.IntConsumer;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainPane extends BorderPane {
	private int size;
	private Controller controller;
	private MyButton inc, dec, reset;
	private int currentCounter;
	private MyRadioButton[] buttonsCounters;
	private ToggleGroup tg;
	private int[] counters;
	private Label[] labels;
	private TextField[][] txtEqs;
	
	public MainPane(Controller controller) {
		this.controller = controller;
		size = this.controller.getCapacity();
		buttonsCounters = new MyRadioButton[size];
		counters = new int[size];
		
		HBox topBox = new HBox();
		tg = new ToggleGroup();
		topBox.setAlignment(Pos.CENTER);
		for(int i = 0; i < size; i++) {
			buttonsCounters[i] = new MyRadioButton("c"+i, i);
			buttonsCounters[i].setFont(Font.font("Courier New", FontWeight.NORMAL, 14));
			buttonsCounters[i].setToggleGroup(tg);
			counters[i] = controller.newCounter();
			topBox.getChildren().addAll(new Label("   "), buttonsCounters[i]);
			buttonsCounters[i].setOnAction(this::setCurrentCounter);
		}
		currentCounter = 0;
		tg.selectToggle(buttonsCounters[currentCounter]);
		this.setTop(topBox);
		
		VBox centerBox = new VBox();
		centerBox.setAlignment(Pos.CENTER_LEFT);
		labels = new Label[size];
		for(int i = 0; i < size; i++) {
			labels[i] = new Label();
			centerBox.getChildren().add(labels[i]);
		}
		updateValueLabels();
		this.setCenter(centerBox);
		
		HBox bottomBox = new HBox();
		inc = new MyButton("Inc", "lightgreen");
		inc.setOnAction(ev -> myHandle(controller::incCounter));
		dec = new MyButton("Dec", "red");
		dec.setOnAction(ev -> myHandle(controller::decCounter));
		reset = new MyButton("Reset", "lightblue");
		reset.setOnAction(ev -> myHandle(controller::resCounter));
		bottomBox.getChildren().addAll(dec, reset, inc);
		this.setBottom(bottomBox);
		
		VBox rightBox = new VBox();
		txtEqs = new TextField[size][size];
		for(int i = 0; i < size; i++) {
			for(int j = i+1; j < size; j++) {
				txtEqs[i][j] = makeEqField("c" + i + " = c" + j);
				rightBox.getChildren().add(txtEqs[i][j]);
			}
		}
		updateEqFields();
		this.setRight(rightBox);
	}
	
	private void updateValueLabels() {
		for(int i = 0; i < size; i++) {
			updateValueLabel(labels[i], "Valore di c" + i + " = ", controller.getCounterValueAsString(i));
		}
	}

	private void updateValueLabel(Label label, String prefix, String value) {
		label.setPrefWidth(160);
		label.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
		label.setText(prefix + value);
	}
	
	private TextField makeEqField(String text) {
		TextField txtField = new TextField();
		txtField.setStyle("-fx-font-weight: bold");
		txtField.setPrefWidth(70);
		txtField.setEditable(false);
		txtField.setText(text);
		return txtField;
	}
	
	private void updateEqFields() {
		for(int i = 0; i < size; i++) {
			for(int j = i+1; j < size; j++) {
				updateEqField(txtEqs[i][j], i, j);
			}
		}
	}
	
	private void updateEqField(TextField txtField, int i, int j) {
		txtField.setStyle("-fx-background-color: " + (
				controller.getCounterValueAsString(i).equals(controller.getCounterValueAsString(j)) ? "green" : "red"));
	}
	
	private void myHandle(IntConsumer action) {
		action.accept(currentCounter);
		updateValueLabels();
		updateEqFields();
	}
	
	private void setCurrentCounter(ActionEvent ev) {
		if(tg.getSelectedToggle() instanceof MyRadioButton b) {
			currentCounter = b.getValue();
		}
	}
}
