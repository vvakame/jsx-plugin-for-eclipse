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

	// デバッグのヒント。
	// mvn clean compile でJsxParserが生成される。
	// JsxParserの中で Debug.dump(this) とかやると現在どこまで食ってるかわかる。
	// mouse.runtime.ParserBaseExtend.get(this).getPos()
	// で現在見てる(食ってないものも含む)位置が見える
	// 実行中にデバッガで止めてEclipseのDisplayビューを上手に活用すること。

	@Test
	public void valid() throws IOException {
		List<String> ignoreFiles = Arrays.asList(new String[] {});

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

		List<String> ignoreFiles = Arrays.asList(new String[] {});

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

	@Test
	public void test() throws IOException {
		File gitRoot = getGitRootDirectory();
		File file = new File(gitRoot, "JSX/t/run/034.as.jsx");

		InputStream stream = getStream(file);
		assertParseSuccess(file.getName(), stream);
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

		{
			SyntaxTreeMouseImpl syntaxTree = parser.semantics()
					.getSyntaxTreeWithCompress();
			String reconstructSource = Debug.replayText(syntaxTree);

			/*
			System.out.println("----- original");
			System.out.println(src.src);
			System.out.println("----- generated");
			System.out.println(reconstructSource);
			System.out.println("-----");
			 */

			assertReconstructSource(fileName, src.src, reconstructSource);
		}
	}

	static void assertParseFailure(String fileName, InputStream stream)
			throws IOException {
		SourceStream src = new SourceStream(stream);

		JsxParser parser = new JsxParser();
		boolean result = parser.parse(src);
		assertThat(fileName + " is invalid", result, is(false));
	}

	static void assertReconstructSource(String fileName, String originalSource,
			String reconstructSource) {
		String[] originalLines = originalSource.split("(\r\n|\r|\n)");
		String[] reconstructLines = reconstructSource.split("(\r\n|\r|\n)");

		for (int i = 0; i < originalLines.length; i++) {
			assertThat(fileName + " L" + (i + 1), reconstructLines[i],
					is(originalLines[i]));
		}
		assertThat(fileName + " source re-construct", reconstructSource,
				equalTo(originalSource));
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
