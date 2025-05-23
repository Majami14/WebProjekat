package beans;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateHelper {

	public static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public static String dateToString(LocalDate date) {
		return date.format(dateFormat);
	}

	public static LocalDate stringToDate(String date) {
		return LocalDate.parse(date, dateFormat);
	}
}
