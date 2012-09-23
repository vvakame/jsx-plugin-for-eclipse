package net.vvakame.jsx.wrapper.entity;

import java.util.List;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * ClassDefinition in JSX AST.
 * @author vvakame
 */
@JsonModel(treatUnknownKeyAsError = true)
public class ClassDefinition {

	// from classdef.js

	static final int IS_CONST = 1;

	static final int IS_ABSTRACT = 2;

	static final int IS_FINAL = 4;

	static final int IS_STATIC = 8;

	static final int IS_NATIVE = 16;

	static final int IS_OVERRIDE = 32;

	static final int IS_INTERFACE = 64;

	static final int IS_MIXIN = 128;

	static final int IS_FAKE = 256; // used for marking a JS non-class object that should be treated like a JSX class instance (e.g. window)

	static final int IS_READONLY = 512;

	static final int IS_INLINE = 1024;

	static final int IS_PURE = 2048; // constexpr (intended for for native functions)

	static final int IS_DELETE = 4096; // used for disabling the default constructor

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
	 * no has interface or mixin modifier.
	 * @return class or not
	 * @author vvakame
	 */
	public boolean isClass() {
		return (flags & (IS_INTERFACE | IS_MIXIN)) == 0;
	}

	/**
	 * has const modifier.
	 * @return const or not
	 * @author vvakame
	 */
	public boolean isConst() {
		return (flags & IS_CONST) != 0;
	}

	/**
	 * has abstract modifier.
	 * @return abstract or not
	 * @author vvakame
	 */
	public boolean isAbstract() {
		return (flags & IS_ABSTRACT) != 0;
	}

	/**
	 * has final modifier.
	 * @return final or not
	 * @author vvakame
	 */
	public boolean isFinal() {
		return (flags & IS_FINAL) != 0;
	}

	/**
	 * has static modifier.
	 * @return static or not
	 * @author vvakame
	 */
	@Deprecated
	public boolean isStatic() {
		return (flags & IS_STATIC) != 0;
	}

	/**
	 * has native modifier.
	 * @return native or not
	 * @author vvakame
	 */
	public boolean isNative() {
		return (flags & IS_NATIVE) != 0;
	}

	/**
	 * has override modifier.
	 * @return override or not
	 * @author vvakame
	 */
	@Deprecated
	public boolean isOverride() {
		return (flags & IS_OVERRIDE) != 0;
	}

	/**
	 * has interface modifier.
	 * @return interface or not
	 * @author vvakame
	 */
	public boolean isInterface() {
		return (flags & IS_INTERFACE) != 0;
	}

	/**
	 * has mixin modifier.
	 * @return mixin or not
	 * @author vvakame
	 */
	public boolean isMixin() {
		return (flags & IS_MIXIN) != 0;
	}

	/**
	 * has face modifier.
	 * @return face or not
	 * @author vvakame
	 */
	public boolean isFake() {
		return (flags & IS_FAKE) != 0;
	}

	/**
	 * has readonly modifier.
	 * @return readpnly or not
	 * @author vvakame
	 */
	@Deprecated
	public boolean isReadonly() {
		return (flags & IS_READONLY) != 0;
	}

	/**
	 * has inline modifier.
	 * @return inline or not
	 * @author vvakame
	 */
	@Deprecated
	public boolean isInline() {
		return (flags & IS_INLINE) != 0;
	}

	/**
	 * has pure modifier.
	 * @return pure or not
	 * @author vvakame
	 */
	@Deprecated
	public boolean isPure() {
		return (flags & IS_PURE) != 0;
	}

	/**
	 * has delete modifier.
	 * @return delete or not
	 * @author vvakame
	 */
	@Deprecated
	public boolean isDelete() {
		return (flags & IS_DELETE) != 0;
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

	@Override
	public String toString() {
		return "ClassDefinition [token=" + token + ", name=" + name + ", flags=" + flags
				+ ", extendsValue=" + extendsValue + ", implementsValue=" + implementsValue
				+ ", members=" + members + "]";
	}
}
