package ui;

import javafx.scene.control.Button;

public class MyButton extends Button {
	public MyButton(String txt, String color) {
		super(txt);
		this.setStyle("-fx-font-weight: bold");
		this.setPrefWidth(100);
		this.setStyle("-fx-background-color: " + color);
	}
}
