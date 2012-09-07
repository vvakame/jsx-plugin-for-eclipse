package net.vvakame.jsx.peg;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SyntaxTest {

	// TODO turn memoize on. at pom.xml

	// デバッグのヒント。
	// mvn clean compile でJsxParserが生成される。
	// JsxParserの中で Debug.dump(this) とかやると現在どこまで食ってるかわかる。
	// mouse.runtime.ParserBaseExtend.get(this).getPos()
	// で現在見てる(食ってないものも含む)位置が見える
	// 実行中にデバッガで止めてEclipseのDisplayビューを上手に活用すること。

	@Test
	public void valid() throws IOException {
		List<String> ignoreFiles = Arrays
				.asList(new String[] { "/jsx/valid/003.jsx", });

		for (int i = 1;; i++) {
			final String fileName = String.format("/jsx/valid/%03d.jsx", i);
			if (ignoreFiles.contains(fileName)) {
				continue;
			}
			InputStream stream = getStream(fileName);
			if (stream == null) {
				break;
			}

			System.out.println(fileName);
			assertParseSuccess(fileName, stream);
		}
	}

	@Test
	public void invalid() throws IOException {
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
	public void tryParseJsxTestCode() throws IOException {
		File gitRoot = getGitRootDirectory();

		String[] jsxExistsDirPaths = { "JSX/t/run/", "JSX/t/lib/",
				"JSX/t/optimize/", "JSX/t/source-map/" };

		List<String> ignoreFiles = Arrays.asList(new String[] {
				// FIXME "as" was consume at
				// "primaryExpr -> ident objectTypeDeclaration"
				// please resolve SyntaxTest#valid case "/jsx/valid/003.jsx".
				"run/036.variant.jsx", "051.call-variant.jsx",
				"run/055.downcast.jsx", "run/057.downcast-interface.jsx",
				"run/107.try-catch-finally.jsx",
				"run/109.nested-caught-variables.jsx",
				"run/189.as_noconvert-to-nullable.jsx",
				"run/190.as_noconvert-exception.jsx", "run/204.array-map.jsx",
				"lib/002.timer.jsx",

		});

		for (String dirPath : jsxExistsDirPaths) {
			File dir = new File(gitRoot, dirPath);
			File[] jsxFiles = dir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File file, String name) {
					return name.endsWith(".jsx");
				}
			});

			TEST: for (File file : jsxFiles) {
				InputStream stream = getStream(file);

				String fileName = file.getAbsolutePath();
				for (String ignoreFile : ignoreFiles) {
					if (file.getAbsolutePath().endsWith(ignoreFile)) {
						continue TEST;
					}
				}
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
			throws IOException {
		SourceStream src = new SourceStream(stream);

		JsxParser parser = new JsxParser();
		boolean result = parser.parse(src);
		assertThat(fileName + " is valid", result, is(true));
	}

	static void assertParseFailure(String fileName, InputStream stream)
			throws IOException {
		SourceStream src = new SourceStream(stream);

		JsxParser parser = new JsxParser();
		boolean result = parser.parse(src);
		assertThat(fileName + " is invalid", result, is(false));
	}

	static File getGitRootDirectory() {
		final String pegProjectName = "jsx-peg-syntax";
		File gitRoot = new File("./").getAbsoluteFile();
		while (!pegProjectName.equals(gitRoot.getName())) {
			gitRoot = gitRoot.getParentFile();
		}
		return gitRoot.getParentFile();
	}
}
