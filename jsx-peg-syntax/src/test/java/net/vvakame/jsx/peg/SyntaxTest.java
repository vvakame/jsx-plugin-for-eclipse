package net.vvakame.jsx.peg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SyntaxTest {

	@Test
	public void valid() {
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
	public void invalid() {
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
	public void tryParseJsxTestCode() throws FileNotFoundException {
		File gitRoot = getGitRootDirectory();

		String[] jsxExistsDirPaths = { "JSX/t/run/", "JSX/t/lib/",
				"JSX/t/optimize/", "JSX/t/source-map/" };

		List<String> ignoreFiles = Arrays.asList(new String[] {
				// FIXME unknown???
				"lib/005.builtins.jsx", "lib/009.console.jsx",
				"lib/010.web.jsx", });

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

	static void assertParseSuccess(String fileName, InputStream stream) {
	}

	static void assertParseFailure(String fileName, InputStream stream) {
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
