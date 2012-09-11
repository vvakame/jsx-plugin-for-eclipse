package net.vvakame.jsx.peg;

import java.util.ArrayList;
import java.util.List;

import mouse.runtime.ParserBase;
import mouse.runtime.Phrase;
import mouse.runtime.PhraseExtend;
import net.vvakame.ide.jsx.parser.SyntaxTree;

public class SyntaxTreeMouseImpl implements SyntaxTree {

	SyntaxTree parent;
	List<SyntaxTree> children = new ArrayList<SyntaxTree>();
	String type;
	String name;
	int index;
	int line;
	int column;

	SyntaxTreeMouseImpl() {
	}

	void init(JsxSemantics sem) {
		ParserBase.Phrase phrase = (mouse.runtime.ParserBase.Phrase) sem.lhs();
		PhraseExtend extend = PhraseExtend.get((ParserBase.Phrase) phrase);
		this.index = extend.getStart();
		this.type = phrase.rule();
		if ("space".equals(this.type)) {
			this.name = sem.lhs().text();
		} else {
			this.name = sem.lhs().text().trim();
		}
	}

	void init(Phrase phrase) {
		if (phrase instanceof ParserBase.Phrase) {
			PhraseExtend extend = PhraseExtend.get((ParserBase.Phrase) phrase);
			this.index = extend.getStart();
			this.type = phrase.rule();
			this.name = phrase.text();
		} else {
			throw new IllegalArgumentException("rule is "
					+ phrase.getClass().getCanonicalName());
		}
	}

	@Override
	public SyntaxTree parent() {
		return this.parent;
	}

	@Override
	public List<SyntaxTree> children() {
		return this.children;
	}

	@Override
	public String type() {
		return this.type;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public int index() {
		return this.index;
	}

	@Override
	public int line() {
		return this.line;
	}

	@Override
	public int column() {
		return this.column;
	}

	@Override
	public String toString() {
		return "SyntaxTreeMouseImpl [type=" + type + ", name=" + name
				+ ", index=" + index + ", children=" + children + "]";
	}
}
