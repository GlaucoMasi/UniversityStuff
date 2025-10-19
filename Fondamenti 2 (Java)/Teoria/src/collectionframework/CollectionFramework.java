package collectionframework;

import java.util.*;

record Persona(String nome, String cognome) implements Comparable<Persona>{
	public int compareTo(Persona that) {
		if(!this.nome.equals(that.nome)) return this.nome.compareTo(that.nome);
		return this.cognome.compareTo(that.cognome);
	}
}

public class CollectionFramework {
	public void collectionFramework() {
		Set<Persona> set = Set.of(new Persona("Glauco", "Masi"), new Persona("Iris", "Prifti")); // Immutable
		set = new TreeSet<Persona>(); // Per l'ordinamento, implementa anche SortedSet
		set = new HashSet<Persona>(); // Più efficiente
		set = new LinkedHashSet<Persona>(); // Anche LinkedList per iterare
		set.add(new Persona("Emily", "Prifti")); set.contains(new Persona("Glauco", "Masi")); set.remove(new Persona("Glauco", "Masi"));
		
		List<Persona> list = List.of(new Persona("Glauco", "Masi"), new Persona("Iris", "Prifti")); // Immutable
		list = new LinkedList<Persona>(); // Inserimento O(1), Accesso O(n)
		list = new ArrayList<Persona>(); // Inserimento O(n), Accesso O(1)
		list.add(new Persona("Emily", "Prifti")); list.get(1); list.remove(new Persona("Glauco", "Masi"));

		Queue<Persona> queue = new LinkedList<Persona>();
		queue = new PriorityQueue<Persona>(); // Per l'ordinamento
		queue.peek(); queue.poll(); queue.add(new Persona("Emily", "Prifti"));
		
		Deque<Persona> deque;
		deque = new LinkedList<Persona>();
		deque = new ArrayDeque<Persona>();
		deque.getFirst(); deque.getLast(); deque.removeFirst(); deque.removeLast();
		deque.addFirst(new Persona("Emily", "Prifti")); deque.addLast(new Persona("Emily", "Prifti"));
		
		Map<String, Persona> map = Map.of("Glauco", new Persona("Glauco", "Masi"), "Iris", new Persona("Iris", "Prifti")); // Immutable
		map = new TreeMap<String, Persona>(); // Per l'ordinamento, implementa anche SortedMap
		map = new HashMap<String, Persona>(); // Più efficiente
		map = new LinkedHashMap<String, Persona>(); // Anche LinkedList per iterare
		map.put("Emily", new Persona("Emily", "Prifti")); map.containsKey("Glauco"); map.containsValue(new Persona("Glauco", "Masi"));
		map.entrySet(); map.keySet(); map.values(); map.get("Glauco"); map.remove("Glauco", new Persona("Glauco", "Masi"));
		
		list.toArray(new Persona[0]);
		Persona[] persone = new Persona[5]; Arrays.asList(persone);
		Collections.emptyList(); Collections.emptySet(); Collections.emptyMap();
		Collections.sort(list); Collections.binarySearch(list, new Persona("Glauco", "Prifti"));		
	}
}
