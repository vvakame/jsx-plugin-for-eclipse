package net.vvakame.jsx.antlr;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

import net.vvakame.jsx.antlr.util.AntlrUtil;
import net.vvakame.jsx.antlr.util.AntlrUtil.AntlrData;

import org.antlr.runtime.RecognitionException;
import org.junit.Ignore;
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

	@Test
	@Ignore
	public void tryParseJsxTestCode() throws IOException, RecognitionException {
		File gitRoot = getGitRootDirectory();

		String[] jsxExistsDirPaths = { "JSX/t/run/" };

		for (String dirPath : jsxExistsDirPaths) {
			File dir = new File(gitRoot, dirPath);
			File[] jsxFiles = dir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File file, String name) {
					return name.endsWith(".jsx");
				}
			});

			for (File file : jsxFiles) {
				InputStream stream = getStream(file);

				String fileName = file.getAbsolutePath();
				System.out.println(fileName);
				assertParseSuccess(fileName, stream);
			}
		}
	}

	static InputStream getStream(String fileName) {
		InputStream stream = SyntaxTest.class.getResourceAsStream(fileName);
		return stream;
	}

	static InputStream getStream(File file) throws FileNotFoundException {
		FileInputStream stream = new FileInputStream(file);
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

	static File getGitRootDirectory() {
		final String antlrProjectName = "jsx-antlr-syntax";
		File gitRoot = new File("./").getAbsoluteFile();
		while (!antlrProjectName.equals(gitRoot.getName())) {
			gitRoot = gitRoot.getParentFile();
		}
		return gitRoot.getParentFile();
	}
}
