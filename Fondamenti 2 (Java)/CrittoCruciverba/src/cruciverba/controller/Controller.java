package cruciverba.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalInt;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Set;

import cruciverba.model.Cruciverba;
import cruciverba.model.SchemaNumerato;
import cruciverba.persistence.BadFileFormatException;
import cruciverba.persistence.MyCruciverbaReader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public abstract class Controller  {
	
	private File file;
	private SchemaNumerato schemaNumerato;
	private Modalita modalita;

	
	public Controller(File file) {
		this.file = file;
	}
	
	protected File getInputFile() {
		return file;
	}
	
	public static void alert(String title, String headerMessage, String contentMessage) {
		javafx.scene.control.Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
	public void leggiSchema() throws BadFileFormatException, IOException {
		var cruciverbaReader = new MyCruciverbaReader();
		setSchemaNumerato(new SchemaNumerato(cruciverbaReader.leggiCruciverba(new FileReader(getInputFile()))));
		setModalita(Modalita.ESPERTA);
	}

	public abstract String parolaIniziale();

	public void impostaCaratteriIniziali() {
		Stream.of(parolaIniziale().split("")).forEach(ch -> {
			OptionalInt possibleMapping = getSchemaNumerato().getMapping(ch.charAt(0));
			if(possibleMapping.isEmpty()) throw new IllegalArgumentException("Situazione imprevista in impostaCaratteri: carattere inesistente nel cruciverba " + ch);
			getSchemaNumerato().setMapping(ch.charAt(0),possibleMapping.getAsInt());
			getSchemaNumerato().associa(possibleMapping.getAsInt(),ch.charAt(0));
			});
	}
	
	public List<Character> lettereDisponibili() {
		var tutti = new HashSet<>(this.getSchemaNumerato().getAllMappings().keySet());
		var usati = this.getSchemaNumerato().associazioni().values();
		tutti.removeAll(usati);
		tutti.remove(Cruciverba.NERA);
		return tutti.stream().sorted().toList();
	}

	public List<Integer> numeriDisponibili() {
		var tutti = new HashSet<>(this.getSchemaNumerato().getAllMappings().values());
		var usati = this.getSchemaNumerato().associazioni().keySet();
		tutti.removeAll(usati);
		tutti.remove(0);
		return tutti.stream().sorted().toList();
	}

	public void setModalita(Modalita m) {
		this.modalita = m;
	}
	
	public Modalita getModalita() {
		return modalita;
	}

	public Cruciverba getCruciverba() {
		return schemaNumerato.getCruciverba();
	}
	
	public String schemaNumeratoAsString() {
		return schemaNumerato.toString();
	}
	
	public void setSchemaNumerato(SchemaNumerato schemaNumerato) {
		this.schemaNumerato = schemaNumerato;
	}
	
	public SchemaNumerato getSchemaNumerato() {
		return schemaNumerato;
	}
	
	public void associa(int v, char c) {
		this.schemaNumerato.associa(v, c);
	}
	
	public void disassocia(int v) {
		this.schemaNumerato.disassocia(v);
	}
	
	public Set<Entry<Integer, Character>> associazioni() {
		return this.schemaNumerato.associazioni().entrySet();
	}

	public String associazioniAsString(){
		return this.associazioni().stream()
									 .map(entry -> entry.getKey() + " = " + entry.getValue())
									 .collect(Collectors.joining(System.lineSeparator()));
	}

	public int getAssociazione(int v) {
		return this.schemaNumerato.getAssociazione(v);
	}

}
