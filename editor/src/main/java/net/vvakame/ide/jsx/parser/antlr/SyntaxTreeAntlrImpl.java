package net.vvakame.ide.jsx.parser.antlr;

import java.util.ArrayList;
import java.util.List;

import net.vvakame.ide.jsx.parser.SourceInfo;
import net.vvakame.ide.jsx.parser.SyntaxTree;
import net.vvakame.jsx.antlr.JSXLexer;

import org.antlr.runtime.tree.Tree;

public class SyntaxTreeAntlrImpl implements SyntaxTree {

	public final SyntaxTree parent;
	public final List<SyntaxTree> children = new ArrayList<SyntaxTree>();
	public final Type type;
	public final String name;
	public final int index;
	public final int line;
	public final int column;

	private SyntaxTreeAntlrImpl(SourceInfo srcInfo, SyntaxTree parent, Tree t,
			Type type) {

		SyntaxTree currentClass = null;
		for (int i = 0; i < t.getChildCount(); i++) {
			Tree child = t.getChild(i);
			final String text = child.getText();

			if ("import".equals(text)) {
				while (child.getType() != JSXLexer.DOUBLE_QUOTED
						&& child.getType() != JSXLexer.SINGLE_QUOTED) {
					i++;
					child = t.getChild(i);
				}
				child = t.getChild(i);
				construct(srcInfo, this, child, Type.IMPORT);

			} else if ("class".equals(text)) {
				i++;
				child = t.getChild(i);
				currentClass = construct(srcInfo, this, child, Type.CLASS);
			} else if ("interface".equals(text)) {
				i++;
				child = t.getChild(i);
				currentClass = construct(srcInfo, this, child, Type.INTERFACE);
			} else if ("mixin".equals(text)) {
				i++;
				child = t.getChild(i);
				currentClass = construct(srcInfo, this, child, Type.MIXIN);
			} else if ("function".equals(text)) {
				// FIXME this implementation is broken! not only
				// memberDefinition.
				i++;
				child = t.getChild(i);
				construct(srcInfo, currentClass, child, Type.MEMBER_FUNCTION);
			}
		}

		this.name = t.getText();
		this.type = type;
		this.line = t.getLine();
		this.column = t.getCharPositionInLine();
		this.index = srcInfo.getIndex(this.line, this.column);

		if (parent != null) {
			parent.children().add(this);
			this.parent = parent;
		} else {
			this.parent = null;
		}
	}

	public static SyntaxTreeAntlrImpl construct(SourceInfo srcInfo, Tree t) {
		return new SyntaxTreeAntlrImpl(srcInfo, null, t, null);
	}

	static SyntaxTreeAntlrImpl construct(SourceInfo srcInfo, SyntaxTree parent,
			Tree t, Type type) {
		return new SyntaxTreeAntlrImpl(srcInfo, parent, t, type);
	}

	@Override
	public String toString() {
		return "SyntaxTree [name=" + name + ", index=" + index + ", line="
				+ line + ", column=" + column + "]";
	}

	@Override
	public SyntaxTree parent() {
		return this.parent;
	}

	@Override
	public List<SyntaxTree> children() {
		return this.children;
	}

	@Override
	public Type type() {
		return this.type;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public int index() {
		return this.index;
	}

	@Override
	public int line() {
		return this.line;
	}

	@Override
	public int column() {
		return this.column;
	}
}