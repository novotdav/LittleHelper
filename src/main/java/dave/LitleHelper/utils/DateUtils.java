package dave.LitleHelper.utils;

import java.time.format.DateTimeFormatter;

public class DateUtils {

	public static DateTimeFormatter getDayFormatter() {
		return DateTimeFormatter.ofPattern("E dd. MM.");
	}

	public static DateTimeFormatter getYearFormatter() {
		return DateTimeFormatter.ofPattern("yyyy");
	}

	public static DateTimeFormatter getWeekFormatter() {
		return DateTimeFormatter.ofPattern("w. 'týden'");
	}

}
