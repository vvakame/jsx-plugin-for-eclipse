package net.vvakame.jsx.wrapper.entity;

import java.util.List;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

@JsonModel(treatUnknownKeyAsError = true)
public class Ast {

	@JsonKey
	Token token;

	@JsonKey
	String name;

	@JsonKey
	int flags;

	@JsonKey("extends")
	String extendsValue;

	@JsonKey("implements")
	List<String> implementsValue;

	@JsonKey
	List<Member> members;

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
	 * @return the extendsValue
	 */
	public String getExtendsValue() {
		return extendsValue;
	}

	/**
	 * @param extendsValue
	 *            the extendsValue to set
	 */
	public void setExtendsValue(String extendsValue) {
		this.extendsValue = extendsValue;
	}

	/**
	 * @return the members
	 */
	public List<Member> getMembers() {
		return members;
	}

	/**
	 * @param members
	 *            the members to set
	 */
	public void setMembers(List<Member> members) {
		this.members = members;
	}

	/**
	 * @return the implementsValue
	 */
	public List<String> getImplementsValue() {
		return implementsValue;
	}

	/**
	 * @param implementsValue
	 *            the implementsValue to set
	 */
	public void setImplementsValue(List<String> implementsValue) {
		this.implementsValue = implementsValue;
	}
}
