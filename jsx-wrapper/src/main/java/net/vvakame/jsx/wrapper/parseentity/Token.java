package net.vvakame.jsx.wrapper.parseentity;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Token in JSX AST.
 * @author vvakame
 */
@JsonModel(treatUnknownKeyAsError = true, genToPackagePrivate = true)
public class Token {

	@JsonKey("_value")
	String value;

	@JsonKey("_isIdentifier")
	boolean isIdentifier;

	@JsonKey("_filename")
	String filename;

	@JsonKey("_lineNumber")
	Integer lineNumber;

	@JsonKey("_columnNumber")
	Integer columnNumber;


	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the isIdentifier
	 */
	public boolean isIdentifier() {
		return isIdentifier;
	}

	/**
	 * @param isIdentifier
	 *            the isIdentifier to set
	 */
	public void setIdentifier(boolean isIdentifier) {
		this.isIdentifier = isIdentifier;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename
	 *            the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the lineNumber
	 */
	public Integer getLineNumber() {
		return lineNumber;
	}

	/**
	 * @param lineNumber
	 *            the lineNumber to set
	 */
	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * @return the columnNumber
	 */
	public Integer getColumnNumber() {
		return columnNumber;
	}

	/**
	 * @param columnNumber
	 *            the columnNumber to set
	 */
	public void setColumnNumber(Integer columnNumber) {
		this.columnNumber = columnNumber;
	}

	@Override
	public String toString() {
		return "Token [value=" + value + ", isIdentifier=" + isIdentifier + ", filename="
				+ filename + ", lineNumber=" + lineNumber + ", columnNumber=" + columnNumber + "]";
	}
}
