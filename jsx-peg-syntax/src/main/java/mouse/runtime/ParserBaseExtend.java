package mouse.runtime;

import mouse.runtime.ParserBase.Phrase;

/**
 * {@link ParserBase} wrapper. this class has getPos, getEndpos and getSource method.
 * @author vvakame
 */
public class ParserBaseExtend {

	ParserBase base;


	private ParserBaseExtend() {
	}

	/**
	 * converter.
	 * @param base
	 * @return {@link ParserBaseExtend}
	 * @author vvakame
	 */
	public static ParserBaseExtend get(ParserBase base) {
		ParserBaseExtend extend = new ParserBaseExtend();
		extend.base = base;
		return extend;
	}

	/**
	 * delegate to base#pos .
	 * @return current possition
	 * @author vvakame
	 */
	public int getPos() {
		return base.pos;
	}

	/**
	 * delegate to base#endpos .
	 * @return current end possition
	 * @author vvakame
	 */
	public int getEndpos() {
		return base.endpos;
	}

	/**
	 * delegate to base.sourece .
	 * @return source string
	 * @author vvakame
	 */
	public Source getSource() {
		return base.source;
	}

	/**
	 * same as {@link ParserBase#equals(Object)}
	 * @param obj
	 * @return same as {@link ParserBase#equals(Object)}
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return base.equals(obj);
	}

	/**
	 * @return delegate to base.
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return base.hashCode();
	}

	/**
	 * @param src
	 * @see mouse.runtime.ParserBase#init(mouse.runtime.Source)
	 */
	public void init(Source src) {
		base.init(src);
	}

	/**
	 * @return delegate to base.
	 * @see mouse.runtime.ParserBase#lhs()
	 */
	public Phrase lhs() {
		return base.lhs();
	}

	/**
	 * @param i
	 * @return delegate to base.
	 * @see mouse.runtime.ParserBase#rhs(int)
	 */
	public Phrase rhs(int i) {
		return base.rhs(i);
	}

	/**
	 * @return delegate to base.
	 * @see mouse.runtime.ParserBase#rhsSize()
	 */
	public int rhsSize() {
		return base.rhsSize();
	}

	/**
	 * @param i
	 * @param j
	 * @return delegate to base.
	 * @see mouse.runtime.ParserBase#rhsText(int, int)
	 */
	public String rhsText(int i, int j) {
		return base.rhsText(i, j);
	}

	/**
	 * @param trace
	 * @see mouse.runtime.ParserBase#setTrace(java.lang.String)
	 */
	public void setTrace(String trace) {
		base.setTrace(trace);
	}

	@Override
	public String toString() {
		return base.toString();
	}
}
