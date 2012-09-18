package net.vvakame.jsx.wrapper.entity;

import java.io.IOException;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import net.vvakame.util.jsonpullparser.util.OnJsonObjectAddListener;
import net.vvakame.util.jsonpullparser.util.TokenConverter;

/**
 * for [ "NumberLiteralExpression",
 * ["2.718281828459045",false,"./lib/built-in.jsx",696,18]]
 * 
 * @author vvakame
 */
public class ArgsConverter extends TokenConverter<Args> {

	static ArgsConverter converter;
	static JsxTokenConverter tokenConverter;

	public static ArgsConverter getInstance() {
		if (converter != null) {
			return converter;
		}
		tokenConverter = new JsxTokenConverter();
		converter = new ArgsConverter();
		return converter;
	}

	@Override
	public Args parse(JsonPullParser parser, OnJsonObjectAddListener listener)
			throws IOException, JsonFormatException {

		if (parser == null) {
			throw new IllegalArgumentException();
		}

		State state = parser.getEventType();

		switch (state) {
		case VALUE_NULL:
			return null;
		case START_ARRAY:
			Args args = new Args();

			if (parser.lookAhead() == State.VALUE_STRING) {
				{
					if (parser.getEventType() != State.VALUE_STRING) {
						throw new JsonFormatException("value is not String");
					}
					args.setType(parser.getValueString());
				}
				Token token = tokenConverter.parse(parser, listener);
				args.setToken(token);
			} else {
				Token token = tokenConverter.parse(parser, listener);
				args.setToken(token);
				{
					if (parser.getEventType() != State.VALUE_STRING) {
						throw new JsonFormatException("value is not String");
					}
					args.setType(parser.getValueString());
				}
			}
			{
				State eventType = parser.getEventType();
				if (eventType != State.END_ARRAY) {
					throw new JsonFormatException("value is not end array");
				}
			}

			return args;
		default:
			throw new JsonFormatException("unknown state " + state);
		}
	}
}
