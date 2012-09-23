package net.vvakame.jsx.wrapper.parseentity;

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
	 * is function?
	 * @return function or not
	 * @author vvakame
	 */
	public boolean isFunction() {
		return token != null && "function".equals(token.getValue());
	}

	/**
	 * has const modifier.
	 * @return const or not
	 * @author vvakame
	 */
	public boolean isConst() {
		return (flags & ClassDefinition.IS_CONST) != 0;
	}

	/**
	 * has abstract modifier.
	 * @return abstract or not
	 * @author vvakame
	 */
	public boolean isAbstract() {
		return (flags & ClassDefinition.IS_ABSTRACT) != 0;
	}

	/**
	 * has final modifier.
	 * @return final or not
	 * @author vvakame
	 */
	public boolean isFinal() {
		return (flags & ClassDefinition.IS_FINAL) != 0;
	}

	/**
	 * has static modifier.
	 * @return static or not
	 * @author vvakame
	 */
	public boolean isStatic() {
		return (flags & ClassDefinition.IS_STATIC) != 0;
	}

	/**
	 * has native modifier.
	 * @return native or not
	 * @author vvakame
	 */
	public boolean isNative() {
		return (flags & ClassDefinition.IS_NATIVE) != 0;
	}

	/**
	 * has override modifier.
	 * @return override or not
	 * @author vvakame
	 */
	public boolean isOverride() {
		return (flags & ClassDefinition.IS_OVERRIDE) != 0;
	}

	/**
	 * has interface modifier.
	 * @return interface or not
	 * @author vvakame
	 */
	public boolean isInterface() {
		return (flags & ClassDefinition.IS_INTERFACE) != 0;
	}

	/**
	 * has mixin modifier.
	 * @return mixin or not
	 * @author vvakame
	 */
	public boolean isMixin() {
		return (flags & ClassDefinition.IS_MIXIN) != 0;
	}

	/**
	 * has face modifier.
	 * @return face or not
	 * @author vvakame
	 */
	public boolean isFake() {
		return (flags & ClassDefinition.IS_FAKE) != 0;
	}

	/**
	 * has readonly modifier.
	 * @return readpnly or not
	 * @author vvakame
	 */
	public boolean isReadonly() {
		return (flags & ClassDefinition.IS_READONLY) != 0;
	}

	/**
	 * has inline modifier.
	 * @return inline or not
	 * @author vvakame
	 */
	public boolean isInline() {
		return (flags & ClassDefinition.IS_INLINE) != 0;
	}

	/**
	 * has pure modifier.
	 * @return pure or not
	 * @author vvakame
	 */
	public boolean isPure() {
		return (flags & ClassDefinition.IS_PURE) != 0;
	}

	/**
	 * has delete modifier.
	 * @return delete or not
	 * @author vvakame
	 */
	public boolean isDelete() {
		return (flags & ClassDefinition.IS_DELETE) != 0;
	}

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
