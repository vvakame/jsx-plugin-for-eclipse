package net.vvakame.jsx.antlr.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import net.vvakame.jsx.antlr.JSXLexer;
import net.vvakame.jsx.antlr.JSXParser;
import net.vvakame.jsx.antlr.JSXParser.programFile_return;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;

public class AntlrUtil {

	public static AntlrData parse(String source) throws IOException,
			RecognitionException {
		InputStream stream = AntlrUtil.class.getResourceAsStream(source);
		return parseStream(stream);
	}

	public static AntlrData parseStream(InputStream stream) throws IOException,
			RecognitionException {
		JSXLexer lexer = new JSXLexer();

		lexer.setCharStream(new ANTLRInputStream(stream));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		JSXParser parser = new JSXParser(tokens);

		tokens.LT(1);
		programFile_return programFileReturn = parser.programFile();

		Tree t = (Tree) programFileReturn.getTree();

		return new AntlrData(lexer, tokens, parser, programFileReturn, t);
	}

	public static class AntlrData {
		public final JSXLexer lexer;
		public final CommonTokenStream tokens;
		public final JSXParser parser;
		public final programFile_return programFileReturn;
		public final Tree t;

		public AntlrData(JSXLexer lexer, CommonTokenStream tokens,
				JSXParser parser, programFile_return programFileReturn, Tree t) {
			super();
			this.lexer = lexer;
			this.tokens = tokens;
			this.parser = parser;
			this.programFileReturn = programFileReturn;
			this.t = t;
		}
	}

	public static void printTree(Tree t) {
		printTree(t, 1);
	}

	static void printTree(Tree t, int depth) {
		for (int i = 0; i < t.getChildCount(); i++) {
			Tree tree = t.getChild(i);
			String str = String.format("L%04d:C%03d depth_%02d %-20s Token:%s",
					tree.getLine(), tree.getCharPositionInLine(), depth,
					tree.getText(), typeToToken(tree.getType()));
			System.out.println(str);
			printTree(tree, depth + 1);
		}
	}

	static String typeToToken(int type) {
		for (Field field : JSXLexer.class.getFields()) {
			try {
				if (type == field.getInt(null)) {
					return field.getName();
				}
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}

		return "NotExists";
	}
}
