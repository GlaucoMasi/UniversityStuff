package flights.persistence;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;

import flights.model.Airport;
import flights.model.City;

public class MyCitiesReader implements CitiesReader {

	@Override
	public Collection<City> read(Reader reader) throws IOException, BadFileFormatException {
		BufferedReader bufferedReader = new BufferedReader(reader);
		ArrayList<City> cityList = new ArrayList<City>();
		City city;
		while ((city = readCity(bufferedReader)) != null) {
			cityList.add(city);
		}
		return cityList;
	}

	private City readCity(BufferedReader reader) throws IOException, BadFileFormatException {
		if (reader == null)
			throw new NullPointerException("reader nullo");
		String line = reader.readLine();
		if (line == null || line.trim().isEmpty())
			return null;

		String[] tokens = line.split(",");
		if (tokens.length < 3)
			throw new BadFileFormatException("Numero di token diverso dalle attese (>= 3)");

		String code = tokens[0];
		int timeZone;
		try {
			String toParse = tokens[1].substring(3); // "GMT".length()
														// == 3
			if (toParse.isEmpty()) {
				timeZone = 0;
			} else {
				if (toParse.startsWith("+")) {
					toParse = toParse.substring(1); // Se positivo, toglie il
													// segno, se negativo lo
													// lascia
				}
				timeZone = Integer.parseInt(toParse);
			}
		} catch (NumberFormatException e) {
			throw new BadFileFormatException(e);
		}
		String name = tokens[2];

		City city = new City(code, name, timeZone);

		if (tokens.length > 3) {
			for (int i = 3; i < tokens.length; i++) {
				String[] codeAndName = tokens[i].split("/");
				if (codeAndName.length != 2) {
					throw new BadFileFormatException("Formato aereoporto diverso da quello atteso");
				}
				Airport airport = new Airport(codeAndName[0], codeAndName[1], city);
				city.getAirports().add(airport);
			}
		} else {
			Airport airport = new Airport(city.getCode(), city.getName(), city);
			city.getAirports().add(airport);
		}
		return city;
	}

}
