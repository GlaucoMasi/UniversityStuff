package agenda.controller;

import agenda.model.Contact;
import agenda.ui.view.DialogResult;
import agenda.ui.view.InsertEditContactDialog;

public class ContactController {

	private ViewFactory viewFactory;

	public ContactController(ViewFactory viewFactory) {
		this.viewFactory = viewFactory;
	}

	public Contact insertContact() {
		Contact contact = new Contact("Please type...", "Please type..."); 
		return editContact(contact) ? contact : null;
	}

	public boolean editContact(Contact contact) {
		InsertEditContactDialog view = viewFactory
				.createInsertEditContactDialog();
		view.setContact(contact);
		if (view.showDialog() == DialogResult.Ok) {
			view.updateContact(contact);
			return true;
		}
		return false;
	}
}
