package net.vvakame.jsx.wrapper.parseentity;

import java.io.IOException;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import net.vvakame.util.jsonpullparser.util.OnJsonObjectAddListener;
import net.vvakame.util.jsonpullparser.util.TokenConverter;

/**
 * {@link TokenConverter} for {@link Token}.
 * @author vvakame
 */
class JsxTokenConverter extends TokenConverter<Token> {

	static JsxTokenConverter converter;


	/**
	 * get instance.
	 * @return {@link JsxTokenConverter}
	 * @author vvakame
	 */
	public static JsxTokenConverter getInstance() {
		if (converter != null) {
			return converter;
		}
		converter = new JsxTokenConverter();
		return converter;
	}

	@Override
	public Token parse(JsonPullParser parser, OnJsonObjectAddListener listener) throws IOException,
			JsonFormatException {

		if (parser == null) {
			throw new IllegalArgumentException();
		}

		if (parser.lookAhead() == State.START_HASH) {
			return TokenGen.get(parser, listener);
		}

		State state = parser.getEventType();

		switch (state) {
			case VALUE_NULL:
				return null;
			case START_ARRAY:
				Token token = new Token();

				{
					State eventType = parser.getEventType();
					if (eventType != State.VALUE_STRING) {
						throw new JsonFormatException("1st value is not String");
					}
					token.setValue(parser.getValueString());
				}
				{
					State eventType = parser.getEventType();
					if (eventType != State.VALUE_BOOLEAN) {
						throw new JsonFormatException("2nd value is not boolean");
					}
					token.setIdentifier(parser.getValueBoolean());
				}
				{
					State eventType = parser.getEventType();
					if (eventType == State.VALUE_NULL) {
					} else if (eventType == State.VALUE_STRING) {
						token.setFilename(parser.getValueString());
					} else {
						throw new JsonFormatException("3rd value is not String");
					}
				}
				{
					State eventType = parser.getEventType();
					if (eventType == State.VALUE_NULL) {
					} else if (eventType == State.VALUE_LONG) {
						token.setLineNumber((int) parser.getValueLong());
					} else {
						throw new JsonFormatException("4th value is not integer");
					}
				}
				{
					State eventType = parser.getEventType();
					if (eventType == State.VALUE_NULL) {
					} else if (eventType == State.VALUE_LONG) {
						token.setColumnNumber((int) parser.getValueLong());
					} else {
						throw new JsonFormatException("5th value is not integer");
					}
				}
				{
					State eventType = parser.getEventType();
					if (eventType != State.END_ARRAY) {
						throw new JsonFormatException("6th value is not end array");
					}
				}
				return token;
			default:
				throw new JsonFormatException("unknown state " + state);
		}
	}
}
