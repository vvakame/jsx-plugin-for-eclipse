package net.vvakame.jsx.peg.util;

import mouse.runtime.CurrentRule;
import mouse.runtime.ParserBase;
import mouse.runtime.Phrase;
import mouse.runtime.SemanticsBase;

public class PegUtil {
	public static void walk(ParserBase parser) {
		Phrase lhs = parser.lhs();
		walk("lhs", lhs, 1);

		System.out.println("rhs=" + parser.rhsSize());
		for (int i = 0; i < parser.rhsSize(); i++) {
			Phrase rhs = parser.rhs(i);
			walk("rhs", rhs, 2);

			String text = parser.rhsText(0, 1);
			System.out.println("test:" + text);
		}
	}

	public static void walk(Phrase phrase) {
		String rule = phrase.rule();
		String text = phrase.text();

		String str = String.format("%s:%s", rule, text);
		System.out.println(str);
	}

	static void walk(String prefix, Phrase phrase, int depth) {
		String rule = phrase.rule();
		String text = phrase.text();

		String str = String.format("%s %s:%s", prefix, rule, text);
		System.out.println(str);
	}

	public static void walk(SemanticsBase semantics) {
		Phrase lhs = semantics.rule.lhs();
		walk("lhs", lhs, 1);

		CurrentRule rule = semantics.rule;
		System.out.println("rhs=" + rule.rhsSize());
		for (int i = 0; i < rule.rhsSize(); i++) {
			Phrase rhs = rule.rhs(i);
			walk("rhs", rhs, 2);

			String text = rule.rhsText(0, 1);
			System.out.println("test:" + text);
		}
	}
}
