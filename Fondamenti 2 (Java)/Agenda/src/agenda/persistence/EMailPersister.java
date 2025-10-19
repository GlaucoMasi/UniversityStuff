package agenda.persistence;

import agenda.model.EMail;
import agenda.model.Detail;

public class EMailPersister implements DetailPersister {
	private static final String SEPARATOR = ";";

	@Override
	public Detail load(String[] tokens) throws BadFileFormatException {
		if(tokens.length != 3) throw new BadFileFormatException();
		EMail ans = new EMail();
		ans.setDescription(tokens[1]);
		ans.setValue(tokens[2]);
		return ans;
	}

	@Override
	public void save(Detail d, StringBuilder sb) {
		EMail e = (EMail) d;
		sb.append(e.getName() + SEPARATOR);
		sb.append(e.getDescription() + SEPARATOR);
		sb.append(e.getValue() + FileUtils.NEWLINE);
	}
}
