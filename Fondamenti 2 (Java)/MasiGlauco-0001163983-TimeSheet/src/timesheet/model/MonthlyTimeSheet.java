package timesheet.model;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class MonthlyTimeSheet {

	private Month month;
	private Year year;
	private SortedMap<LocalDate, DailyTimeSheet> mappaTimeSheetGiornalieri;
	private LocalDate startDate, endDate;
	
	public MonthlyTimeSheet(Month month, Year year) {
		Objects.requireNonNull(month, "Il mese non può essere nullo");
		Objects.requireNonNull(year, "L'anno non può essere nullo");
		this.month=month;
		this.year=year;
		//
		startDate = LocalDate.of(year.getValue(), month.getValue(), 1);
		endDate = startDate.plusMonths(1).minusDays(1);
		mappaTimeSheetGiornalieri = new TreeMap<>();
		for(LocalDate d=startDate; !endDate.isBefore(d); d = d.plusDays(1)) {
			mappaTimeSheetGiornalieri.put(d, new DailyTimeSheet());
		}
	}

	public Month getMonth() {
		return month;
	}

	public Year getYear() {
		return year;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public int getTotalWorkedTime() {
		return mappaTimeSheetGiornalieri.values().stream().mapToInt(DailyTimeSheet::getTotalWorkedTime).sum();
	}
	
	public String getTotalWorkedTimeAsString() {
		return Formatters.getTimeAsString(getTotalWorkedTime());
	}
	
	public int getTotalWorkedTimePerProject(String project) {
		return mappaTimeSheetGiornalieri.values().stream().mapToInt(workedTimePerProject -> workedTimePerProject.getWorkedTime(project)).sum();
	}
	
	public String getTotalWorkedTimePerProjectAsString(String project) {
		return Formatters.getTimeAsString(getTotalWorkedTimePerProject(project));
	}
	
	public Set<String> activeProjects() {
		return mappaTimeSheetGiornalieri.values().stream()
										.map(DailyTimeSheet::activeProjects)
										.flatMap(Set::stream)
										.collect(Collectors.toSet());
	}
	
	public void setWorkedTimePerProject(LocalDate date, int minutes, String projectName) {
		Objects.requireNonNull(projectName, "Il nome del progetto non può essere nullo");
		Objects.requireNonNull(date, "La data del giorno non può essere nullo");
		if(date.isBefore(this.getStartDate()) || date.isAfter(this.getEndDate())) throw new IllegalArgumentException("La data non appartiene al mese e all'anno del TimeSheet");
		if (projectName.isBlank()) throw new IllegalArgumentException("Il nome del progetto non può essere vuoto");
		if (minutes<0) throw new IllegalArgumentException("I minuti lavorati non possono essere negativi: " + minutes);	
		mappaTimeSheetGiornalieri.get(date).setWorkedTime(projectName, minutes);
	}
	
	public int getWorkedTimePerProject(LocalDate date, String projectName) {
		Objects.requireNonNull(projectName, "Il nome del progetto non può essere nullo");
		Objects.requireNonNull(date, "La data del giorno non può essere nullo");
		if(date.isBefore(this.getStartDate()) || date.isAfter(this.getEndDate())) throw new IllegalArgumentException("La data non appartiene al mese e all'anno del TimeSheet");
		if (projectName.isBlank()) throw new IllegalArgumentException("Il nome del progetto non può essere vuoto");
		return mappaTimeSheetGiornalieri.get(date).getWorkedTime(projectName);
	}
	
	public int getWorkedTime(LocalDate date) {
		Objects.requireNonNull(date, "La data del giorno non può essere nullo");
		if(date.isBefore(this.getStartDate()) || date.isAfter(this.getEndDate())) throw new IllegalArgumentException("La data non appartiene al mese e all'anno del TimeSheet");
		if(!mappaTimeSheetGiornalieri.containsKey(date)) return 0;
		return mappaTimeSheetGiornalieri.get(date).getTotalWorkedTime();
	}
	
	@Override
	public String toString() {
		return "Mese di " + month.getDisplayName(TextStyle.FULL, Locale.ITALY).toUpperCase() + " " + year + "\t" 
				+ "ore totali " + getTotalWorkedTimeAsString() 
				+ ", di cui: " + this.activeProjects().stream().map(p -> p + " = " + this.getTotalWorkedTimePerProjectAsString(p)).collect(Collectors.joining(", "));
	}
	
	public String toFullString() {
		return this.toString() + System.lineSeparator() + "Dettaglio:" + System.lineSeparator() 
				+ mappaTimeSheetGiornalieri.entrySet().stream().map(
						es -> Formatters.dateFormatter.format(es.getKey()) + "\t" 
						+ this.activeProjects().stream().map(p -> p + " = " + es.getValue().getWorkedTimeAsString(p)).collect(Collectors.joining(", "))
					).collect(Collectors.joining(System.lineSeparator()));
	}
}
