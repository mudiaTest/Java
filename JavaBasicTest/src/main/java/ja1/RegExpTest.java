/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ja1;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class RegExpTest {

	public static void main(String[] args) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException,
	    InvocationTargetException, IOException, Exception, Throwable {
		RESingle();
		REMulti();
		REParseResult();
		REStringSplit();
		REStringReplace();
		REParameters();
		REStream();
	}

	// RegExp - uĹĽyÄ‡ do znalezienia tylko pierwszego - mniej wydajne jeĹ›li chcemy
	// uzywac w pÄ™tli
	// Oddaje tylko wynik CZY speĹ‚nia, a nie ile razy i jak speĹ‚nia
	// matches
	public static void RESingle() {
		String regex = "\\sma[a-zA-Z]*\\s";
		CharSequence input = "Ala ma kota i nie mam tego auta";
		// "\\sma[a-zA-Z]*\\s" odda false
		// ".*\\sma[a-zA-Z]*\\s.*" odda true
		System.out.println("0-> " + Pattern.matches(regex, input));
	}

	// RegExp - odnajduje wszystkie wystÄ…pienia
	// matcher1.find
	public static void REMulti() {
		// "group()" = "group(0)" - oddaje po prostu odznaleziony wynik
		// matcher.toMatchResult().start() - wkazuje na pierwszy element znalezionego
		// ciÄ…gu
		// matcher.toMatchResult().end() - wkazuje na pierwszy element PO znalezionym
		// ciÄ…gu
		String regex = "\\sma[a-zA-Z]*\\s"; // sĹ‚owa zaczynajÄ…ce nie na "ma"
		CharSequence input = "Ala ma kota i nie mam tego auta";
		Pattern pattern1 = Pattern.compile(regex);
		Matcher matcher1 = pattern1.matcher(input);
		while (matcher1.find()) {
			String match1 = matcher1.group();
			System.out.print(matcher1.toMatchResult().group() + ": " + /* znaleziony wynik */
			    matcher1.toMatchResult().start() + "-" + /* poczÄ…tek wyniku w ĹşrĂłdle */
			    matcher1.toMatchResult().end()); /* koniec wyniku w ĹşrĂłdle */
			// drugi argument subSequence tpokazuje na pierwszy NIE NALEĹ»Ä„CY do podciagu
			System.out.println(" | '"
			    + input.subSequence(matcher1.toMatchResult().start(), matcher1.toMatchResult().end()).toString() + "'");
		}
	}

	// Parsowanie poszczegĂłlnych fragmentĂłw wyniku.
	public static void REParseResult() {
		// KaĹĽdy () bÄ™dzie traktowany jako osobna grupa.
		// "group(n)" oddaje n-tÄ… grupe w wyniku, czyli n-ty nawias. Aby siÄ™ nie
		// pomyliÄ‡ najlepiej naywac nawiasy
		// (?:...) zostanie pominiÄ™te. czasem dodajemy (...) w celu budowy samego
		// regexpa, ale nie wyciÄ…gniÄ™cia z niego informacji
		// ?<...> pozwala odwoĹ‚aÄ‡ siÄ™ do grupy poprzez jej nazwÄ™, a nie numer
		// UWAGA! Grupa oddaje ostatnie "wystÄ…pienie", wiÄ™c (?<item>...)* odda tylko
		// jeden string
		Pattern pattern2 = Pattern.compile("(?<item>\\p{Alnum}+(?:\\s+\\p{Alnum}+)*)\\s+([A-Z]{3})([0-9.]*)");
		Matcher matcher2 = pattern2.matcher("Blackwell Toaster USD29.95\nMono TV PLN11.22");
		while (matcher2.find()) {
			String item = matcher2.group("item");// Blackwell Toaster, Mono TV
			String currency = matcher2.group(2);// USD / PLN
			String price = matcher2.group(3); // 29.95, 11.22
			int brk = 0;
		}

		String input = "ab1c1ab2c2abc3ab41c4";
		Pattern pattern3 = Pattern.compile("(ab)([0-9])+");
		Matcher matcher3 = pattern3.matcher(input);// ab1 ab2 ab41
		while (matcher3.find()) {
			String match1 = matcher3.group();
			System.out.print("'" + matcher3.toMatchResult().group(1) + "', '" + matcher3.toMatchResult().group(2) + ": "
			    + matcher3.toMatchResult().start() + "-" + matcher3.toMatchResult().end());
			// drugi argument subSequence pokazuje na pierwszy NIE NALEĹ»Ä„CY do podciagu
			System.out.println(" | '" + input.subSequence(matcher3.toMatchResult().start()/* pierwszy naleĹĽacy do grupy */,
			    matcher3.toMatchResult().end() /* pierwszy nienaleĹĽacy do grupy */).toString() + "'");
		}

		int brk = 0;
	}

	// PodziaĹ‚ wg wzorca
	public static void REStringSplit() {
		// UWAGA! PoniĹĽszy ciÄ…g ma 2 linie, ale split potrzktuje znak nowej lini jako
		// "zwykĹ‚y" znak, wiÄ™c jeden z elementĂłw bÄ™dzie zawieraĹ‚ w sobie znak nowej
		// linii
		String input4 = "1, 2,3,   4 \n 5, ,6";
		Pattern commas1 = Pattern.compile("\\s*,\\s*");
		String[] tokens = commas1.split(input4);
		// "1, 2, 3" turns into ["1", "2", "3", "4 "]
		int brk = 0;
		System.out.println(input4);
		System.out.println(Arrays.asList(tokens));
	}

	// Podmiana wg wzorca
	public static void REStringReplace() {
		// $1 odwoĹ‚uje siÄ™ do () - tak jak group
		// ${minutes} odwoĹ‚uje siÄ™ do (?<minutes>...) - tak jak group po nazwie
		String stTime = "3:45".replaceAll("(\\d{1,2}):(?<minutes>\\d{2})", "$1 hours and ${minutes} minutes");
		// Sets result to â€ś3 hours and 45 minutesâ€ť

		int brk = 0;
	}

	// Patramtry wyszukiwania
	public static void REParameters() {
		String input = "BlAckwell Toaster model AC4 USD29.95";
		// ".*(AC).*\\s" - odda caĹ‚y string jako wynik
		// "\\w*(AC)\\w*" - odda pojedyncze sĹ‚owa
		// wiÄ™cej atrybutĂłw wyszukiwania w Pattern.java (CTRL+ML na CASE_INSENSITIVE)
		Pattern pattern6 = Pattern.compile("\\w*(AC)\\w*", Pattern.CASE_INSENSITIVE);
		Matcher matcher6 = pattern6.matcher(input);
		while (matcher6.find()) {
			System.out.print("'" + matcher6.toMatchResult().group(1) + ": " + matcher6.toMatchResult().start() + "-"
			    + matcher6.toMatchResult().end());
			// drugi argument subSequence pokazuje na pierwszy NIE NALEĹ»Ä„CY do podciagu
			System.out.println(" | '" + input.subSequence(matcher6.toMatchResult().start()/* pierwszy naleĹĽacy do grupy */,
			    matcher6.toMatchResult().end() /* pierwszy nienaleĹĽacy do grupy */).toString() + "'");
		}
	}

	// regexp w streamie - pattern uĹĽyty jako predicate dopasowywany jest do
	// kaĹĽdego elementu w streamie
	public static void REStream() {
		Stream<String> strings8 = Arrays.asList("ala", "ola", "bolo").stream();
		Pattern pattern8 = Pattern.compile(".*(l)(a).*");
		// pattern8.asPredicate() oddaje predykat stworzony na podstawie regexp'a
		Stream<String> result8 = strings8.filter(pattern8.asPredicate());
		result8.forEach(s -> System.out.println("8-> " + s));
	}
}
