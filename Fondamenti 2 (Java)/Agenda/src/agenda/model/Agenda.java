package agenda.model;

import java.util.Set;
import java.util.Objects;
import java.util.TreeSet;
import java.util.Optional;
import java.util.SortedSet;
import java.util.Collection;
import java.util.stream.Collectors;

public class Agenda {
	private SortedSet<Contact> contactSet;

	public Agenda(Collection<Contact> contacts) {
		Objects.requireNonNull(contacts);
		if(contacts.isEmpty()) throw new IllegalArgumentException();
		this.contactSet = new TreeSet<Contact>(contacts);
	}
	
	public Agenda() {
		contactSet = new TreeSet<Contact>();
	}
	
	public void addContact(Contact c) {
		contactSet.add(c);
	}
	
	public Set<Contact> getContacts() {
		return contactSet;
	}
	
	public Optional<Contact> getContact(int idx) {
		if(idx >= contactSet.size()) return Optional.empty();
		return Optional.of(contactSet.toArray(new Contact[0])[idx]);
	}
	
	public Optional<Contact> getContact(String firstName, String secondName) {
		return contactSet.stream().filter(e -> e.getName().equals(firstName) && e.getSurname().equals(secondName)).findFirst();
	}
	
	public void removeContact(Contact c) {
		contactSet.remove(c);
	}
	
	public SortedSet<Contact> searchContacts(String secondName){
		return contactSet.stream().filter(e -> e.getSurname().equals(secondName)).collect(Collectors.toCollection(TreeSet::new));
	}
}
