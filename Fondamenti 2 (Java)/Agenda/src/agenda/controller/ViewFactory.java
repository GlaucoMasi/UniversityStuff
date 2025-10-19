package agenda.controller;

import agenda.ui.view.DetailDialog;
import agenda.ui.view.InsertEditContactDialog;
import agenda.ui.view.InsertEditDetailDialog;
import agenda.ui.view.MainView;
import agenda.ui.view.MessageDialog;
import agenda.ui.view.SelectDetailTypeDialog;
import agenda.ui.view.YesNoQuestionDialog;

public interface ViewFactory {

	MainView createMainView();

	SelectDetailTypeDialog createSelectDetailTypeDialog();

	InsertEditDetailDialog createInsertEditDetailDialog(String type);

	InsertEditContactDialog createInsertEditContactDialog();

	DetailDialog createDetailDialog();
	
	MessageDialog createMessageDialog();
	
	YesNoQuestionDialog createYesNoQuestionDialog();

}