package agenda.persistence;

import agenda.model.Phone;
import agenda.model.Detail;

public class PhonePersister implements DetailPersister {
	private static final String SEPARATOR = ";";

	@Override
	public Detail load(String[] tokens) throws BadFileFormatException {
		if(tokens.length != 3) throw new BadFileFormatException();
		Phone ans = new Phone();
		ans.setDescription(tokens[1]);
		ans.setValue(tokens[2]);
		return ans;
	}

	@Override
	public void save(Detail d, StringBuilder sb) {
		Phone p = (Phone) d;
		sb.append(p.getName() + SEPARATOR);
		sb.append(p.getDescription() + SEPARATOR);
		sb.append(p.getValue() + FileUtils.NEWLINE);
	}
}
