package net.vvakame.ide.jsx.editors;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Test for {@link JsxEditorMessages}.
 * @author vvakame
 */
public class JsxEditorMessagesTest {

	/**
	 * test.
	 * @author vvakame
	 */
	@Test
	public void test() {
		assertThat(JsxEditorMessages.Test_test, is("test!!!"));
	}
}
