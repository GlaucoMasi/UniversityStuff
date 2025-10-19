package streams;

public class Streams {
	/*
	 * .char()
	 * .stream()
	 * IntStream().of()
	 * Arrays.stream(array)
	 * DoubleStream/LongStream
	 * Stream.iterate(3, n -> 2*n)
	 * Stream.generate(Math::random)
	 * Stream<Double> = Stream.of()
	 * Stream<Integer> = IntStream.boxed()
	 

	 * .distinct()
	 * .filter()
	 * .flatMap(Set::stream)
	 * .flatMap((Collection<String> c -> c.stream())
	 * .limit()
	 * .map(Agency::city) == .map(a -> a.city())
	 * .map(String::trim) == .map(a -> a.trim())
	 * .mapToInt(Integer::intValue)
	 * .mapToLong(Long::longValue)
	 * .mapToDouble(Fraction::getNumerator)
	 * .mapToObj()
	 * .parallel()
	 * .skip()
	 * .sorted()
	 * .sorted(Comparator.comparing().thenComparing())


	 * .allMatch()
	 * .anyMatch()
	 * .noneMatch()
	 * .average()
	 * .average().getAsDouble()
	 * .collect(Collectors.toList()))
	 * .collect(Collectors.joining("\n"))
	 * .collect(Collectors.joining("\n", "", "\n"))
	 * .collect(Collectors.toCollection(TreeSet::new))
	 * .collect(Collectors.summingInt/Double/...())          somma
	 * .collect(Collectors.summaringInt/Double/...())        molte statistiche (max, min, media, sum)
	 * .collect(Collectors.toMap(t -> t.getValue()+1, t -> t.toString()))
	 * .collect(Collectors.toMap(Fraction::getDenominator, Function.identity())
	 * .collect(Collectors.toCollection(() -> new TreeSet<>((Comparator.comparing(Fraction::getDenominator))))
	 * .collect(Collectors.averagingInt/Double(Fraction::getDenominator))
	 * .collect(Collectors.groupingBy(Fraction::getDenominator, Collectors.counting())   standard crea Map<K, List<T>>
	 * .collect(Collectors.partitioningBy(...)                                           analogo, ma in due gruppi con boolean predicate
	 * .count()
	 * .findAny()
	 * .findFirst()
	 * .forEach()
	 * .forEach(menu::add)
	 * .forEachOrdered()
	 * .get()
	 * .max()
	 * .min()
	 * .peek()
	 * .reduce(0, (a, b) -> a+b))      riduce lo stream con un accumulatore
	 * .sum()
	 * .toArray(String[]::new)
	 * .toList()
	 */
}
