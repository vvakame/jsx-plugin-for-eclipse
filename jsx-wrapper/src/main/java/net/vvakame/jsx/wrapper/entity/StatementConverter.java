package net.vvakame.jsx.wrapper.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import net.vvakame.util.jsonpullparser.util.OnJsonObjectAddListener;
import net.vvakame.util.jsonpullparser.util.TokenConverter;

/**
 * {@link TokenConverter} for {@link Statement}.
 * @author vvakame
 */
class StatementConverter extends TokenConverter<Statement> {

	static StatementConverter converter;


	/**
	 * get instance.
	 * @return {@link StatementConverter}
	 * @author vvakame
	 */
	public static StatementConverter getInstance() {
		if (converter != null) {
			return converter;
		}
		converter = new StatementConverter();
		return converter;
	}

	@Override
	public Statement parse(JsonPullParser parser, OnJsonObjectAddListener listener)
			throws IOException, JsonFormatException {

		if (parser == null) {
			throw new IllegalArgumentException();
		}

		Statement statement = new Statement();

		State state;

		state = parser.getEventType();
		if (state == State.VALUE_NULL) {
			return null;
		} else if (state != State.START_ARRAY) {
			throw new JsonFormatException("statement must start with [. but " + state);
		}

		state = parser.getEventType();
		if (state != State.VALUE_STRING) {
			throw new JsonFormatException("unexpected " + state);
		}

		final String name = parser.getValueString();
		statement.setName(name);

		parsePayload(name, statement, parser, listener);

		if (parser.getEventType() != State.END_ARRAY) {
			throw new JsonFormatException("statement must end with ]");
		}

		return statement;
	}

	void parsePayload(String name, Statement statement, JsonPullParser parser,
			OnJsonObjectAddListener listener) throws JsonFormatException, IOException {
		if ("NumberLiteralExpression".equals(name)) {
			token(statement, parser);
		} else if ("IntegerLiteralExpression".equals(name)) {
			token(statement, parser);
		} else if ("StringLiteralExpression".equals(name)) {
			token(statement, parser);
		} else if ("BooleanLiteralExpression".equals(name)) {
			token(statement, parser);
		} else if ("BinaryExpression".equals(name)) {
			token(statement, parser);
			statement(statement, parser);
			statement(statement, parser);
		} else if ("AsExpression".equals(name)) {
			statement(statement, parser);
			string(statement, parser);
		} else if ("ClassExpression".equals(name)) {
			token(statement, parser);
			string(statement, parser);
		} else if ("NullExpression".equals(name)) {
			token(statement, parser);
			string(statement, parser);
		} else if ("CallExpression".equals(name)) {
			token(statement, parser);
			statement(statement, parser);
			statementArray(statement, parser);
		} else if ("ArrayLiteralExpression".equals(name)) {
			token(statement, parser);
			statementArray(statement, parser);
			string(statement, parser);
		} else if ("PropertyExpression".equals(name)) {
			statement(statement, parser);
			token(statement, parser);
			string(statement, parser);
		} else if ("FunctionExpression".equals(name)) {
			member(statement, parser);
		} else if ("MapLiteralExpression".equals(name)) {
			token(statement, parser);
			mapKeyValuePair(statement, parser);
			string(statement, parser);
		} else if ("ExpressionStatement".equals(name)) {
			statement(statement, parser);
		} else if ("WhileStatement".equals(name)) {
			token(statement, parser);
			statement(statement, parser);
			statementArray(statement, parser);
		} else if ("ConstructorInvocationStatement".equals(name)) {
			string(statement, parser);
			statementArray(statement, parser);
		} else if ("LogStatement".equals(name)) {
			token(statement, parser);
			statementArray(statement, parser);
		} else if ("DebuggerStatement".equals(name)) {
			token(statement, parser);
		} else if ("ReturnStatement".equals(name)) {
			statement(statement, parser);
		} else if ("DoWhileStatement".equals(name)) {
			token(statement, parser);
			statement(statement, parser);
			statementArray(statement, parser);
		} else if ("IfStatement".equals(name)) {
			statement(statement, parser);
			statementArray(statement, parser);
			statementArray(statement, parser);
		} else if ("DeleteStatement".equals(name)) {
			statement(statement, parser);
		} else if ("SwitchStatement".equals(name)) {
			token(statement, parser);
			statement(statement, parser);
			statementArray(statement, parser);
		} else if ("ForStatement".equals(name)) {
			token(statement, parser);
			statement(statement, parser);
			statement(statement, parser);
			statement(statement, parser);
			statementArray(statement, parser);
		} else if ("ThrowStatement".equals(name)) {
			token(statement, parser);
			statement(statement, parser);
		} else if ("TryStatement".equals(name)) {
			statementArray(statement, parser);
			statementArray(statement, parser);
			statementArray(statement, parser);
			statementArray(statement, parser);
		} else if ("AssertStatement".equals(name)) {
			token(statement, parser);
			statementArray(statement, parser);
		} else if ("ForInStatement".equals(name)) {
			statement(statement, parser);
			statement(statement, parser);
			statement(statement, parser);
			statementArray(statement, parser);
		} else if ("SuperExpression".equals(name)) {
			token(statement, parser);
			token(statement, parser);
			statementArray(statement, parser);
			statement(statement, parser);
		} else if ("RegExpLiteralExpression".equals(name)) {
			token(statement, parser);
		} else if ("AsNoConvertExpression".equals(name)) {
			statement(statement, parser);
			string(statement, parser);
		} else if ("InstanceofExpression".equals(name)) {
			statement(statement, parser);
			string(statement, parser);
		} else if ("ConditionalExpression".equals(name)) {
			token(statement, parser);
			statement(statement, parser);
			statement(statement, parser);
			statement(statement, parser);
		} else if ("PostIncrementExpression".equals(name)) {
			token(statement, parser);
			statement(statement, parser);
		} else if ("NewExpression".equals(name)) {
			token(statement, parser);
			string(statement, parser);
			statementArray(statement, parser);
		} else if ("LocalExpression".equals(name)) {
			token(statement, parser);
			args(statement, parser);
		} else if ("CommaExpression".equals(name)) {
			statement(statement, parser);
			statement(statement, parser);
		} else if ("ContinueStatement".equals(name)) {
			token(statement, parser);
			token(statement, parser);
		} else if ("UnaryExpression".equals(name)) {
			token(statement, parser);
			statement(statement, parser);
		} else if ("ThisExpression".equals(name)) {
			token(statement, parser);
			string(statement, parser);
		} else if ("PreIncrementExpression".equals(name)) {
			token(statement, parser);
			statement(statement, parser);
		} else if ("DefaultStatement".equals(name)) {
		} else if ("BreakStatement".equals(name)) {
			token(statement, parser);
			token(statement, parser);
		} else if ("CaseStatement".equals(name)) {
			statement(statement, parser);
		} else if ("CatchStatement".equals(name)) {
			token(statement, parser);
			args(statement, parser);
			statementArray(statement, parser);
		} else {
			throw new JsonFormatException("unknown name " + name);
		}
	}

