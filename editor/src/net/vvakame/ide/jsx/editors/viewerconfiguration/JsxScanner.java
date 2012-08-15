package net.vvakame.ide.jsx.editors.viewerconfiguration;

import static net.vvakame.ide.jsx.editors.misc.IJsxToken.JSX_KEYWORD;

import java.util.ArrayList;
import java.util.List;

import net.vvakame.ide.jsx.editors.misc.ColorManager;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.NumberRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;

class JsxScanner extends RuleBasedScanner {

	public JsxScanner(ColorManager manager) {
		IToken keyword = new Token(JSX_KEYWORD);

		List<IRule> list = new ArrayList<IRule>();

		// FIXME
		IWordDetector detector = new IWordDetector() {

			@Override
			public boolean isWordStart(char c) {
				return c == 'c';
			}

			@Override
			public boolean isWordPart(char c) {
				System.out.println(c);
				return "class".contains(new String(new char[] { c }));
			}
		};
		WordRule wordRule = new WordRule(detector, keyword, false);
		wordRule.addWord("class", keyword);
		list.add(wordRule);

		list.add(new NumberRule(keyword));

		setRules(list.toArray(new IRule[] {}));
	}
}
