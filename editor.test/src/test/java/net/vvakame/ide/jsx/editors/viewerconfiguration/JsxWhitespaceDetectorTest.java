package net.vvakame.ide.jsx.editors.viewerconfiguration;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Test for {@link JsxWhitespaceDetector}.
 * @author vvakame
 */
public class JsxWhitespaceDetectorTest {

	final Object tokenObj = new Object();


	/**
	 * test.
	 * @author vvakame
	 */
	@Test
	public void test() {
		String[] valid = {
			" ",
			"\t",
			"\n",
			"\r"
		};

		for (String word : valid) {
			WhitespaceRule rule =
					new WhitespaceRule(new JsxWhitespaceDetector(), new Token(tokenObj));

			RuleBasedScanner scanner = new RuleBasedScanner();
			scanner.setRules(new IRule[] {
				rule
			});
			scanner.setRange(new Document(word), 0, word.length());

			IToken token = scanner.nextToken();
			assertThat(word + " is valid.", token.getData(), is(tokenObj));
		}

		String[] invalid = {
			"",
			"　"
		};

		for (String word : invalid) {
			WhitespaceRule rule =
					new WhitespaceRule(new JsxWhitespaceDetector(), new Token(tokenObj));

			RuleBasedScanner scanner = new RuleBasedScanner();
			scanner.setRules(new IRule[] {
				rule
			});
			scanner.setRange(new Document(word), 0, word.length());

			IToken token = scanner.nextToken();
			assertThat(word + " is invalid.", token.getData(), nullValue());
		}
	}
}
