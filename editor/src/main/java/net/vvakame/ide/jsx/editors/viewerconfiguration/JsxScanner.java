package net.vvakame.ide.jsx.editors.viewerconfiguration;

import static net.vvakame.ide.jsx.editors.misc.IJsxToken.*;

import java.util.ArrayList;
import java.util.List;

import net.vvakame.ide.jsx.editors.misc.ColorManager;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.WordRule;

class JsxScanner extends RuleBasedScanner {

	// from here.
	// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L132
	static final String[] KEYWORDS = {
			// literals shared with ECMA 262
			"null", "true", "false",
			"NaN",
			"Infinity",
			// keywords shared with ECMA 262
			"break", "do", "instanceof", "typeof", "case", "else", "new",
			"var", "catch", "finally", "return", "void", "continue", "for",
			"switch", "while", "function", "this",
			/* "default", */// contextual keywords
			"if", "throw",
			/* "assert", "log", // contextual keywords */
			"delete", "in", "try",
			// keywords of JSX
			"class", "extends", "super", "import", "implements", "interface",
			"static", "__FILE__", "__LINE__", "undefined" };

	// from here.
	// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L153
	static final String[] RESERVED = {
			// literals of ECMA 262 but not used by JSX
			"debugger", "with",
			// future reserved words of ECMA 262
			"const", "export",
			// future reserved words within strict mode of ECMA 262
			"let", "private", "public", "yield", "protected",

			// JSX specific reserved words
			"extern", "native", "as", "operator" };

	public JsxScanner(ColorManager manager) {
		final IToken keyword = manager.getToken(JSX_KEYWORD);

		final IToken string = manager.getToken(JSX_STRING);
		final IToken comment = manager.getToken(JSX_COMMENT);

		List<IRule> rules = new ArrayList<IRule>();

		// String
		rules.add(new SingleLineRule("\"", "\"", string, '\\'));
		rules.add(new SingleLineRule("'", "'", string, '\\'));

		// comment
		rules.add(new EndOfLineRule("//", comment));

		// keyword
		WordRule wordRule = new WordRule(new JsxIdentDetector());
		for (String word : KEYWORDS) {
			wordRule.addWord(word, keyword);
		}
		for (String word : RESERVED) {
			wordRule.addWord(word, keyword);
		}
		rules.add(wordRule);

		setRules(rules.toArray(new IRule[] {}));
	}
}
