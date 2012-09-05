package net.vvakame.jsx.peg;

import mouse.runtime.Source;
import mouse.runtime.SourceString;
import net.vvakame.jsx.peg.util.PegUtil;
import net.vvakame.peg.JsxParser;

class Main {

	public static void main(String[] args) {
		JsxParser parser = new JsxParser();
		Source src = new SourceString("import \"hoge\";\nimport \"fuga\";");
		parser.parse(src);

		PegUtil.walk(parser.semantics());
	}
}
