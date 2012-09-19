package net.vvakame.jsx.wrapper.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import net.vvakame.util.jsonpullparser.util.OnJsonObjectAddListener;
import net.vvakame.util.jsonpullparser.util.TokenConverter;

public class ArgsListConverter extends TokenConverter<List<Args>> {

	static ArgsListConverter converter;

	static ArgsConverter argsConverter;


	public static ArgsListConverter getInstance() {
		if (converter != null) {
			return converter;
		}
		argsConverter = new ArgsConverter();
		converter = new ArgsListConverter();
		return converter;
	}

	@Override
	public List<Args> parse(JsonPullParser parser, OnJsonObjectAddListener listener)
			throws IOException, JsonFormatException {

		if (parser == null) {
			throw new IllegalArgumentException();
		}

		State state = parser.getEventType();

		switch (state) {
			case VALUE_NULL:
				return null;
			case START_ARRAY:
				List<Args> result = new ArrayList<Args>();

				while (parser.lookAhead() != State.END_ARRAY) {
					Args args = parseArgs(parser, listener);
					result.add(args);
				}
				parser.getEventType();

				return result;
			default:
				throw new JsonFormatException("unknown state " + state);
		}
	}

	Args parseArgs(JsonPullParser parser, OnJsonObjectAddListener listener) throws IOException,
			JsonFormatException {

		if (parser == null) {
			throw new IllegalArgumentException();
		}

		State state = parser.getEventType();

		switch (state) {
			case VALUE_NULL:
				return null;
			case START_ARRAY:
				Args args = new Args();

				Token token = TokenGen.get(parser, listener);
				args.setToken(token);

				{
					State eventType = parser.getEventType();
					if (eventType == State.VALUE_NULL) {
					} else if (eventType == State.VALUE_STRING) {
						args.setType(parser.getValueString());
					} else {
						throw new JsonFormatException("value is not String, " + eventType);
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
