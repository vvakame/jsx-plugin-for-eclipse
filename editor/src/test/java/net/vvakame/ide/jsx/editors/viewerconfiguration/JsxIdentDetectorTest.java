package net.vvakame.ide.jsx.editors.viewerconfiguration;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.junit.Test;

public class JsxIdentDetectorTest {
	final Object tokenObj = new Object();

	@Test
	public void test() {
		String[] valid = { "class", "extends", "vvakame_", "_u1", "VVAKAME",
				"U1ARYZ" };

		for (String word : valid) {
			WordRule rule = new WordRule(new JsxIdentDetector());
			rule.addWord(word, new Token(tokenObj));

			RuleBasedScanner scanner = new RuleBasedScanner();
			scanner.setRules(new IRule[] { rule });
			scanner.setRange(new Document(word), 0, word.length());

			IToken token = scanner.nextToken();
			assertThat(word + " is valid.", token.getData(), is(tokenObj));
		}

		String[] invalid = { "fizzばず", "ほげ", "日本全国酒飲み音頭", "(´∀｀∩)↑age↑", "110",
				" ", "　", "أعلنت قوات التحالف الشمالي" };

		for (String word : invalid) {
			WordRule rule = new WordRule(new JsxIdentDetector());
			rule.addWord(word, new Token(tokenObj));

			RuleBasedScanner scanner = new RuleBasedScanner();
			scanner.setRules(new IRule[] { rule });
			scanner.setRange(new Document(word), 0, word.length());

			IToken token = scanner.nextToken();
			assertThat(word + " is invalid.", token.getData(), nullValue());
		}
	}
}
