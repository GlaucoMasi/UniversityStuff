package ui;

import javafx.scene.control.RadioButton;

public class MyRadioButton extends RadioButton {
	private int value;
	
	public MyRadioButton(String txt, int value) {
		super(txt);
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
