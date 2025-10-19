package autonoleggio.controller;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;

import autonoleggio.model.Rate;
import autonoleggio.model.Agency;
import autonoleggio.model.CarType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Controller {

	public static void alert(String title, String headerMessage, String contentMessage) {
		javafx.scene.control.Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}

	private Set<Agency> agencies;
	private SortedMap<CarType, Rate> rates;
	public final Set<DayOfWeek> WEEKEND_DAYS = Set.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
		
	//--------------
	
	public Controller(Set<Agency> agencies, SortedMap<CarType, Rate> rates) {
		Objects.requireNonNull(agencies, "Insieme agenzie nullo nel construttore del Controller");
		Objects.requireNonNull(rates, "Elenco tariffe nullo nel construttore del Controller");
		this.agencies = agencies;
		this.rates = rates;
	}

	public Set<Agency> getAgencies() {
		return agencies;
	}

	public SortedMap<CarType, Rate> getRates() {
		return rates;
	}

	public List<String> getCities() {
		return agencies.stream().map(Agency::city).sorted().distinct().toList();
	}
	
	public List<Agency> getAgencies(String city) {
		return agencies.stream().filter( agency -> agency.city().equalsIgnoreCase(city)).sorted().toList();
	}

	private String siNo(boolean flag) {
		return flag ? "sì" : "no";
	}
	
	public Result calcolaTariffa(String city, Agency agency, LocalDateTime start, LocalDateTime end, CarType carType, boolean dropOff) {
		Objects.requireNonNull(city, "La città non può essere nulla");
		Objects.requireNonNull(agency, "L'agenzia non può essere nulla");
		Objects.requireNonNull(start, "Il tempo d'inizio non può essere nulla");
		Objects.requireNonNull(end, "Il tempo di fine non può essere nulla");
		Objects.requireNonNull(carType, "Il tipo di macchina non può essere nulla");
		if(city.isBlank()) throw new IllegalArgumentException("La città non può essere vuota");
		if(end.isBefore(start)) throw new IllegalArgumentException("La fine non può essere prima dell'inizio");
		
		int giorni = (int) Duration.between(start, end).toDaysPart();
		if(!Duration.between(start, end).minusDays(giorni).isZero()) giorni++;
		double costo = giorni*getRates().get(carType).dailyRate();
		
		boolean weekendFlag = false;
		if(start.getDayOfWeek().getValue() >= DayOfWeek.FRIDAY.getValue() && end.getDayOfWeek().getValue() >= start.getDayOfWeek().getValue() && giorni <= 3) {
			if(getRates().get(carType).weekendRate() < costo) {
				weekendFlag = true;
				costo = getRates().get(carType).weekendRate();
			}
		}

		if(dropOff) costo += getRates().get(carType).dropoffCharge();
		String msg = "Giorni: " + giorni + ", tariffa weekend: " + siNo(weekendFlag) + ", dropOff: " + siNo(dropOff);
		
		return new Result(costo, msg);
	}
	
}
