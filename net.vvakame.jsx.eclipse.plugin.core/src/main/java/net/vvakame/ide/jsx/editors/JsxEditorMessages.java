package net.vvakame.ide.jsx.editors;

import org.eclipse.osgi.util.NLS;

/**
 * NLS module.
 * @author vvakame
 */
public final class JsxEditorMessages extends NLS {

	private static final String BUNDLE_NAME = JsxEditorMessages.class.getSimpleName();

	static {
		NLS.initializeMessages(BUNDLE_NAME, JsxEditorMessages.class);
	}


	private JsxEditorMessages() {
	}


	@SuppressWarnings("javadoc")
	public static String Test_test;

	@SuppressWarnings("javadoc")
	public static String Preference_jsxPath;

	@SuppressWarnings("javadoc")
	public static String Preference_nodejsPath;
}
