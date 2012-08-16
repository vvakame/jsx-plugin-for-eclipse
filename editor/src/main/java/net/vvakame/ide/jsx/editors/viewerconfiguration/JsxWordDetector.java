package net.vvakame.ide.jsx.editors.viewerconfiguration;

import org.eclipse.jface.text.rules.IWordDetector;

public class JsxWordDetector implements IWordDetector {

	@Override
	public boolean isWordStart(char c) {
		// FIXME this implementation was cloned from
		// Character.isJavaIdentifierStart
		System.out.println(c);
		if (Character.isLetter(c)) {
			return true;
		} else if (Character.getType(c) == Character.LETTER_NUMBER) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isWordPart(char c) {
		// FIXME this implementation was cloned from
		// Character.isJavaIdentifierPart
		if (Character.isLetter(c)) {
			return true;
		} else if (Character.getType(c) == Character.LETTER_NUMBER) {
			return true;
		} else if ('_' == c) {
			return true;
		}

		return false;
	}
}
