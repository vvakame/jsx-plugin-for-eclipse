package net.vvakame.ide.jsx.editors.rule;

import net.vvakame.ide.jsx.editors.misc.IJsxToken;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class KeywordClassRule implements IRule {

	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		StringBuilder builder = new StringBuilder();
		builder.append((char) scanner.read());
		builder.append((char) scanner.read());
		builder.append((char) scanner.read());
		builder.append((char) scanner.read());
		builder.append((char) scanner.read());

		if ("class".equals(builder.toString())) {
			return new Token(IJsxToken.JSX_KEYWORD);
		} else {

			scanner.unread();
			scanner.unread();
			scanner.unread();
			scanner.unread();
			scanner.unread();

			return Token.UNDEFINED;
		}
	}
}
