package net.vvakame.ide.jsx.editors.misc;

import java.lang.reflect.Field;

import org.eclipse.jface.text.rules.IToken;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Test for {@link ColorManager}.
 * @author vvakame
 */
public class ColorManagerTest {

	/**
	 * test.
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @author vvakame
	 */
	@Test
	public void getToken() throws IllegalArgumentException, IllegalAccessException {
		ColorManager manager = new ColorManager();

		for (Field field : IJsxToken.class.getFields()) {
			if (!String.class.equals(field.getType())) {
				continue;
			}

			String value = (String) field.get(null);
			IToken token = manager.getToken(value);

			assertThat("token was declared", token, notNullValue());
		}
	}

	/**
	 * test.
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @author vvakame
	 */
	@Test
	public void getColor() throws IllegalArgumentException, IllegalAccessException {
		ColorManager manager = new ColorManager();

		for (Field field : IJsxColorConstants.class.getFields()) {
			if (!RGB.class.equals(field.getType())) {
				continue;
			}

			RGB value = (RGB) field.get(null);
			Color color = manager.getColor(value);

			assertThat("color was declared", color, notNullValue());
		}
	}
}
