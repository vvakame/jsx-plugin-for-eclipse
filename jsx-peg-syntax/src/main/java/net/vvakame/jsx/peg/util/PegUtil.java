package net.vvakame.jsx.peg.util;

import mouse.runtime.ParserBase;
import mouse.runtime.ParserBase.Phrase;

public class PegUtil {
	public static void walk(ParserBase parser) {
		Phrase lhs = parser.lhs();
		walk(lhs);

		for (int i = 0; i < parser.rhsSize(); i++) {
			Phrase rhs = parser.rhs(i);
			walk(rhs);

			String text = parser.rhsText(0, 1);
			System.out.println("test:" + text);
		}
	}

	static void walk(Phrase phrase) {
		String rule = phrase.rule();
		String text = phrase.text();

		String str = String.format("%s:%s", rule, text);
		System.out.println(str);
	}
}
