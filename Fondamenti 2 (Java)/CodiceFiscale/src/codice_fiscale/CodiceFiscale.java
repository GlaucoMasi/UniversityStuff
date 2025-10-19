package codice_fiscale;

public class CodiceFiscale {
	private static boolean isConsonante(String c) {
		return !"AEIOU".contains(c);
	}
	
	private static boolean isNumero(String c) {
		return "0123456789".contains(c);
	}
	
	private static String calcolaCognome(String cognome) {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < cognome.length() && sb.length() < 3; i++) {
			if(isConsonante(String.valueOf(cognome.charAt(i)))) {
				sb.append(cognome.charAt(i));
			}
		}
		
		for(int i = 0; i < cognome.length() && sb.length() < 3; i++) {
			if(!isConsonante(String.valueOf(cognome.charAt(i)))) {
				sb.append(cognome.charAt(i));
			}
		}
		
		while(sb.length() < 3) sb.append("X");
		
		return sb.toString();
	}
	
	private static String calcolaNome(String nome) {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < nome.length() && sb.length() < 4; i++) {
			if(isConsonante(String.valueOf(nome.charAt(i)))) {
				sb.append(nome.charAt(i));
			}
		}
		
		if(sb.length() > 3) return String.valueOf(sb.charAt(0)) + String.valueOf(sb.charAt(2)) + String.valueOf(sb.charAt(3));
		
		for(int i = 0; i < nome.length() && sb.length() < 3; i++) {
			if(!isConsonante(String.valueOf(nome.charAt(i)))) {
				sb.append(nome.charAt(i));
			}
		}
		
		while(sb.length() < 3) sb.append("X");		

		return sb.toString();
	}
	
	private static String calcolaAnno(int anno) {
		String conv = String.valueOf(anno);
		return conv.substring(2);
	}
	
	private static char calcolaMese(int mese) {
		return "ABCDEHLMPRST".charAt(mese-1);
	}
	
	private static String calcolaGiornoSesso(int giorno, String sesso) {
		String ans = String.valueOf(giorno + (sesso.equals("F") ? 40 : 0));
		if(ans.length() == 1) ans = '0'+ans;
		return ans;
	}
	
	private static String calcolaComune(String comune) {
		if(comune.equals("BOLOGNA")) return "A944";
		if(comune.equals("REGGIOEMILIA")) return "H223";
		if(comune.equals("MILANO")) return "F205";
		if(comune.equals("IMOLA")) return "E289";
		if(comune.equals("ANCONA")) return "A271";
		return "XXXX";
	}
	
	private static char calcolaCarControllo(String codice) {
		String peso_numeri_dispari = "BAFHJNPRTV";
		String peso_lettere_dispari = "BAFHJNPRTVCESULDGIMOQKWZYX";

		int tot = 0;
		for(int i = 0; i < codice.length(); i++) {
			char curr = codice.charAt(i);
			int weight = (isNumero(String.valueOf(curr)) ? curr-'0' : curr-'A');
			if(i%2 == 1) tot += weight;
			else if(isNumero(String.valueOf(curr))) tot += peso_numeri_dispari.charAt(weight)-'A';
			else tot += peso_lettere_dispari.charAt(weight)-'A';
		}
		
		return (char)('A'+tot%26);
	}
	
	public static String calcolaCodiceFiscale(String nome, String cognome, int giorno, int mese, int anno, String sesso, String comune) {
		StringBuilder sb = new StringBuilder();
		sb.append(calcolaCognome(cognome.toUpperCase()));
		sb.append(calcolaNome(nome.toUpperCase()));
		sb.append(calcolaAnno(anno));
		sb.append(calcolaMese(mese));
		sb.append(calcolaGiornoSesso(giorno, sesso.toUpperCase()));
		sb.append(calcolaComune(comune.toUpperCase()));
		sb.append(calcolaCarControllo(sb.toString()));
		return sb.toString();
	}
	
	public static boolean verificaCodiceFiscale(String nome, String cognome, int giorno, int mese, int anno, String sesso, String comune, String codice) {
		StringBuilder sb = new StringBuilder();
		String ordine = "LMNPQRSTUV";
		
		sb.append(calcolaCodiceFiscale(nome, cognome, giorno, mese, anno, sesso, comune));
		
		for(int i = 0; i < sb.length()-1; i++) {
			if(sb.charAt(i) == codice.charAt(i)) continue;
			
			if(!isNumero(String.valueOf(sb.charAt(i))) || codice.charAt(i) != ordine.charAt(sb.charAt(i)-'0')) return false;
			
			sb.setCharAt(i, ordine.charAt(sb.charAt(i)-'0'));
		}
		
		sb.setLength(sb.length()-1);
		sb.append(calcolaCarControllo(sb.toString()));
		
		return sb.toString().equals(codice);
	}
}
