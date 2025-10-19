package mastermind.model;

public class Gioco {
	private int dim, maxTentativi, numTentativi;
	private Combinazione[] tentativi;
	private Risposta[] risposte;
	private Status status;

	public Gioco(int maxTentativi, int dim){
		this.dim = dim;
		this.maxTentativi = maxTentativi;
		this.numTentativi = 0;
		this.status = Status.IN_CORSO;
		this.tentativi = new Combinazione[maxTentativi];
		this.risposte = new Risposta[maxTentativi];
	}
	
	public int dimensione() {
		return this.dim;
	}
	
	public int maxTentativi() {
		return this.maxTentativi;
	}
	
	public Risposta risposta(int index) {
		if(index < 0 || index >= this.risposte.length) return null;
		return this.risposte[index];
	}
	
	public Status stato() {
		return this.status;
	}
	
	public int tentativiEffettuati() {
		return this.numTentativi;
	}
	
	public int tentativiRimasti() {
		return this.maxTentativi-this.tentativiEffettuati();
	}
	
	public Combinazione tentativo(int index) {
		if(index < 0 || index >= this.tentativi.length) return null;
		return this.tentativi[index];
	}
	
	public Risposta ultimaRisposta() {
		return this.risposta(this.tentativiEffettuati()-1);
	}
	
	public Combinazione ultimoTentativo() {
		return this.tentativo(this.tentativiEffettuati()-1);
	}
		
	public Status registraMossa(Combinazione tentativo, Risposta risposta) {
		this.tentativi[this.numTentativi] = tentativo;
		this.risposte[this.numTentativi] = risposta;
		this.numTentativi++;
		
		if(risposta.vittoria()) {
			this.status = Status.VITTORIA;
		}else if(this.tentativiRimasti() == 0) {
			this.status = Status.PERSO;
		}
		
		return this.stato();
	}
	
	public String situazione() {
		StringBuilder sb = new StringBuilder("");
		
		for(int i = 0; i < this.tentativiEffettuati(); i++) {
			sb.append((i+1) + ") ");
			
			String stringaTentativo = this.tentativo(i).toString();
			
			sb.append(stringaTentativo);
			sb.append("\t\t" + (stringaTentativo.length() < 20 ? "\t" : ""));
			
			sb.append(this.risposta(i).toString());
			sb.append(System.lineSeparator());
		}
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Situazione:"+System.lineSeparator());
		sb.append(this.situazione());
		sb.append("Gioco: ");
		sb.append(this.stato().toString());
		return sb.toString();
	}
	
	
}
