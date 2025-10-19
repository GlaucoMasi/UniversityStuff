package codice_fiscale;

public class ClasseMain {
	public static void main(String args[]) {
		if(args.length == 1 && args[0].equals("Testing")) {
			Collaudo.startTests();
			System.out.println("Test Passati!");
			return;
		}
		
		if(args.length != 7) {
			System.out.println("Numero errato di argomenti, devono essere 7: Nome, Cognome, Giorno, Mese, Anno, Sesso e Comune");
			return;
		}
		
		System.out.println(CodiceFiscale.calcolaCodiceFiscale(args[0], args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), args[5], args[6]));
	}
}
