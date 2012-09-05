package mouse.runtime;

import mouse.runtime.ParserBase.Phrase;

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

	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return base.equals(obj);
	}

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
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
	 * @return
	 * @see mouse.runtime.ParserBase#lhs()
	 */
	public Phrase lhs() {
		return base.lhs();
	}

	/**
	 * @param i
	 * @return
	 * @see mouse.runtime.ParserBase#rhs(int)
	 */
	public Phrase rhs(int i) {
		return base.rhs(i);
	}

	/**
	 * @return
	 * @see mouse.runtime.ParserBase#rhsSize()
	 */
	public int rhsSize() {
		return base.rhsSize();
	}

	/**
	 * @param i
	 * @param j
	 * @return
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

	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return base.toString();
	}
}
