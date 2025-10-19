package agenda.persistence;

import java.util.Map;
import agenda.model.Detail;

public interface DetailPersister {
	Detail load(String[] tokens) throws BadFileFormatException;
	void save(Detail d, StringBuilder sb);
	
	public static DetailPersister of(String name) {		
		Map<String, DetailPersister> persisterMap = Map.of(
				"EMail", new EMailPersister(),
				"Phone", new PhonePersister(),
				"Address", new AddressPersister()
				);
		return persisterMap.get(name);
	}
}
