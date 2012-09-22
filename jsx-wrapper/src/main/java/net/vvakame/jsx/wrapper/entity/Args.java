package net.vvakame.jsx.wrapper.entity;

/**
 * Arguments in JSX AST.
 * @author vvakame
 */
public class Args {

	Token token;

	String type;


	/**
	 * @return the token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(Token token) {
		this.token = token;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Args [token=" + token + ", type=" + type + "]";
	}
}
