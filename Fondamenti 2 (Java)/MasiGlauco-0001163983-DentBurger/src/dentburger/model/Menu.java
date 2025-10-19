package dentburger.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.HashMap;

/** Rappresenta il menù del ristorante, strutturato come mappa di mappe che gestiscono liste di prodotti. 
 *  Più precisamente, il menù è organizzato come mappa indicizzata per Categoria: a ogni categoria è 
 *  associata un'ulteriore mappa, indicizzata per genere (stringa), che associa a ogni genere la lista 
 *  dei Prodotto di quella categoria e genere (vedere figura nel testo)
 */
public class Menu {

	private Map<Categoria, Map<String,List<Prodotto>> > menu;
	
	/** Il costruttore istanzia la mappa vuota: è compito del metodo add popolarla via via che vengono 
	 *  aggiunti prodotti, curando la costruzione delle mappe di secondo livello e delle liste quando 
	 *  necessario. 
	 */
	public Menu() {
		menu = new HashMap<>();
	}
	
	public void add(Prodotto p) {
		Objects.requireNonNull(p, "Fornito prodotto nullo");
		if(menu.get(p.getCategoria()) == null) menu.put(p.getCategoria(), new HashMap<>());
		if(menu.get(p.getCategoria()).get(p.getGenere()) == null) menu.get(p.getCategoria()).put(p.getGenere(), new ArrayList<>());
		if(menu.get(p.getCategoria()).get(p.getGenere()).contains(p)) throw new IllegalArgumentException("Prodotto già presente nel menu");
		menu.get(p.getCategoria()).get(p.getGenere()).add(p);
	}
	
	private Map<String,List<Prodotto>> getProdottiPerCategoria(Categoria c){
		Objects.requireNonNull(c, "Fornita categoria nulla");
		return Map.copyOf(menu.getOrDefault(c, Collections.emptyMap()));
	}
	
	public Set<String> getGeneriPerCategoria(Categoria c){
		Objects.requireNonNull(c, "Fornita categoria nulla");
		return menu.get(c).keySet();
	}
	
	public List<Prodotto> getProdottiPerCategoriaEGenere(Categoria c, String denominazione){
		Objects.requireNonNull(c, "Fornita categoria nulla");
		Objects.requireNonNull(denominazione, "Fornita denominazione nulla");
		if(denominazione.isBlank()) throw new IllegalArgumentException("Stringa denominazione vuota");
		return getProdottiPerCategoria(c).getOrDefault(denominazione, Collections.emptyList());
	}
	
	@Override
	public String toString() { 
		return menu.keySet().stream()
							.map(this::getProdottiPerCategoria)
							.map(Map::entrySet)
							.flatMap(Set::stream)
							.map(entry -> entry.getKey() + " " + entry.getValue().toString())
							.collect(Collectors.joining(System.lineSeparator()));
	}
	
}
