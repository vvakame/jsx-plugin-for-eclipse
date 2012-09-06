package net.vvakame.jsx.peg;

import mouse.runtime.ParserBaseExtend;
import mouse.runtime.Source;

public class Debug {
	private Debug() {
	}

	public static String dump(JsxParser parser) {
		ParserBaseExtend extend = ParserBaseExtend.get(parser);
		final int pos = extend.getPos();
		Source source = extend.getSource();
		String current = source.at(0, pos);
		@SuppressWarnings("unused")
		String all = source.at(0, extend.getEndpos());

		System.out.println("current\n" + current);
		System.out.println("<---");

		return current;
	}
}
