package net.vvakame.ide.jsx.editors.viewerconfiguration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;

import static net.vvakame.ide.jsx.editors.misc.IJsxToken.*;

/**
 * Scanner for block comment (not jsxdoc).
 * @author vvakame
 */
public class BlockCommentScanner extends RuleBasedScanner {

	/**
	 * the constructor.
	 * @category constructor
	 */
	public BlockCommentScanner() {

		List<IRule> list = new ArrayList<IRule>();

		IToken token = new Token(JSX_COMMENT);
		list.add(new MultiLineRule("/*", "*/", token));

		setRules(list.toArray(new IRule[] {}));
	}
}
