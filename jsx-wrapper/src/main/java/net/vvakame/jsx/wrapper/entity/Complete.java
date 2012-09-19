package net.vvakame.jsx.wrapper.entity;

import java.io.IOException;
import java.io.StringWriter;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Complete in JSX completion.
 * @author vvakame
 */
@JsonModel(treatUnknownKeyAsError = true)
public class Complete {

	@JsonKey
	String word;

	@JsonKey
	String partialWord;

	@JsonKey
	String definedFilename;

	@JsonKey
	int definedLineNumber;

	@JsonKey
	String doc;


	@Override
	public String toString() {
		StringWriter writer = new StringWriter();
		try {
			CompleteGen.encode(writer, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

	/**
	 * @return the word
	 * @category accessor
	 */
	public String getWord() {
		return word;
	}

	/**
	 * @param word the word to set
	 * @category accessor
	 */
	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * @return the definedFilename
	 * @category accessor
	 */
	public String getDefinedFilename() {
		return definedFilename;
	}

	/**
	 * @param definedFilename the definedFilename to set
	 * @category accessor
	 */
	public void setDefinedFilename(String definedFilename) {
		this.definedFilename = definedFilename;
	}

	/**
	 * @return the definedLineNumber
	 * @category accessor
	 */
	public int getDefinedLineNumber() {
		return definedLineNumber;
	}

	/**
	 * @param definedLineNumber the definedLineNumber to set
	 * @category accessor
	 */
	public void setDefinedLineNumber(int definedLineNumber) {
		this.definedLineNumber = definedLineNumber;
	}

	/**
	 * @return the doc
	 * @category accessor
	 */
	public String getDoc() {
		return doc;
	}

	/**
	 * @param doc the doc to set
	 * @category accessor
	 */
	public void setDoc(String doc) {
		this.doc = doc;
	}

	/**
	 * @return the partialWord
	 * @category accessor
	 */
	public String getPartialWord() {
		return partialWord;
	}

	/**
	 * @param partialWord the partialWord to set
	 * @category accessor
	 */
	public void setPartialWord(String partialWord) {
		this.partialWord = partialWord;
	}
}
