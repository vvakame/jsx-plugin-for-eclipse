package net.vvakame.ide.jsx.editors;

import org.eclipse.osgi.util.NLS;

public final class JsxEditorMessages extends NLS {

	private static final String BUNDLE_NAME = JsxEditorMessages.class
			.getSimpleName();

	static {
		NLS.initializeMessages(BUNDLE_NAME, JsxEditorMessages.class);
	}

	private JsxEditorMessages() {
	}

	public static String Test_test;
}
