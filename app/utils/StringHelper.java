package utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * Created by jesu on 08/06/17.
 */
public class StringHelper {

	public static String toParagraph(String... lines) {
		String paragraph = "";
		if ( lines != null )
			paragraph = toParagraph(Arrays.stream(lines));
		return paragraph;
	}

	public static String toParagraph(Collection<String> lines) {
		String paragraph = "";
		if ( lines != null )
			paragraph = toParagraph(lines.stream());
		return paragraph;
	}

	public static String toParagraph(Stream<String> lines) {
		String paragraph = "";
		if ( lines != null )
			paragraph = lines.reduce( (l1, l2) -> new StringBuilder(l1).append("\n").append(l2).toString() ).orElse("");
		return paragraph;
	}

}
