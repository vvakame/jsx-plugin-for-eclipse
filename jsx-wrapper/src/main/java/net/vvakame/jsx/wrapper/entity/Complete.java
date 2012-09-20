package net.vvakame.jsx.wrapper.entity;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

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
	String doc;

	@JsonKey
	String type;

	@JsonKey
	List<String> args;

	@JsonKey
	String returnType;

	@JsonKey
	String definedClass;

	@JsonKey
	String definedFilename;

	@JsonKey
	int definedLineNumber;


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
	 * @return the type
	 * @category accessor
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 * @category accessor
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the args
	 * @category accessor
	 */
	public List<String> getArgs() {
		return args;
	}

	/**
	 * @param args the args to set
	 * @category accessor
	 */
	public void setArgs(List<String> args) {
		this.args = args;
	}

	/**
	 * @return the returnType
	 * @category accessor
	 */
	public String getReturnType() {
		return returnType;
	}

	/**
	 * @param returnType the returnType to set
	 * @category accessor
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	/**
	 * @return the definedClass
	 * @category accessor
	 */
	public String getDefinedClass() {
		return definedClass;
	}

	/**
	 * @param definedClass the definedClass to set
	 * @category accessor
	 */
	public void setDefinedClass(String definedClass) {
		this.definedClass = definedClass;
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
}
