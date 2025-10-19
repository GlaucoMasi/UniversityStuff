package meteodent.model;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Formatters {
	public static final DateTimeFormatter itDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy", Locale.ITALY);
	public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.ITALY);
	public static final DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.ITALY);
}
