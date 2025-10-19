package battleship.ui;

import javafx.scene.control.Alert;

public class FxUtils {
	private FxUtils() {
	}

	public static void alert(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
}
