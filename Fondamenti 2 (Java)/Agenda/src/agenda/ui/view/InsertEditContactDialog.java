package agenda.ui.view;

import agenda.model.Contact;

public interface InsertEditContactDialog {
	void setContact(Contact contact);

	void updateContact(Contact contact);

	DialogResult showDialog();
}
