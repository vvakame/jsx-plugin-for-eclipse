package net.vvakame.ide.jsx.editors.viewerconfiguration;

import static net.vvakame.ide.jsx.editors.misc.IJsxToken.JSX_COMMENT;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;

public class BlockCommentScanner extends RuleBasedScanner {

	public BlockCommentScanner() {

		List<IRule> list = new ArrayList<IRule>();

		IToken token = new Token(JSX_COMMENT);
		list.add(new MultiLineRule("/*", "*/", token));

		setRules(list.toArray(new IRule[] {}));
	}
}
