package net.vvakame.jsx.antlr;

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

import net.vvakame.jsx.antlr.JSXParser.programFile_return;
import net.vvakame.jsx.antlr.util.AntlrUtil;
import net.vvakame.jsx.antlr.util.AntlrUtil.AntlrData;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
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
	public void tryParseJsxTestCode() throws IOException, RecognitionException {
		File gitRoot = getGitRootDirectory();

		String[] jsxExistsDirPaths = { "JSX/t/run/", "JSX/t/lib/",
				"JSX/t/optimize/", "JSX/t/source-map/" };

		List<String> ignoreFiles = Arrays.asList(new String[] {
				// FIXME unknown???
				"run/078.bitnot.jsx", "run/137.same-pred-op-with-parens.jsx",
				"lib/005.builtins.jsx",
				"lib/009.console.jsx",
				"lib/010.web.jsx",
				"optimize/011.fold-const-propagate-number.jsx",
				"optimize/012.fold-const-propagate-int.jsx",

				// FIXME too difficult... can't parse ">>" to ">" ">"
				"run/175.T-of-ArrayT-should-never-be-maybeundef.jsx",
				"run/181.issue48.jsx", "run/184.implements-template.jsx",
				"run/198.template-as-param.jsx", });

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
	public void tryWalk() throws IOException, RecognitionException {
		String fileName = "/jsx/valid/013.jsx";
		InputStream stream = getStream(fileName);

		JSXLexer lexer = new JSXLexer();

		lexer.setCharStream(new ANTLRInputStream(stream));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		JSXParser parser = new JSXParser(tokens);

		tokens.LT(1);
		programFile_return programFileReturn = parser.programFile();

		Tree t = (Tree) programFileReturn.getTree();

		AntlrUtil.printTree(t);
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
