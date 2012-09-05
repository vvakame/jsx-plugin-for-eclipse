package net.vvakame.jsx.peg;

import mouse.runtime.Source;
import mouse.runtime.SourceString;
import net.vvakame.jsx.peg.util.PegUtil;
import net.vvakame.peg.JsxParser;
import net.vvakame.peg.JsxSemantics;
import net.vvakame.peg.SyntaxTreeMouseImpl;

class Main {

	public static void main(String[] args) {
		JsxParser parser = new JsxParser();
		Source src = new SourceString("import \"hoge\";\nimport \"fuga\";");
		parser.parse(src);
		System.out.println("parsing finish.");

		JsxSemantics semantics = parser.semantics();
		SyntaxTreeMouseImpl tree = semantics.getSyntaxTree();
		System.out.println(tree);
		System.out.println("syntax tree.");

		System.out.println("walk tree.");
		PegUtil.walk(parser.semantics());
	}
}
