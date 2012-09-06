package net.vvakame.jsx.peg;

import mouse.runtime.Phrase;
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
}
