package utils;

import scala.Tuple2;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.function.Function;

public class DateHelper {
	
	private static final ZoneId zone = ZoneId.systemDefault();
	
	// Public methods
	
	public static Instant dayBeginning(Instant date) {
		return processInstant(localDate -> localDate.atStartOfDay(zone).toLocalDate(), date);
	}
	
	public static Instant weekBeginning(Instant date) {
		return processInstant(localDate -> localDate.with(DayOfWeek.MONDAY), date);
	}
	
	public static Instant monthBeginning(Instant date) {
		return processInstant(localDate -> localDate.withDayOfMonth(1), date);
	}
	
	public static Instant yearBeginning(Instant date) {
		return processInstant(localDate -> localDate.withDayOfYear(1), date);
	}
	
	public static Instant oneDayAgo(Instant date) {
		return processInstant(localDate -> localDate.minusDays(1), date);
	}
	
	public static Instant oneWeekAgo(Instant date) {
		return processInstant(localDate -> localDate.minusWeeks(1), date);
	}
	
	public static Instant oneMonthAgo(Instant date) {
		return processInstant(localDate -> localDate.minusMonths(1), date);
	}
	
	public static Instant oneYearAgo(Instant date) {
		return processInstant(localDate -> localDate.minusYears(1), date);
	}
	
	public static Instant now() {
		return Instant.now();
	}
	
	public static Tuple2<Instant, Instant> strings2Dates(String dates) {
		final Tuple2<String, String> splittedDatesStrings = StringHelper.splitBlank(dates);
		return Tuple2.apply(Instant.parse(splittedDatesStrings._1()), Instant.parse(splittedDatesStrings._2()));
	}
	
	// Private methods
	
	private static Instant processInstant(Function<LocalDate, LocalDate> f, Instant instant) {
		return toInstant(f.apply(toLocalDate(instant)));
	}
	
	private static LocalDate toLocalDate(Instant instant) {
		return instant.atZone(zone).toLocalDate();
	}
	
	private static Instant toInstant(LocalDate localDate) {
		return localDate.atStartOfDay(zone).toInstant();
	}
	
}
