package ja1;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;

public class DateTest {
	public static void main(String[] args) throws InterruptedException {
		// InstantTest();
		// LocalDateTest();
		// TemporalAdjusterTest();
		// LocalTimeTest();
		DateTimeFormatterTest();
	}

	public static void DateTimeFormatterTest() {
		LocalDate today = LocalDate.now();
		LocalDate firstDay = LocalDate.of(1990, 1, 1);
		// Pokaże odpowiednio sfotmatowany czas
		String stData1 = DateTimeFormatter.ISO_WEEK_DATE.format(today);
		// Poniższe da bład, bo LocalTime nie będzie mogło obsłużyć Year, month etc
		// String stData1 = DateTimeFormatter.ISO_DATE_TIME.format(rightNow);		
		LocalDate tomorrow = LocalDate.now().plus(1L, ChronoUnit.DAYS);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("e yyyy-MM-dd HH:mm:ss");
		// formatter.parse("MON 2016-07-12 12:05:11");
		LocalDateTime lt1 = LocalDateTime.parse("3 1969-07-16 09:32:11", formatter);
		// UWAGA! poniższe da bład, bo nie będzie zgadzać się wartośc e z podaną datą
		// LocalDateTime lt1 = LocalDateTime.parse("2 1969-07-16 09:32:11", formatter);
		System.out.println(today);
		System.out.println(stData1);
		System.out.println(lt1);
	}

	public static void LocalTimeTest() {
		// teraz
		LocalTime rightNow = LocalTime.now();
		// 22:30:00.0
		LocalTime bedtime = LocalTime.of(22, 30);
		// Sekunda
		int sec = rightNow.getSecond();
		// Teraz + 10 min
		LocalTime Now10min = rightNow.plusMinutes(10);
		// Ile sekund or początku dnia
		int secFromNidnight = rightNow.toSecondOfDay();
		System.out.println(Arrays.asList(rightNow, bedtime, sec, Now10min, secFromNidnight));
	}

	// Pobieranie punktów szczególnych od zadanej daty: pierwszy dzień miesiąca etc.
	public static void TemporalAdjusterTest() {
		// dzisiaj
		LocalDate today = LocalDate.now();
		// Pierwszy wtorek, od daty lub pózniej
		LocalDate nextTuesday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY));
		// Pierwszy dzień miesiąca z daty
		LocalDate firstDayThisMonth = today.with(TemporalAdjusters.firstDayOfMonth());
		// Ostatni dzień roku z daty
		LocalDate lastDayThisYear = today.with(TemporalAdjusters.lastDayOfYear());
		// Ostatni poniedziałek z miesiąca daty
		LocalDate lastInThisMonth = today.with(TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY));
		System.out.println(Arrays.asList(today, nextTuesday, firstDayThisMonth, lastDayThisYear, lastInThisMonth));
	}

	// Data, jej budowanie i modyfikacja
	public static void LocalDateTest() {
		LocalDate ld1 = LocalDate.now();
		LocalDate ld2 = LocalDate.of(2015, 01, 01);
		LocalDate ld3 = LocalDate.ofEpochDay(10);// +10 dni do 1970.01.01
		LocalDate ld4 = LocalDate.ofYearDay(02, 32);// +2 lata +32 dni do początku roku 0
		System.out.println(ld1 + " " + ld2 + " " + ld3 + " " + ld4);
		ld1.plusDays(2);
		LocalDate.of(2016, 1, 31).plusMonths(1); // UWAGA! Coś takiego da nie 31.02.2016, ale 29.02.2016 !!!
		int intMonth = ld1.getMonth().getValue();
		int intDayOfYear = ld1.getDayOfYear();
		DayOfWeek intDayOfWeek = ld1.getDayOfWeek();
		System.out.println(ld1 + " " + intMonth + " " + intDayOfYear + " " + intDayOfWeek);
	}

	// czas podawany w milisekundach
	public static void InstantTest() throws InterruptedException {
		Instant start = Instant.now();
		Thread.sleep(100);
		Instant stop = Instant.now();
		Duration diff = Duration.between(start, stop);
		System.out.println(start.toString() + " - " + stop.toString() + "; " + diff.toString());
	}
}
