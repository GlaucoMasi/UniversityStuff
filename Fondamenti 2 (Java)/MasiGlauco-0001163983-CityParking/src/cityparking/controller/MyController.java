package cityparking.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;

import cityparking.model.MyCalcolatoreSosta;
import cityparking.model.Tariffa;
import cityparking.model.Ricevuta;
import cityparking.model.BadTimeIntervalException;
import cityparking.model.CalcolatoreSosta;


public class MyController implements Controller {
	private CalcolatoreSosta calc;
	private DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
	
	public MyController(Tariffa tariffa) {
		calc = new MyCalcolatoreSosta(tariffa);
	}

	@Override
	public Ricevuta calcolaSosta(LocalDate dataInizio, String strOraInizio, LocalDate dataFine, String strOraFine) 
			throws BadTimeIntervalException, BadTimeFormatException {
		try {
			return calc.calcola(
					LocalDateTime.of(dataInizio, LocalTime.parse(strOraInizio, formatter)),
					LocalDateTime.of(dataFine, LocalTime.parse(strOraFine, formatter)));
		} catch(DateTimeParseException e) {
			throw new BadTimeFormatException("Stringhe di orario malformate");
		}
	}
	
}
