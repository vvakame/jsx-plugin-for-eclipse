package net.vvakame.jsx.peg;

import mouse.runtime.ParserBase.Phrase;
import mouse.runtime.Source;
import mouse.runtime.SourceString;
import net.vvakame.peg.JsxParser;

class Main {

	public static void main(String[] args) {
		JsxParser parser = new JsxParser();
		Source src = new SourceString("-1 + 1 + 2");
		boolean result = parser.parse(src);
		System.out.println(result);
		
		Phrase lhs = parser.lhs();
		System.out.println(lhs.text());
	}
}
