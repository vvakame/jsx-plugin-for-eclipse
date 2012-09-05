package net.vvakame.ide.jsx.parser;

import java.util.List;

public interface SyntaxTree {

	enum Type {
		IMPORT, CLASS, INTERFACE, MIXIN, MEMBER_FUNCTION, UNKNOWN
	}

	public SyntaxTree parent();

	public List<SyntaxTree> children();

	public Type type();

	public String name();

	public int index();

	@Deprecated
	public int line();

	@Deprecated
	public int column();
}