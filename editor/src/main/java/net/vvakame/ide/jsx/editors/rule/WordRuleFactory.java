package net.vvakame.ide.jsx.editors.rule;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.WordRule;

public class WordRuleFactory {

	public static IRule createRule(final String word, final IToken token) {

		IWordDetector detector = new IWordDetector() {

			@Override
			public boolean isWordStart(char c) {
				return word.charAt(0) == c;
			}

			@Override
			public boolean isWordPart(char c) {
				return word.substring(1).contains(new String(new char[] { c }));
			}
		};
		WordRule wordRule = new WordRule(detector);
		wordRule.addWord(word, token);

		return wordRule;
	}
}
