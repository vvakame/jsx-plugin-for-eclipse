package net.vvakame.jsx.antlr;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import net.vvakame.jsx.antlr.util.AntlrUtil;
import net.vvakame.jsx.antlr.util.AntlrUtil.AntlrData;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;

public class SyntaxTest {

	@Test
	public void valid() throws IOException, RecognitionException {
		for (int i = 1;; i++) {
			String fileName = String.format("/jsx/valid/%03d.jsx", i);
			InputStream stream = getStream(fileName);
			if (stream == null) {
				break;
			}

			System.out.println(fileName);
			assertParseSuccess(fileName, stream);
		}
	}

	@Test
	public void invalid() throws IOException, RecognitionException {
		for (int i = 1;; i++) {
			String fileName = String.format("/jsx/invalid/%03d.jsx", i);
			InputStream stream = getStream(fileName);
			if (stream == null) {
				break;
			}

			System.out.println(fileName);
			assertParseFailure(fileName, stream);
		}
	}

	static InputStream getStream(String fileName) {
		InputStream stream = SyntaxTest.class.getResourceAsStream(fileName);
		return stream;
	}

	static void assertParseSuccess(String fileName, InputStream stream)
			throws IOException, RecognitionException {
		AntlrData result = AntlrUtil.parseStream(stream);
		assertThat(fileName + " expected valid syntax.",
				result.parser.getNumberOfSyntaxErrors(), is(0));
	}

	static void assertParseFailure(String fileName, InputStream stream)
			throws IOException, RecognitionException {
		AntlrData result = AntlrUtil.parseStream(stream);
		assertThat(fileName + " expected invalid syntax.",
				result.parser.getNumberOfSyntaxErrors(), is(not(0)));
	}
}
