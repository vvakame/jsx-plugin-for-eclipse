package net.vvakame.jsx.wrapper.entity;

import java.io.IOException;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Parse helpder.
 */
public class ParseHelper {

	private ParseHelper() {
	}

	/**
	 * parser.
	 * @param jsonArray
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static void parseStatement(JsonArray jsonArray) throws IOException, JsonFormatException {
		if (jsonArray == null) {
		} else if ("NumberLiteralExpression".equals(jsonArray.getStringOrNull(0))) {
			singleStatementLiteral(jsonArray);
		} else if ("IntegerLiteralExpression".equals(jsonArray.getStringOrNull(0))) {
			singleStatementLiteral(jsonArray);
		} else if ("StringLiteralExpression".equals(jsonArray.getStringOrNull(0))) {
			singleStatementLiteral(jsonArray);
		} else if ("BooleanLiteralExpression".equals(jsonArray.getStringOrNull(0))) {
			singleStatementLiteral(jsonArray);

		} else if ("BinaryExpression".equals(jsonArray.getStringOrNull(0))) {
			binaryExpression(jsonArray);

		} else if ("AsExpression".equals(jsonArray.getStringOrNull(0))) {
			asExpression(jsonArray);

		} else if ("ClassExpression".equals(jsonArray.getStringOrNull(0))) {
			classExpression(jsonArray);
		} else if ("NullExpression".equals(jsonArray.getStringOrNull(0))) {
			classExpression(jsonArray);

		} else if ("CallExpression".equals(jsonArray.getStringOrNull(0))) {
			callExpression(jsonArray);

		} else if ("ArrayLiteralExpression".equals(jsonArray.getStringOrNull(0))) {
			arrayLiteralExpression(jsonArray);

		} else if ("PropertyExpression".equals(jsonArray.getStringOrNull(0))) {
			// 確度低い
			propertyExpression(jsonArray);

		} else if ("FunctionExpression".equals(jsonArray.getStringOrNull(0))) {
			functionExpression(jsonArray);

		} else if ("MapLiteralExpression".equals(jsonArray.getStringOrNull(0))) {
			mapLiteralExpression(jsonArray);

		} else if ("ExpressionStatement".equals(jsonArray.getStringOrNull(0))) {
			expressionStatement(jsonArray);
		} else if ("WhileStatement".equals(jsonArray.getStringOrNull(0))) {
			whileStatement(jsonArray);
		} else if ("ConstructorInvocationStatement".equals(jsonArray.getStringOrNull(0))) {
			constructorInvocationStatement(jsonArray);
		} else if ("LogStatement".equals(jsonArray.getStringOrNull(0))) {
			logStatement(jsonArray);
		} else if ("DebuggerStatement".equals(jsonArray.getStringOrNull(0))) {
			debuggerStatement(jsonArray);
		} else if ("ReturnStatement".equals(jsonArray.getStringOrNull(0))) {
			returnStatement(jsonArray);
		} else if ("DoWhileStatement".equals(jsonArray.getStringOrNull(0))) {
			doWhileStatement(jsonArray);
		} else if ("IfStatement".equals(jsonArray.getStringOrNull(0))) {
			ifStatement(jsonArray);
		} else if ("DeleteStatement".equals(jsonArray.getStringOrNull(0))) {
			deleteStatement(jsonArray);
		} else if ("SwitchStatement".equals(jsonArray.getStringOrNull(0))) {
			switchStatement(jsonArray);
		} else if ("ForStatement".equals(jsonArray.getStringOrNull(0))) {
			forStatement(jsonArray);
		} else if ("ThrowStatement".equals(jsonArray.getStringOrNull(0))) {
			throwStatement(jsonArray);
		} else if ("TryStatement".equals(jsonArray.getStringOrNull(0))) {
			tryStatement(jsonArray);
		} else if ("AssertStatement".equals(jsonArray.getStringOrNull(0))) {
			assertStatement(jsonArray);
		} else if ("ForInStatement".equals(jsonArray.getStringOrNull(0))) {
			forInStatement(jsonArray);

		} else if ("SuperExpression".equals(jsonArray.getStringOrNull(0))) {
			superExpression(jsonArray);
		} else if ("RegExpLiteralExpression".equals(jsonArray.getStringOrNull(0))) {
			regExpLiteralExpression(jsonArray);
		} else if ("AsNoConvertExpression".equals(jsonArray.getStringOrNull(0))) {
			asNoConvertExpression(jsonArray);
		} else if ("InstanceofExpression".equals(jsonArray.getStringOrNull(0))) {
			instanceofExpression(jsonArray);
		} else if ("ConditionalExpression".equals(jsonArray.getStringOrNull(0))) {
			conditionalExpression(jsonArray);
		} else if ("PostIncrementExpression".equals(jsonArray.getStringOrNull(0))) {
			postIncrementExpression(jsonArray);
		} else if ("NewExpression".equals(jsonArray.getStringOrNull(0))) {
			newExpression(jsonArray);
		} else if ("LocalExpression".equals(jsonArray.getStringOrNull(0))) {
			localExpression(jsonArray);
		} else if ("CommaExpression".equals(jsonArray.getStringOrNull(0))) {
			commaExpression(jsonArray);
		} else if ("ContinueStatement".equals(jsonArray.getStringOrNull(0))) {
			continueStatement(jsonArray);
		} else if ("UnaryExpression".equals(jsonArray.getStringOrNull(0))) {
			unaryExpression(jsonArray);
		} else if ("ThisExpression".equals(jsonArray.getStringOrNull(0))) {
			thisExpression(jsonArray);
		} else if ("PreIncrementExpression".equals(jsonArray.getStringOrNull(0))) {
			preIncrementExpression(jsonArray);
		} else if ("DefaultStatement".equals(jsonArray.getStringOrNull(0))) {
			dafaultStatement(jsonArray);
		} else if ("BreakStatement".equals(jsonArray.getStringOrNull(0))) {
			breakStatement(jsonArray);
		} else if ("CaseStatement".equals(jsonArray.getStringOrNull(0))) {
			caseStatement(jsonArray);
		} else if ("CatchStatement".equals(jsonArray.getStringOrNull(0))) {
			catchStatement(jsonArray);

		} else {
			System.out.println(jsonArray.size() + ":" + jsonArray.toString());
		}
	}

	static void dafaultStatement(JsonArray jsonArray) {
		size(1, jsonArray);
		string(0, jsonArray);
	}

	static void caseStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(2, jsonArray);
		string(0, jsonArray);
		statement(1, jsonArray);
	}

	static void expressionStatement(JsonArray jsonArray) throws IOException, JsonFormatException {
		size(2, jsonArray);
		string(0, jsonArray);
		statement(1, jsonArray);
	}

	static void returnStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(2, jsonArray);
		string(0, jsonArray);
		statement(1, jsonArray);
	}

	static void deleteStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(2, jsonArray);
		string(0, jsonArray);
		statement(1, jsonArray);
	}

	static void singleStatementLiteral(JsonArray jsonArray) throws IOException, JsonFormatException {
		size(2, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
	}

	static void debuggerStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(2, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
	}

	static void regExpLiteralExpression(JsonArray jsonArray) throws IllegalStateException,
			IOException, JsonFormatException {
		size(2, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
	}

	static void functionExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(2, jsonArray);
		string(0, jsonArray);
		member(1, jsonArray);
	}

	static void postIncrementExpression(JsonArray jsonArray) throws IllegalStateException,
			IOException, JsonFormatException {
		size(3, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		statement(2, jsonArray);
	}

	static void preIncrementExpression(JsonArray jsonArray) throws IllegalStateException,
			IOException, JsonFormatException {
		size(3, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		statement(2, jsonArray);
	}

	static void unaryExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(3, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		statement(2, jsonArray);
	}

	static void logStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(3, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		statementArray(2, jsonArray);
	}

	static void instanceofExpression(JsonArray jsonArray) throws IllegalStateException,
			IOException, JsonFormatException {
		size(3, jsonArray);
		string(0, jsonArray);
		statement(1, jsonArray);
		string(2, jsonArray);
	}

	static void asExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(3, jsonArray);
		string(0, jsonArray);
		statement(1, jsonArray);
		string(2, jsonArray);
	}

	static void continueStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(3, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		token(2, jsonArray);
	}

	static void breakStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(3, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		token(2, jsonArray);
	}

	static void commaExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(3, jsonArray);
		string(0, jsonArray);
		statement(1, jsonArray);
		statement(2, jsonArray);
	}

	static void classExpression(JsonArray jsonArray) throws IOException, JsonFormatException {
		size(3, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		string(2, jsonArray);
	}

	static void thisExpression(JsonArray jsonArray) throws IOException, JsonFormatException {
		size(3, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		string(2, jsonArray);
	}

	static void assertStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(3, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		statementArray(2, jsonArray);
	}

	static void asNoConvertExpression(JsonArray jsonArray) throws IllegalStateException,
			IOException, JsonFormatException {
		size(3, jsonArray);
		string(0, jsonArray);
		statement(1, jsonArray);
		string(2, jsonArray);
	}

	static void throwStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(3, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		statement(2, jsonArray);
	}

	static void constructorInvocationStatement(JsonArray jsonArray) throws IllegalStateException,
			IOException, JsonFormatException {
		size(3, jsonArray);
		string(0, jsonArray);
		string(1, jsonArray);
		statementArray(2, jsonArray);
	}

	static void localExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(3, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		args(2, jsonArray);
	}

	static void whileStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(4, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		statement(2, jsonArray);
		statementArray(3, jsonArray);
	}

	static void doWhileStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(4, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		statement(2, jsonArray);
		statementArray(3, jsonArray);
	}

	static void switchStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(4, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		statement(2, jsonArray);
		statementArray(3, jsonArray);
	}

	static void callExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(4, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		statement(2, jsonArray);
		statementArray(3, jsonArray);
	}

	static void arrayLiteralExpression(JsonArray jsonArray) throws IllegalStateException,
			IOException, JsonFormatException {
		size(4, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		statementArray(2, jsonArray);
		string(3, jsonArray);
	}

	static void ifStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(4, jsonArray);
		string(0, jsonArray);
		statement(1, jsonArray);
		statementArray(2, jsonArray);
		statementArray(3, jsonArray);
	}

	static void catchStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(4, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		args(2, jsonArray);
		statementArray(3, jsonArray);
	}

	static void propertyExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(4, jsonArray);
		string(0, jsonArray);
		statement(1, jsonArray);
		token(2, jsonArray);
		string(3, jsonArray);
	}

	static void binaryExpression(JsonArray jsonArray) throws IOException, JsonFormatException {
		size(4, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		statement(2, jsonArray);
		statement(3, jsonArray);
	}

	static void newExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(4, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		string(2, jsonArray);
		statementArray(3, jsonArray);
	}

	static void mapLiteralExpression(JsonArray jsonArray) throws IllegalStateException,
			IOException, JsonFormatException {
		size(4, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);

		for (int i = 0; i < jsonArray.getJsonArrayOrNull(2).size(); i++) {
			JsonArray pair = jsonArray.getJsonArrayOrNull(2).getJsonArrayOrNull(i);
			size(2, pair);
			token(0, pair);
			statement(1, pair);
		}

		string(3, jsonArray);
	}

	static void tryStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(5, jsonArray);
		string(0, jsonArray);
		statementArray(1, jsonArray);
		statementArray(2, jsonArray);
		statementArray(3, jsonArray);
		statementArray(4, jsonArray);
	}

	static void forInStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(5, jsonArray);
		string(0, jsonArray);
		statement(1, jsonArray);
		statement(2, jsonArray);
		statement(3, jsonArray);
		statementArray(4, jsonArray);
	}

	static void conditionalExpression(JsonArray jsonArray) throws IllegalStateException,
			IOException, JsonFormatException {
		size(5, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		statement(2, jsonArray);
		statement(3, jsonArray);
		statement(4, jsonArray);
	}

	static void superExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(5, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		token(2, jsonArray);
		statementArray(3, jsonArray);
		statement(4, jsonArray);
	}

	static void forStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		size(6, jsonArray);
		string(0, jsonArray);
		token(1, jsonArray);
		statement(2, jsonArray);
		statement(3, jsonArray);
		statement(4, jsonArray);
		statementArray(5, jsonArray);
	}

	static void size(int size, JsonArray jsonArray) {
		assertThat(jsonArray.size(), is(size));
	}

	static void string(int index, JsonArray jsonArray) {
		String str = jsonArray.getStringOrNull(index);
		if (str != null) {
			assertThat(str, instanceOf(String.class));
		}
	}

	static void statement(int index, JsonArray jsonArray) throws IOException, JsonFormatException {
		jsonArray = jsonArray.getJsonArrayOrNull(index);
		parseStatement(jsonArray);
	}

	static void statementArray(int index, JsonArray jsonArray) throws IllegalStateException,
			IOException, JsonFormatException {
		jsonArray = jsonArray.getJsonArrayOrNull(index);
		if (jsonArray == null) {
			return;
		}
		for (int i = 0; i < jsonArray.size(); i++) {
			parseStatement(jsonArray.getJsonArrayOrNull(i));
		}
	}

	static void token(int index, JsonArray jsonArray) throws IOException, JsonFormatException {
		jsonArray = jsonArray.getJsonArrayOrNull(index);
		if (jsonArray == null) {
			return;
		}
		JsonPullParser parser = JsonPullParser.newParser(jsonArray.toString());
		Token token = JsxTokenConverter.getInstance().parse(parser, null);
		assertThat(token, notNullValue());
	}

	static void args(int index, JsonArray jsonArray) throws IOException, JsonFormatException {
		jsonArray = jsonArray.getJsonArrayOrNull(index);
		JsonPullParser parser = JsonPullParser.newParser(jsonArray.toString());
		Args args = ArgsConverter.getInstance().parse(parser, null);
		assertThat(args, notNullValue());
	}

	static void member(int index, JsonArray jsonArray) throws IOException, JsonFormatException {
		JsonHash jsonHash = jsonArray.getJsonHashOrNull(index);
		Member member = MemberGen.get(jsonHash.toString());
		assertThat(member, notNullValue());
	}
}
