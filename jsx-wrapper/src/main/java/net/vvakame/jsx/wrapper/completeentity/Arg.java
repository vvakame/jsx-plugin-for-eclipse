package net.vvakame.jsx.wrapper.completeentity;

import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Arg in Complete.
 * @author vvakame
 */
@JsonModel(treatUnknownKeyAsError = true, genToPackagePrivate = true)
public class Arg {

	@JsonKey
	String name;

	@JsonKey
	String type;


	/**
	 * @return the name
	 * @category accessor
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 * @category accessor
	 */
	public void setName(String name) {
		this.name = name;
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
}
