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

	@Test
	public void test() {
		String document = "class";

		WordRule rule = new WordRule(new JsxIdentDetector());
		rule.addWord("class", new Token(this));

		RuleBasedScanner scanner = new RuleBasedScanner();
		scanner.setRules(new IRule[] { rule });
		scanner.setRange(new Document(document), 0, document.length());

		IToken token = scanner.nextToken();
		assertThat((JsxIdentDetectorTest) token.getData(), is(this));
	}
}
