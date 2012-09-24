package net.vvakame.ide.jsx.editors.jsxprovider;

import static net.vvakame.ide.jsx.editors.misc.IJsxToken.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

class JsxPartitionScanner extends RuleBasedPartitionScanner {

	public JsxPartitionScanner() {

		List<IPredicateRule> list = new ArrayList<IPredicateRule>();

		IToken comment = new Token(JSX_COMMENT);
		list.add(new MultiLineRule("/*", "*/", comment));

		setPredicateRules(list.toArray(new IPredicateRule[] {}));
	}
}
