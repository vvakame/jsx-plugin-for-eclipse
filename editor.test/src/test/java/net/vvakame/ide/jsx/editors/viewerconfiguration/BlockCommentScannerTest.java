package net.vvakame.ide.jsx.editors.viewerconfiguration;

import net.vvakame.ide.jsx.editors.misc.IJsxToken;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.rules.IToken;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Test for {@link BlockCommentScanner}.
 * @author vvakame
 */
public class BlockCommentScannerTest {

	/**
	 * test.
	 * @author vvakame
	 */
	@Test
	public void test() {
		String[] valid = {
			"/**/",
			"/** */",
			"/*\n\n\n*/",
			"/* hello world! */"
		};

		for (String word : valid) {
			BlockCommentScanner scanner = new BlockCommentScanner();

			scanner.setRange(new Document(word), 0, word.length());

			IToken token = scanner.nextToken();
			assertThat(word + " is valid.", (String) token.getData(), is(IJsxToken.JSX_COMMENT));
		}

		String[] invalid = {
			"//",
			"/a* */"
		};

		for (String word : invalid) {
			BlockCommentScanner scanner = new BlockCommentScanner();

			scanner.setRange(new Document(word), 0, word.length());

			IToken token = scanner.nextToken();
			assertThat(word + " is invalid.", token.getData(), nullValue());
		}
	}
}
