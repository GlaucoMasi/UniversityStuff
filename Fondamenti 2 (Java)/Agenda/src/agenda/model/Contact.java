package agenda.model;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

public class Contact implements Comparable<Contact> {
	private String name, surname;
	private List<Detail> detailList;
	
	public Contact(String name, String surname, List<Detail> detailList) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(surname);
		if(name.isBlank()) throw new IllegalArgumentException();
		if(surname.isBlank()) throw new IllegalArgumentException();
		Objects.requireNonNull(detailList);
		this.name = name.trim();
		this.surname = surname.trim();
		this.detailList = detailList;
	}
	
	public Contact(String name, String surname) {
		this(name, surname, new ArrayList<Detail>());
	}

	@Override
	public int compareTo(Contact that) {
		if(this.getSurname().equalsIgnoreCase(that.getSurname())) return this.getName().compareToIgnoreCase(that.getName());
		return this.getSurname().compareToIgnoreCase(that.getSurname());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<Detail> getDetailList() {
		return detailList;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getName() + " " + getSurname());
		for(Detail detail : detailList) sb.append("\n" + detail.getName() + " :: " + detail.getDescription() + "\n" + detail.getValues());
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(detailList, name, surname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		return Objects.equals(detailList, other.detailList) && Objects.equals(name, other.name)
				&& Objects.equals(surname, other.surname);
	}
}
