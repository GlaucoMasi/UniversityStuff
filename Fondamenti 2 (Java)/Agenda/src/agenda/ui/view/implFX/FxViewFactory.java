package agenda.ui.view.implFX;

import agenda.controller.ViewFactory;
import agenda.ui.view.DetailDialog;
import agenda.ui.view.InsertEditContactDialog;
import agenda.ui.view.InsertEditDetailDialog;
import agenda.ui.view.MainView;
import agenda.ui.view.MessageDialog;
import agenda.ui.view.SelectDetailTypeDialog;
import agenda.ui.view.YesNoQuestionDialog;
import javafx.stage.Stage;

public class FxViewFactory implements ViewFactory {

	private Stage stage;

	public FxViewFactory(Stage stage) {
		this.stage = stage;
	}
	
	@Override
	public MainView createMainView() {
		return new FxMainView(stage);
	}

	@Override
	public SelectDetailTypeDialog createSelectDetailTypeDialog() {
		return new FxSelectDetailTypeDialog();
	}

	@Override
	public InsertEditDetailDialog createInsertEditDetailDialog(String type) {
		switch (type) {
		case "Phone":
			return new FxInsertEditPhoneDialog();
		case "Address":
			return new FxInsertEditAddressDialog();
		case "Email":
			return new FxInsertEditEmailDialog();
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public InsertEditContactDialog createInsertEditContactDialog() {
		return new FxInsertEditContactDialog();
	}

	@Override
	public DetailDialog createDetailDialog() {
		return new FxDetailDialog();
	}

	@Override
	public MessageDialog createMessageDialog() {
		return new FxMessageDialog();
	}

	@Override
	public YesNoQuestionDialog createYesNoQuestionDialog() {
		return new FxYesNoQuestionDialog();
	}

}
