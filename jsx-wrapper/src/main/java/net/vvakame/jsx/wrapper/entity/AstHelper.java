package net.vvakame.jsx.wrapper.entity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.vvakame.util.jsonpullparser.JsonFormatException;

/**
 * Helper utility for Ast process.
 * @author vvakame
 */
public class AstHelper {

	private AstHelper() {
	}

	/**
	 * get ast.
	 * @param stream
	 * @return ast
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static List<ClassDefinition> getAst(InputStream stream) throws IOException,
			JsonFormatException {
		return ClassDefinitionGen.getList(stream);
	}

	/**
	 * Filter by file name.
	 * @param classes
	 * @param fileName
	 * @return filtered list.
	 * @author vvakame
	 */
	public static List<ClassDefinition> filterByDefinedFile(List<ClassDefinition> classes,
			String fileName) {
		List<ClassDefinition> newList = new ArrayList<ClassDefinition>();
		for (ClassDefinition classDefinition : classes) {
			if (classDefinition.getToken().getFilename().endsWith(fileName)) {
				newList.add(classDefinition);
			}
		}

		return newList;
	}
}
