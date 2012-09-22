package net.vvakame.jsx.wrapper.entity;

import java.util.List;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Member in JSX AST.
 * @author vvakame
 */
@JsonModel(treatUnknownKeyAsError = true, genToPackagePrivate = true)
public class Member {

	@JsonKey(converter = JsxTokenConverter.class)
	Token token;

	@JsonKey(converter = JsxTokenConverter.class)
	Token nameToken;

	@JsonKey
	String name;

	@JsonKey
	String type;

	@JsonKey(converter = StatementConverter.class)
	Statement initialValue;

	@JsonKey
	int flags;

	@JsonKey
	String returnType;

	@JsonKey(converter = ArgsListConverter.class)
	List<Args> args;

	@JsonKey(converter = ArgsListConverter.class)
	List<Args> locals;

	@JsonKey(converter = StatementListConverter.class)
	List<Statement> statements;


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
	 * @return the nameToken
	 */
	public Token getNameToken() {
		return nameToken;
	}

	/**
	 * @param nameToken
	 *            the nameToken to set
	 */
	public void setNameToken(Token nameToken) {
		this.nameToken = nameToken;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

	/**
	 * @return the initialValue
	 */
	public Statement getInitialValue() {
		return initialValue;
	}

	/**
	 * @param initialValue
	 *            the initialValue to set
	 */
	public void setInitialValue(Statement initialValue) {
		this.initialValue = initialValue;
	}

	/**
	 * @return the flags
	 */
	public int getFlags() {
		return flags;
	}

	/**
	 * @param flags
	 *            the flags to set
	 */
	public void setFlags(int flags) {
		this.flags = flags;
	}

	/**
	 * @return the returnType
	 */
	public String getReturnType() {
		return returnType;
	}

	/**
	 * @param returnType
	 *            the returnType to set
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	/**
	 * @return the args
	 */
	public List<Args> getArgs() {
		return args;
	}

	/**
	 * @param args
	 *            the args to set
	 */
	public void setArgs(List<Args> args) {
		this.args = args;
	}

	/**
	 * @return the locals
	 */
	public List<Args> getLocals() {
		return locals;
	}

	/**
	 * @param locals
	 *            the locals to set
	 */
	public void setLocals(List<Args> locals) {
		this.locals = locals;
	}

	/**
	 * @return the statements
	 */
	public List<Statement> getStatements() {
		return statements;
	}

	/**
	 * @param statements
	 *            the statements to set
	 */
	public void setStatements(List<Statement> statements) {
		this.statements = statements;
	}

	@Override
	public String toString() {
		return "Member [token=" + token + ", nameToken=" + nameToken + ", name=" + name + ", type="
				+ type + ", initialValue=" + initialValue + ", flags=" + flags + ", returnType="
				+ returnType + ", args=" + args + ", locals=" + locals + ", statements="
				+ statements + "]";
	}
}
