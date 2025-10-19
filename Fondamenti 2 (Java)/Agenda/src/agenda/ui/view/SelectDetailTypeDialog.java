package agenda.ui.view;

public interface SelectDetailTypeDialog {
	void setAvailableTypes(String[] types);

	String getSelectedType();

	DialogResult showDialog();
}
