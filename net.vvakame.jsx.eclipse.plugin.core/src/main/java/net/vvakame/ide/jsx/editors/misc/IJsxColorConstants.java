package net.vvakame.ide.jsx.editors.misc;

import org.eclipse.swt.graphics.RGB;

/**
 * Constants for Color.
 * @author vvakame
 */
public interface IJsxColorConstants {

	/** default */
	RGB DEFAULT = new RGB(0, 0, 0);

	/** string token */
	RGB STRING = new RGB(42, 0, 255);

	/** keyword token */
	RGB KEYWORD = new RGB(127, 0, 85);

	/** comment token */
	RGB COMMENT = new RGB(63, 95, 191);
}
