package net.vvakame.jsx.wrapper.parseentity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import net.vvakame.util.jsonpullparser.util.OnJsonObjectAddListener;
import net.vvakame.util.jsonpullparser.util.TokenConverter;

/**
 * {@link TokenConverter} for {@link List} of {@link Statement}.
 * @author vvakame
 */
class StatementListConverter extends TokenConverter<List<Statement>> {

	static StatementListConverter converter;


	/**
	 * get instance.
	 * @return {@link StatementListConverter}
	 * @author vvakame
	 */
	public static StatementListConverter getInstance() {
		if (converter != null) {
			return converter;
		}
		converter = new StatementListConverter();
		return converter;
	}

	@Override
	public List<Statement> parse(JsonPullParser parser, OnJsonObjectAddListener listener)
			throws IOException, JsonFormatException {

		if (parser == null) {
			throw new IllegalArgumentException();
		}

		State state;

		state = parser.getEventType();
		if (state == State.VALUE_NULL) {
			return null;
		} else if (state != State.START_ARRAY) {
			throw new JsonFormatException("statement must start with [. but " + state);
		}

		StatementConverter statementConverter = StatementConverter.getInstance();

		List<Statement> statementList = new ArrayList<Statement>();
		while (parser.lookAhead() != State.END_ARRAY) {
			statementList.add(statementConverter.parse(parser, listener));
		}
		parser.getEventType();

		return statementList;
	}
}
