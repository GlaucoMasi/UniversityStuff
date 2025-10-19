package formattazione;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class FormattatoriNumerici {
	public void formattazioneNumeri() {
		NumberFormat fn = NumberFormat.getNumberInstance(Locale.ITALY);
		fn.setMaximumFractionDigits(2);
		fn.format(43.21418);
	}
	
	public void formattazionePercentuali() throws ParseException {
		NumberFormat fp = NumberFormat.getPercentInstance(Locale.ITALY);
		fp.setMaximumFractionDigits(2);
		fp.format(0.43251);
		fp.parse("7,24%");
	}
	
	public void formattazioneValute() throws ParseException {
		NumberFormat fv = NumberFormat.getCurrencyInstance(Locale.US);
		fv.format(1245.21421);
		fv.parse("$124.51");
	}
	
	public void formattazionePersonalizzata() {
		DecimalFormat formatter = new DecimalFormat("¤ #,##0.##;¤ -#,##0.##");
		formatter.format(1235.2551); // € 1.235,26
		formatter.format(-10.42); // € -10,42
	}
}
