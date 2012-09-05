package net.vvakame.ide.jsx.parser.antlr;

import java.util.HashMap;
import java.util.Map;

import net.vvakame.ide.jsx.parser.SourceInfo;

public class SourceInfoAntlrImpl implements SourceInfo {
	public final String src;

	final Map<Integer, Integer> lengthMemo = new HashMap<Integer, Integer>();
	String lines[];

	public SourceInfoAntlrImpl(String src) {
		this.src = src;
	}

	@Override
	public int getIndex(int line, int column) {
		if (lines == null) {
			lines = src.split("(\r\n|\r|\n)");
		}
		if (lines.length < line) {
			return getIndex(line - 1, column);
		}

		if (line == 1) {
			return column;
		} else {
			int lineTotal = getTotalLength(line - 1);
			return lineTotal + column;
		}
	}

	private int getTotalLength(final int includeLine) {
		{
			Integer lineFirst = lengthMemo.get(includeLine);
			if (lineFirst != null) {
				return lineFirst;
			}
		}

		if (includeLine == 1) {
			int len = lines[0].length() + 1;
			lengthMemo.put(includeLine, len);
			return len;
		} else {
			int beforeLine = getTotalLength(includeLine - 1);
			int thisLine = beforeLine + lines[includeLine - 1].length() + 1;
			lengthMemo.put(includeLine, thisLine);
			return thisLine;
		}
	}
}