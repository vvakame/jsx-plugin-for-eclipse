package net.vvakame.ide.jsx.editors.viewerconfiguration;

import static net.vvakame.ide.jsx.editors.misc.IJsxToken.JSX_KEYWORD;

import java.util.ArrayList;
import java.util.List;

import net.vvakame.ide.jsx.editors.misc.ColorManager;
import net.vvakame.ide.jsx.editors.rule.KeywordClassRule;
import net.vvakame.ide.jsx.editors.rule.WordRuleFactory;

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

		list.add(new KeywordClassRule());
		list.add(new NumberRule(keyword));

		IWordDetector detector = new IWordDetector() {

			@Override
			public boolean isWordStart(char c) {
				return 'f' == c;
			}

			@Override
			public boolean isWordPart(char c) {
				return "unction".contains(new String(new char[] { c }));
			}
		};
		WordRule wordRule = new WordRule(detector);
		wordRule.addWord("function", keyword);
		list.add(wordRule);

		list.add(WordRuleFactory.createRule("static", keyword));
		list.add(WordRuleFactory.createRule("new", keyword));
		list.add(WordRuleFactory.createRule("var", keyword));

		setRules(list.toArray(new IRule[] {}));
	}
}
