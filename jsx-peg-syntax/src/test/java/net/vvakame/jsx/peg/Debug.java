package net.vvakame.jsx.peg;

import java.util.ArrayList;
import java.util.List;

import mouse.runtime.ParserBaseExtend;
import mouse.runtime.PhraseExtend;
import mouse.runtime.Source;
import net.vvakame.ide.jsx.parser.SyntaxTree;
import net.vvakame.jsx.peg.JsxSemantics.Tree;

/**
 * Debugging tools.
 * @author vvakame
 */
public class Debug {

	private Debug() {
	}

	/**
	 * dump {@link JsxParser} state. get current cunsuming.
	 * @param parser
	 * @return state string
	 * @author vvakame
	 */
	public static String dump(JsxParser parser) {
		ParserBaseExtend extend = ParserBaseExtend.get(parser);
		final int pos = extend.getPos();
		Source source = extend.getSource();
		String current = source.at(0, pos);
		@SuppressWarnings("unused")
		String all = source.at(0, extend.getEndpos());

		return current;
	}

	/**
	 * dump {@link Tree}. print the syntax tree.
	 * @param syntaxTree
	 * @author vvakame
	 */
	public static void dump(Tree syntaxTree) {
		dump(syntaxTree, 0);
	}

	static void dump(Tree syntaxTree, int depth) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			builder.append("\t");
		}
		builder.append(syntaxTree.lhs.rule()).append(" ");
		PhraseExtend extend = syntaxTree.lhsExtend;
		builder.append(extend.getStart()).append(":");
		builder.append(extend.getEnd()).append(" ");
		if (extend.getStart() == extend.getEnd()) {
			builder.append("*empty ");
		}

		List<String> valuesToken = new ArrayList<String>();
		valuesToken.add("ident");
		valuesToken.add("doubleQuoted");
		valuesToken.add("singleQuoted");
		valuesToken.add("decimalIntegerLiteral");
		if (valuesToken.contains(extend.rule())) {
			builder.append(extend.text()).append(" ");
		}

		System.out.println(builder.toString());
		for (Tree child : syntaxTree.rhs) {
			dump(child, depth + 1);
		}
	}

	/**
	 * dump {@link SyntaxTreeMouseImpl}. print syntax tree.
	 * TODO for vvakame
	 * @param syntaxTree
	 * @author vvakame
	 */
	public static void dump(SyntaxTreeMouseImpl syntaxTree) {
		dump(syntaxTree, 0);
	}

	static void dump(SyntaxTreeMouseImpl syntaxTree, int depth) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			builder.append("\t");
		}
		builder.append(syntaxTree.type);
		System.out.println(builder.toString());
		for (SyntaxTree child : syntaxTree.children) {
			dump((SyntaxTreeMouseImpl) child, depth + 1);
		}
	}

	/**
	 * construct source code from {@link Tree}.
	 * @param syntaxTree
	 * @return JSX source code
	 * @author vvakame
	 */
	@Deprecated
	public static String replayText(Tree syntaxTree) {
		StringBuilder builder = new StringBuilder();
		replayText(syntaxTree, builder);
		return builder.toString();
	}

	static void replayText(Tree syntaxTree, StringBuilder builder) {
		if ("spacing".equals(syntaxTree.lhs.rule())) {
		} else if (syntaxTree.lhs.rule().length() != 0
				&& Character.isUpperCase(syntaxTree.lhs.rule().charAt(0))) {
			builder.append(syntaxTree.lhs.text());
		} else if (syntaxTree.rhs.size() == 0) {
			builder.append(syntaxTree.lhs.text());
			return;
		}
		for (Tree tree : syntaxTree.rhs) {
			replayText(tree, builder);
		}
	}

	/**
	 * construct source code from {@link SyntaxTreeMouseImpl}.
	 * @param syntaxTree
	 * @return JSX source code.
	 * @author vvakame
	 */
	public static String replayText(SyntaxTreeMouseImpl syntaxTree) {
		StringBuilder builder = new StringBuilder();
		replayText(syntaxTree, builder);
		return builder.toString();
	}

	static void replayText(SyntaxTreeMouseImpl syntaxTree, StringBuilder builder) {
		builder.append(syntaxTree.name());
		for (SyntaxTree tree : syntaxTree.children()) {
			replayText((SyntaxTreeMouseImpl) tree, builder);
		}
	}
}
