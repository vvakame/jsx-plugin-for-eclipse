package net.vvakame.ide.jsx.editors.jsxprovider;

import org.eclipse.jface.text.rules.*;
import static net.vvakame.ide.jsx.editors.misc.IXMLTagToken.*;

class XMLPartitionScanner extends RuleBasedPartitionScanner {

	public XMLPartitionScanner() {

		IToken xmlComment = new Token(XML_COMMENT);
		IToken tag = new Token(XML_TAG);

		IPredicateRule[] rules = new IPredicateRule[2];

		rules[0] = new MultiLineRule("<!--", "-->", xmlComment);
		rules[1] = new TagRule(tag);

		setPredicateRules(rules);
	}
}
