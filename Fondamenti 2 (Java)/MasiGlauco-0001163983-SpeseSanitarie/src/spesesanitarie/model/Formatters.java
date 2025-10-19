package spesesanitarie.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Formatters {
	public static final DateTimeFormatter itDateFormatter =
			DateTimeFormatter.ofPattern("dd/MM/yyyy").withLocale(Locale.ITALY);
	public static final NumberFormat itPriceFormatter =
			new DecimalFormat("¤ #,##0.##;¤ -#,##0.##");
}
