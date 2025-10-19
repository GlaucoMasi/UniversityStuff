package agenda.ui.view.implFX;

import java.util.Optional;

import agenda.ui.view.DialogResult;
import agenda.ui.view.SelectDetailTypeDialog;
import javafx.scene.control.ChoiceDialog;

public class FxSelectDetailTypeDialog implements SelectDetailTypeDialog {
	private String[] availableTypes;
	private String selectedType;
	
	@Override
	public void setAvailableTypes(String[] types) {
		this.availableTypes = types;
	}

	@Override
	public String getSelectedType() {
		return selectedType;
	}

	@Override
	public DialogResult showDialog() {
		ChoiceDialog<String> dialog = new ChoiceDialog<>(availableTypes[0], availableTypes);
		dialog.setTitle("Dettaglio");
		dialog.setHeaderText("Tipo di dettaglio");
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			selectedType = result.get();
			return DialogResult.Ok;
		}
		return DialogResult.Cancel;
	}

}
