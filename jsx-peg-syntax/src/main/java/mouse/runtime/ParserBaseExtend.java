package mouse.runtime;

import mouse.runtime.ParserBase.Phrase;

/**
 * {@link ParserBase} wrapper.
 * @author vvakame
 */
public class ParserBaseExtend {

	ParserBase base;


	private ParserBaseExtend() {
	}

	public static ParserBaseExtend get(ParserBase base) {
		ParserBaseExtend extend = new ParserBaseExtend();
		extend.base = base;
		return extend;
	}

	public int getPos() {
		return base.pos;
	}

	public int getEndpos() {
		return base.endpos;
	}

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
	 * TODO for vvakame
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return base.hashCode();
	}

	/**
	 * TODO for vvakame
	 * @param src
	 * @see mouse.runtime.ParserBase#init(mouse.runtime.Source)
	 */
	public void init(Source src) {
		base.init(src);
	}

	/**
	 * TODO for vvakame
	 * @return
	 * @see mouse.runtime.ParserBase#lhs()
	 */
	public Phrase lhs() {
		return base.lhs();
	}

	/**
	 * TODO for vvakame
	 * @param i
	 * @return
	 * @see mouse.runtime.ParserBase#rhs(int)
	 */
	public Phrase rhs(int i) {
		return base.rhs(i);
	}

	/**
	 * TODO for vvakame
	 * @return
	 * @see mouse.runtime.ParserBase#rhsSize()
	 */
	public int rhsSize() {
		return base.rhsSize();
	}

	/**
	 * TODO for vvakame
	 * @param i
	 * @param j
	 * @return
	 * @see mouse.runtime.ParserBase#rhsText(int, int)
	 */
	public String rhsText(int i, int j) {
		return base.rhsText(i, j);
	}

	/**
	 * TODO for vvakame
	 * @param trace
	 * @see mouse.runtime.ParserBase#setTrace(java.lang.String)
	 */
	public void setTrace(String trace) {
		base.setTrace(trace);
	}

	/**
	 * TODO for vvakame
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return base.toString();
	}
}