	void string(Statement statement, JsonPullParser parser) throws JsonFormatException, IOException {
		State state = parser.getEventType();
		if (state == State.VALUE_NULL) {
			statement.add((String) null);
		} else if (state == State.VALUE_STRING) {
			statement.add(parser.getValueString());
		} else {
			throw new JsonFormatException("unexpected state " + state);
		}
	}

	void statement(Statement statement, JsonPullParser parser) throws IOException,
			JsonFormatException {
		statement.add(parse(parser, null));
	}

	void statementArray(Statement statement, JsonPullParser parser) throws IOException,
			JsonFormatException {
		State state = parser.getEventType();
		if (state == State.VALUE_NULL) {
			statement.add((List<Statement>) null);
			return;
		} else if (state != State.START_ARRAY) {
			throw new JsonFormatException("unexpected state " + state);
		}

		List<Statement> statmentList = new ArrayList<Statement>();
		while (parser.lookAhead() != State.END_ARRAY) {
			statmentList.add(parse(parser, null));
		}
		statement.add(statmentList);
		parser.getEventType();
	}

	void token(Statement statement, JsonPullParser parser) throws IOException, JsonFormatException {
		State state = parser.lookAhead();
		if (state == State.VALUE_NULL) {
			statement.add((Token) null);
			parser.getEventType();
			return;
		}
		statement.add(JsxTokenConverter.getInstance().parse(parser, null));
	}

	void args(Statement statement, JsonPullParser parser) throws IOException, JsonFormatException {
		State state = parser.lookAhead();
		if (state == State.VALUE_NULL) {
			statement.add((Args) null);
			parser.getEventType();
			return;
		}
		statement.add(ArgsConverter.getInstance().parse(parser, null));
	}

	void member(Statement statement, JsonPullParser parser) throws IllegalStateException,
			IOException, JsonFormatException {
		Member member = MemberGen.get(parser, null);
		statement.add(member);
	}

	void mapKeyValuePair(Statement statement, JsonPullParser parser) throws IllegalStateException,
			IOException, JsonFormatException {
		// TODO please implement.
		parser.discardArrayToken();
	}
}
