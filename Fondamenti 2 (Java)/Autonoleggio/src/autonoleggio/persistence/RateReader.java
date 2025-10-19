package autonoleggio.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import autonoleggio.model.CarType;
import autonoleggio.model.Rate;

public interface RateReader {
	
	default public SortedMap<CarType,Rate> readAllRates(Reader reader) throws IOException, BadFileFormatException {
		return new TreeMap<CarType,Rate>(Map.of(
				CarType.A, new Rate(CarType.A, 45.00, 87.00, 100.0),
				CarType.B, new Rate(CarType.B, 51.00, 97.00, 120.0),
				CarType.C, new Rate(CarType.C, 58.00, 115.00, 140.0),
				CarType.D, new Rate(CarType.D, 66.00, 121.00, 170.0)
				));
	}
	
}