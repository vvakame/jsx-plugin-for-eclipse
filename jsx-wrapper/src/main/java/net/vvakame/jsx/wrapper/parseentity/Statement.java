package net.vvakame.jsx.wrapper.parseentity;

import java.util.ArrayList;
import java.util.List;

/**
 * Statement in JSX AST.
 * @author vvakame
 */
public class Statement {

	String name;

	List<StatementWrapper> payload = new ArrayList<StatementWrapper>();


	void add(String str) {
		payload.add(new StatementWrapper(str));
	}

	void add(Token token) {
		payload.add(new StatementWrapper(token));
	}

	void add(Statement statement) {
		payload.add(new StatementWrapper(statement));
	}

	void add(List<Statement> statements) {
		payload.add(new StatementWrapper(statements));
	}

	void add(Args args) {
		payload.add(new StatementWrapper(args));
	}

	void add(Member member) {
		payload.add(new StatementWrapper(member));
	}


	/**
	 * Statment payload element.
	 * @author vvakame
	 */
	public static class StatementWrapper {

		/** value of String */
		public final String str;

		/** value of Token */
		public final Token token;

		/** value of Statement */
		public final Statement statement;

		/** value of Statement list */
		public final List<Statement> statements;

		/** value of args */
		public final Args args;

		/** value of member */
		public final Member member;


		/**
		 * the constructor.
		 * @param str
		 * @category constructor
		 */
		StatementWrapper(String str) {
			this.str = str;
			this.token = null;
			this.statement = null;
			this.statements = null;
			this.args = null;
			this.member = null;
		}

		/**
		 * the constructor.
		 * @param token
		 * @category constructor
		 */
		StatementWrapper(Token token) {
			this.str = null;
			this.token = token;
			this.statement = null;
			this.statements = null;
			this.args = null;
			this.member = null;
		}

		/**
		 * the constructor.
		 * @param statement
		 * @category constructor
		 */
		StatementWrapper(Statement statement) {
			this.str = null;
			this.token = null;
			this.statement = statement;
			this.statements = null;
			this.args = null;
			this.member = null;
		}

		/**
		 * the constructor.
		 * @param statements
		 * @category constructor
		 */
		StatementWrapper(List<Statement> statements) {
			this.str = null;
			this.token = null;
			this.statement = null;
			this.statements = statements;
			this.args = null;
			this.member = null;
		}

		/**
		 * the constructor.
		 * @param args
		 * @category constructor
		 */
		StatementWrapper(Args args) {
			this.str = null;
			this.token = null;
			this.statement = null;
			this.statements = null;
			this.args = args;
			this.member = null;
		}

		/**
		 * the constructor.
		 * @param member
		 * @category constructor
		 */
		StatementWrapper(Member member) {
			this.str = null;
			this.token = null;
			this.statement = null;
			this.statements = null;
			this.args = null;
			this.member = member;
		}

		@Override
		public String toString() {
			return "StatementWrapper [str=" + str + ", token=" + token + ", statement=" + statement
					+ ", statements=" + statements + ", args=" + args + ", member=" + member + "]";
		}
	}


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
	 * @return the payload
	 * @category accessor
	 */
	public List<StatementWrapper> getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 * @category accessor
	 */
	public void setPayload(List<StatementWrapper> payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "Statement [name=" + name + ", payload=" + payload + "]";
	}
}
