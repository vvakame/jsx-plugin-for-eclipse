package net.vvakame.ide.jsx.editors;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class JsxEditorMessagesTest {

	@Test
	public void test() {
		assertThat(JsxEditorMessages.Test_test, is("test!!!"));
	}
}
