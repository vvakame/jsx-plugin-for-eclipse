package net.vvakame.jsx.peg;

import mouse.runtime.Phrase;
import mouse.runtime.PhraseExtend;
import mouse.runtime.SemanticsBase;

public class JsxSemantics extends SemanticsBase {

	SyntaxTreeMouseImpl parent;
	SyntaxTreeMouseImpl current;

	public SyntaxTreeMouseImpl getSyntaxTree() {
		return current;
	}

	public void programFile() {
		if (parent != null) {
			current = parent;
		} else {
			current = new SyntaxTreeMouseImpl();
		}
		current.init(lhs());
	}

	public void importStatement() {
		SyntaxTreeMouseImpl tree = new SyntaxTreeMouseImpl();
		tree.init(lhs());

		for (int i = 0; i < rhsSize(); i++) {
			Phrase rhs = rhs(i);

			SyntaxTreeMouseImpl child = new SyntaxTreeMouseImpl();
			child.init(rhs);
			tree.children.add(child);
		}

		if (parent == null) {
			parent = new SyntaxTreeMouseImpl();
		}
		parent.children().add(tree);
	}

	public void variableDeclaration() {
		dump();
	}

	public void dump() {
		StringBuilder builder = new StringBuilder();

		StackTraceElement stack = Thread.currentThread().getStackTrace()[2];
		builder.append("at ").append(stack.getMethodName()).append("\t");
		builder.append(stack.getFileName()).append("#")
				.append(stack.getMethodName()).append(" L")
				.append(stack.getLineNumber()).append("\n");

		dumpPhrase(builder, "lhs", lhs());
		for (int i = 0; i < rhsSize(); i++) {
			Phrase rhs = rhs(i);
			dumpPhrase(builder, "rhs(" + i + ")", rhs);
		}
		builder.append("\n");

		System.out.println(builder.toString());
	}

	void dumpPhrase(StringBuilder builder, String prefix, Phrase phrase) {
		final PhraseExtend extend = PhraseExtend
				.get((mouse.runtime.ParserBase.Phrase) phrase);

		builder.append("\t").append(prefix).append("\n");
		builder.append("\t\trule=\"").append(extend.rule())
				.append("\" : index=").append(extend.getStart()).append("\n");
		builder.append("\t\ttext=\"").append(extend.text()).append("\"\n");
	}
}
