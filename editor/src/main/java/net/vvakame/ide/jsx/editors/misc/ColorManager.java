package net.vvakame.ide.jsx.editors.misc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class ColorManager {

	protected Map<RGB, Color> colorTable = new HashMap<RGB, Color>();
	private Map<String, Token> tokenTable = new HashMap<String, Token>();

	private Map<String, RGB> rgbTable = new HashMap<String, RGB>();

	public ColorManager() {
		rgbTable.put(IJsxToken.JSX_DEFAULT, IJsxColorConstants.DEFAULT);
		rgbTable.put(IJsxToken.JSX_KEYWORD, IJsxColorConstants.KEYWORD);
		rgbTable.put(IJsxToken.JSX_COMMENT, IJsxColorConstants.COMMENT);
		rgbTable.put(IJsxToken.JSX_STRING, IJsxColorConstants.STRING);
	}

	public void dispose() {
		Iterator<Color> e = colorTable.values().iterator();
		while (e.hasNext()) {
			e.next().dispose();
		}
	}

	public IToken getToken(String prefKey) {
		Token token = tokenTable.get(prefKey);
		if (token == null) {
			RGB rgb = rgbTable.get(prefKey);

			if (prefKey.equals(IJsxToken.JSX_KEYWORD)) {
				token = new Token(new TextAttribute(getColor(rgb), null,
						SWT.BOLD));
			} else {
				token = new Token(new TextAttribute(getColor(rgb)));
			}
			tokenTable.put(prefKey, token);
		}
		return token;
	}

	public Color getColor(RGB rgb) {
		Color color = colorTable.get(rgb);
		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			colorTable.put(rgb, color);
		}
		return color;
	}
}
