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

	static final String[] KEYWORDS = { "class", "function" };

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
		WordRule wordRule = new WordRule(new JsxWordDetector());
		for (int i = 0; i < KEYWORDS.length; i++) {
			wordRule.addWord(KEYWORDS[i], keyword);
		}
		rules.add(wordRule);

		setRules(rules.toArray(new IRule[] {}));
	}
}
