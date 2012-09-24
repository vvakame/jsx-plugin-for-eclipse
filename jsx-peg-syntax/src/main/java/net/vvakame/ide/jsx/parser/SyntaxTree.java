package net.vvakame.ide.jsx.parser;

import java.util.List;

/**
 * Syntax tree.
 * @author vvakame
 */
public interface SyntaxTree {

	/**
	 * get parent tree. 
	 * @return {@link SyntaxTree}
	 */
	public SyntaxTree parent();

	/**
	 * get child trees.
	 * @return child trees. 
	 */
	public List<SyntaxTree> children();

	/** 
	 * get node type
	 * @return node type
	 */
	public String type();

	/**
	 * get node name
	 * @return node name
	 */
	public String name();

	/** 
	 * get index about file offset.
	 * @return index
	 */
	public int index();
}
