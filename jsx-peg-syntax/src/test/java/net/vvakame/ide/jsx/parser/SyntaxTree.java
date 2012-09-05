package net.vvakame.ide.jsx.parser;

import java.util.List;

public interface SyntaxTree {

	public SyntaxTree parent();

	public List<SyntaxTree> children();

	public String type();

	public String name();

	public int index();

	@Deprecated
	public int line();

	@Deprecated
	public int column();
}