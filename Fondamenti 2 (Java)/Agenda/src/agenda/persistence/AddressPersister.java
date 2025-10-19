package agenda.persistence;

import agenda.model.Detail;
import agenda.model.Address;

public class AddressPersister implements DetailPersister {
	private static final String SEPARATOR = ";";

	@Override
	public Detail load(String[] tokens) throws BadFileFormatException {
		if(tokens.length != 7) throw new BadFileFormatException();
		Address ans = new Address();
		ans.setDescription(tokens[1]);
		ans.setStreet(tokens[2]);
		ans.setNumber(tokens[3]);
		ans.setZipCode(tokens[4]);
		ans.setCity(tokens[5]);
		ans.setState(tokens[6]);
		return ans;
	}

	@Override
	public void save(Detail d, StringBuilder sb) {
		Address a = (Address) d;
		sb.append(a.getName() + SEPARATOR);
		sb.append(a.getDescription() + SEPARATOR);
		sb.append(a.getStreet() + SEPARATOR);
		sb.append(a.getNumber() + SEPARATOR);
		sb.append(a.getZipCode() + SEPARATOR);
		sb.append(a.getCity() + SEPARATOR);
		sb.append(a.getState() + FileUtils.NEWLINE);
	}

}
