package net.vvakame.ide.jsx.editors.viewerconfiguration;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import net.vvakame.ide.jsx.editors.misc.ColorManager;
import net.vvakame.ide.jsx.editors.misc.IJsxToken;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.junit.Test;

public class JsxScannerTest {

	ColorManager manager = new ColorManager();

	// TODO implement more effective test

	@Test
	public void test() {
		JsxScanner scanner = new JsxScanner(manager);
		final IToken defaultToken = new Token("default");
		scanner.setDefaultReturnToken(defaultToken);

		String documentString = "\"\"; class {}";

		scanner.setRange(new Document(documentString), 0,
				documentString.length());

		IToken token;

		// ""
		token = scanner.nextToken();
		assertThat(token, is(manager.getToken(IJsxToken.JSX_STRING)));

		// ;
		token = scanner.nextToken();
		assertThat(token, is(defaultToken));

		// space
		token = scanner.nextToken();
		assertThat(token, is(Token.WHITESPACE));

		// class
		token = scanner.nextToken();
		assertThat(token, is(manager.getToken(IJsxToken.JSX_KEYWORD)));

		// space
		token = scanner.nextToken();
		assertThat(token, is(Token.WHITESPACE));

		// {
		token = scanner.nextToken();
		assertThat(token, is(defaultToken));

		// }
		token = scanner.nextToken();
		assertThat(token, is(defaultToken));

		// EOF
		token = scanner.nextToken();
		assertThat(token, is(Token.EOF));
	}
}
