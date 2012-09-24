package net.vvakame.jsx.wrapper;

/**
 * Raise by JSX command when failure.
 * @author vvakame
 */
public class JsxCommandException extends RuntimeException {

	private static final long serialVersionUID = 1L;


	/**
	 * the constructor.
	 * @category constructor
	 */
	public JsxCommandException() {
		super();
	}

	/**
	 * the constructor.
	 * @param message
	 * @param th
	 * @category constructor
	 */
	public JsxCommandException(String message, Throwable th) {
		super(message, th);
	}

	/**
	 * the constructor.
	 * @param message
	 * @category constructor
	 */
	public JsxCommandException(String message) {
		super(message);
	}

	/**
	 * the constructor.
	 * @param th
	 * @category constructor
	 */
	public JsxCommandException(Throwable th) {
		super(th);
	}
}
