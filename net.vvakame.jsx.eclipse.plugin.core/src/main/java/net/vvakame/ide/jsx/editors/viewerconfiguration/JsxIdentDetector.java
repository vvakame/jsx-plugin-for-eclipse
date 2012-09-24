package net.vvakame.ide.jsx.editors.viewerconfiguration;

import org.eclipse.jface.text.rules.IWordDetector;

/**
 * JSX identifier detector. JSX identifier is matched "[a-zA-Z_][a-zA-Z0-9_]*".
 * 
 * @author vvakame
 */
public class JsxIdentDetector implements IWordDetector {

	@Override
	public boolean isWordStart(char c) {
		if ('a' <= c && c <= 'z') {
			return true;
		} else if ('A' <= c && c <= 'Z') {
			return true;
		} else if ('_' == c) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isWordPart(char c) {
		if ('a' <= c && c <= 'z') {
			return true;
		} else if ('A' <= c && c <= 'Z') {
			return true;
		} else if ('_' == c) {
			return true;
		} else if ('0' <= c && c <= '9') {
			return true;
		}

		return false;
	}
}
