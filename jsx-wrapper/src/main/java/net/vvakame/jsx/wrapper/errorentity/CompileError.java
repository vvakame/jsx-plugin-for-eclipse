package net.vvakame.jsx.wrapper.errorentity;

/**
 * Compile error info.
 * @author vvakame
 */
public class CompileError {

	String filename;

	int line;

	int column;

	String message;


	/**
	 * @return the filename
	 * @category accessor
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 * @category accessor
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the line
	 * @category accessor
	 */
	public int getLine() {
		return line;
	}

	/**
	 * @param line the line to set
	 * @category accessor
	 */
	public void setLine(int line) {
		this.line = line;
	}

	/**
	 * @return the column
	 * @category accessor
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @param column the column to set
	 * @category accessor
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * @return the message
	 * @category accessor
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 * @category accessor
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "CompileError [filename=" + filename + ", line=" + line + ", column=" + column
				+ ", message=" + message + "]";
	}
}
