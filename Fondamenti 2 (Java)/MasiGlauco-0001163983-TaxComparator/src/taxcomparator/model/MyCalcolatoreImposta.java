package taxcomparator.model;

import java.text.DecimalFormat;
import java.util.StringJoiner;

public class MyCalcolatoreImposta implements CalcolatoreImposta{
	private DecimalFormat currencyFormatter = new DecimalFormat("¤ #,##0.##");
	private StringJoiner sj;

	@Override
	public double calcolaImposta(double reddito, Fasce fasce) {
		sj = new StringJoiner(System.lineSeparator());
		
		sj.add("Imponibile lordo = " + currencyFormatter.format(reddito) 
				+ ", no-tax area = " + currencyFormatter.format(fasce.getNoTaxArea()) 
				+ ", imponibile netto = " + currencyFormatter.format(Math.max(0, reddito-fasce.getNoTaxArea())));
		
		double imposta = 0;
		reddito -= fasce.getNoTaxArea();
		
		for(Fascia fascia : fasce.getFasce().reversed()) {
			if(reddito > fascia.getMin()) {
				sj.add(fascia.toString());

				double imponibile = reddito-fascia.getMin();
				imposta += imponibile*fascia.getAliquota();
				reddito -= imponibile;
				
				sj.add("Imponibile corrente = " + currencyFormatter.format(reddito+imponibile) 
						+ ", imposta = " + currencyFormatter.format(imponibile*fascia.getAliquota()) 
						+ ", imponibile restante = " + currencyFormatter.format(reddito));
			}
		}
		
		return imposta;
	}

	@Override
	public String getLog() {
		return sj.toString();
	}

}
