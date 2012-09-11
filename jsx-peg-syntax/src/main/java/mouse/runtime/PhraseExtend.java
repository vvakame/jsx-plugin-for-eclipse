package mouse.runtime;

import mouse.runtime.ParserBase.Phrase;

public class PhraseExtend {
	Phrase phrase;

	public static PhraseExtend get(Phrase phrase) {
		PhraseExtend extend = new PhraseExtend();
		extend.phrase = phrase;
		return extend;
	}

	public int getStart() {
		return phrase.start;
	}

	public int getEnd() {
		return phrase.end;
	}

	/**
	 * @param o
	 * @see mouse.runtime.ParserBase.Phrase#put(java.lang.Object)
	 */
	public void put(Object o) {
		phrase.put(o);
	}

	/**
	 * @return
	 * @see mouse.runtime.ParserBase.Phrase#get()
	 */
	public Object get() {
		return phrase.get();
	}

	/**
	 * @return
	 * @see mouse.runtime.ParserBase.Phrase#text()
	 */
	public String text() {
		return phrase.text();
	}

	/**
	 * @param i
	 * @return
	 * @see mouse.runtime.ParserBase.Phrase#charAt(int)
	 */
	public char charAt(int i) {
		return phrase.charAt(i);
	}

	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return phrase.equals(obj);
	}

	/**
	 * @return
	 * @see mouse.runtime.ParserBase.Phrase#isEmpty()
	 */
	public boolean isEmpty() {
		return phrase.isEmpty();
	}

	/**
	 * @return
	 * @see mouse.runtime.ParserBase.Phrase#rule()
	 */
	public String rule() {
		return phrase.rule();
	}

	/**
	 * @return
	 * @see mouse.runtime.ParserBase.Phrase#isTerm()
	 */
	public boolean isTerm() {
		return phrase.isTerm();
	}

	/**
	 * @return
	 * @see mouse.runtime.ParserBase.Phrase#errMsg()
	 */
	public String errMsg() {
		return phrase.errMsg();
	}

	/**
	 * 
	 * @see mouse.runtime.ParserBase.Phrase#errClear()
	 */
	public void errClear() {
		phrase.errClear();
	}

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return phrase.hashCode();
	}

	/**
	 * @param rule
	 * @return
	 * @see mouse.runtime.ParserBase.Phrase#isA(java.lang.String)
	 */
	public boolean isA(String rule) {
		return phrase.isA(rule);
	}

	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return phrase.toString();
	}

	/**
	 * @param i
	 * @return
	 * @see mouse.runtime.ParserBase.Phrase#where(int)
	 */
	public String where(int i) {
		return phrase.where(i);
	}
}
