package dentburger.model;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Formatters {
	public static final NumberFormat priceFormatter = NumberFormat.getCurrencyInstance(Locale.ITALY);
	public static final DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.ITALY); 
}
