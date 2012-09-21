package net.vvakame.jsx.wrapper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.vvakame.jsx.wrapper.Jsx.Builder;
import net.vvakame.jsx.wrapper.Jsx.Executable;
import net.vvakame.jsx.wrapper.Jsx.Mode;
import net.vvakame.jsx.wrapper.entity.Args;
import net.vvakame.jsx.wrapper.entity.ArgsConverter;
import net.vvakame.jsx.wrapper.entity.ClassDefinition;
import net.vvakame.jsx.wrapper.entity.Complete;
import net.vvakame.jsx.wrapper.entity.JsxTokenConverter;
import net.vvakame.jsx.wrapper.entity.Member;
import net.vvakame.jsx.wrapper.entity.MemberGen;
import net.vvakame.jsx.wrapper.entity.Token;
import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.util.JsonArray;
import net.vvakame.util.jsonpullparser.util.JsonHash;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Test for {@link Jsx}
 * @author vvakame
 */
public class JsxTest {

	// hint: if remove process.waitFor(),
	// flush process.getInputStream() or process.getErrorStream()

	/**
	 * Test for {@link Jsx#exec(net.vvakame.jsx.wrapper.Jsx.Args)}
	 * @throws IOException
	 * @throws InterruptedException
	 * @author vvakame
	 */
	@Test
	public void exec() throws IOException, InterruptedException {
		Builder builder = new Jsx.Builder();
		builder.setNodeJsPath("/opt/local/bin/node");
		builder.setJsxPath(getJsxPath());

		builder.help(true);

		Jsx jsx = Jsx.getInstance();

		Process process = jsx.exec(builder.build());
		process.waitFor();

		assertThat(process.exitValue(), is(0));
	}

	/**
	 * Test for {@link Jsx#parse(net.vvakame.jsx.wrapper.Jsx.Args)}
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void parse() throws IOException, InterruptedException, JsonFormatException {
		{
			Builder builder = makeDefault();
			builder.jsxSource(getGitRootDirectory().getAbsolutePath() + "/JSX/t/run/001.hello.jsx");

			Jsx jsx = Jsx.getInstance();

			List<ClassDefinition> astList = jsx.parse(builder.build());

			assertThat(astList.size(), is(not(0)));
		}

		{
			File gitRoot = getGitRootDirectory();

			String[] jsxExistsDirPaths = {
				"JSX/t/run/",
				"JSX/t/lib/",
				"JSX/t/optimize/",
				"JSX/t/source-map/"
			};

			for (String dirPath : jsxExistsDirPaths) {
				File dir = new File(gitRoot, dirPath);
				File[] jsxFiles = dir.listFiles(new FilenameFilter() {

					@Override
					public boolean accept(File file, String name) {
						return name.endsWith(".jsx");
					}
				});

				for (File file : jsxFiles) {
					Builder builder = makeDefault();
					builder.jsxSource(file.getAbsolutePath());

					Jsx jsx = Jsx.getInstance();

					List<ClassDefinition> astList = jsx.parse(builder.build());
					for (ClassDefinition classDefinition : astList) {
						List<Member> members = classDefinition.getMembers();
						for (Member member : members) {
							parseStatement(member.getInitialValue());
							if (member.getStatements() == null) {
								continue;
							}
							for (int i = 0; i < member.getStatements().size(); i++) {
								JsonArray statement = member.getStatements().getJsonArrayOrNull(i);
								parseStatement(statement);
							}
						}
					}

					assertThat(file.getAbsolutePath(), astList.size(), is(not(0)));
				}
			}
		}
	}

	void parseStatement(JsonArray jsonArray) throws IOException, JsonFormatException {
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

	void dafaultStatement(JsonArray jsonArray) {
		assertThat(jsonArray.size(), is(1));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
	}

	void caseStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(2));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		parseStatement(jsonArray.getJsonArrayOrNull(1));
	}

	void singleStatementLiteral(JsonArray jsonArray) throws IOException, JsonFormatException {
		assertThat(jsonArray.size(), is(2));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
	}

	void expressionStatement(JsonArray jsonArray) throws IOException, JsonFormatException {
		assertThat(jsonArray.size(), is(2));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		parseStatement(jsonArray.getJsonArrayOrNull(1));
	}

	void debuggerStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(2));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
	}

	void deleteStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(2));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		parseStatement(jsonArray.getJsonArrayOrNull(1));
	}

	void functionExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(2));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		member(jsonArray.getJsonHashOrNull(1));
	}

	void returnStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(2));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		parseStatement(jsonArray.getJsonArrayOrNull(1));
	}

	void regExpLiteralExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(2));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
	}

	void postIncrementExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(3));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
		parseStatement(jsonArray.getJsonArrayOrNull(2));
	}

	void preIncrementExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(3));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
		parseStatement(jsonArray.getJsonArrayOrNull(2));
	}

	void logStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(3));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
		parseStatementArray(jsonArray.getJsonArrayOrNull(2));
	}

	void instanceofExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(3));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		parseStatement(jsonArray.getJsonArrayOrNull(1));
		assertThat(jsonArray.getStringOrNull(2), instanceOf(String.class));
	}

	void asExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(3));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		parseStatement(jsonArray.getJsonArrayOrNull(1));
		assertThat(jsonArray.getStringOrNull(2), instanceOf(String.class));
	}

	void unaryExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(3));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
		parseStatement(jsonArray.getJsonArrayOrNull(2));
	}

	void continueStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(3));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
		token(jsonArray.getJsonArrayOrNull(2));
	}

	void commaExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(3));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		parseStatement(jsonArray.getJsonArrayOrNull(1));
		parseStatement(jsonArray.getJsonArrayOrNull(2));
	}

	void classExpression(JsonArray jsonArray) throws IOException, JsonFormatException {
		assertThat(jsonArray.size(), is(3));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
		assertThat(jsonArray.getStringOrNull(2), instanceOf(String.class));
	}

	void thisExpression(JsonArray jsonArray) throws IOException, JsonFormatException {
		assertThat(jsonArray.size(), is(3));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
		@SuppressWarnings("unused")
		String stringOrNull = jsonArray.getStringOrNull(2);
	}

	void breakStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(3));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
		token(jsonArray.getJsonArrayOrNull(2));
	}

	void assertStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(3));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
		parseStatementArray(jsonArray.getJsonArrayOrNull(2));
	}

	void asNoConvertExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(3));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		parseStatement(jsonArray.getJsonArrayOrNull(1));
		assertThat(jsonArray.getStringOrNull(2), instanceOf(String.class));
	}

	void throwStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(3));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
		parseStatement(jsonArray.getJsonArrayOrNull(2));
	}

	void constructorInvocationStatement(JsonArray jsonArray) throws IllegalStateException,
			IOException, JsonFormatException {
		assertThat(jsonArray.size(), is(3));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		assertThat(jsonArray.getStringOrNull(1), instanceOf(String.class));
		parseStatementArray(jsonArray.getJsonArrayOrNull(2));
	}

	void localExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(3));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
		args(jsonArray.getJsonArrayOrNull(2));
	}

	void whileStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(4));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
		parseStatement(jsonArray.getJsonArrayOrNull(2));
		parseStatementArray(jsonArray.getJsonArrayOrNull(3));
	}

	void doWhileStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(4));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
		parseStatement(jsonArray.getJsonArrayOrNull(2));
		parseStatementArray(jsonArray.getJsonArrayOrNull(3));
	}

	void arrayLiteralExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(4));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
		parseStatementArray(jsonArray.getJsonArrayOrNull(2));
		@SuppressWarnings("unused")
		String stringOrNull = jsonArray.getStringOrNull(3);
	}

	void ifStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(4));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		parseStatement(jsonArray.getJsonArrayOrNull(1));
		parseStatementArray(jsonArray.getJsonArrayOrNull(2));
		parseStatementArray(jsonArray.getJsonArrayOrNull(3));
	}

	void catchStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(4));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
		args(jsonArray.getJsonArrayOrNull(2));
		parseStatementArray(jsonArray.getJsonArrayOrNull(3));
	}

	void propertyExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(4));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		parseStatement(jsonArray.getJsonArrayOrNull(1));
		token(jsonArray.getJsonArrayOrNull(2));
		@SuppressWarnings("unused")
		String stringOrNull = jsonArray.getStringOrNull(3);
	}

	void binaryExpression(JsonArray jsonArray) throws IOException, JsonFormatException {
		assertThat(jsonArray.size(), is(4));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		assertThat(jsonArray.getJsonArrayOrNull(1), instanceOf(JsonArray.class));
		token(jsonArray.getJsonArrayOrNull(1));

		parseStatement(jsonArray.getJsonArrayOrNull(2));
		parseStatement(jsonArray.getJsonArrayOrNull(3));
	}

	void switchStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(4));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
		parseStatement(jsonArray.getJsonArrayOrNull(2));
		parseStatementArray(jsonArray.getJsonArrayOrNull(3));
	}

	void newExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(4));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
		assertThat(jsonArray.getStringOrNull(2), instanceOf(String.class));
		parseStatementArray(jsonArray.getJsonArrayOrNull(3));
	}

	void mapLiteralExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(4));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));

		for (int i = 0; i < jsonArray.getJsonArrayOrNull(2).size(); i++) {
			JsonArray pair = jsonArray.getJsonArrayOrNull(2).getJsonArrayOrNull(i);
			assertThat("2以外ないの？謎", pair.size(), is(2));
			assertThat(pair.size(), is(2));
			token(pair.getJsonArrayOrNull(0));
			parseStatement(pair.getJsonArrayOrNull(1));
		}

		@SuppressWarnings("unused")
		String stringOrNull = jsonArray.getStringOrNull(3);
	}

	void callExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(4));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
		parseStatement(jsonArray.getJsonArrayOrNull(2));
		parseStatementArray(jsonArray.getJsonArrayOrNull(3));
	}

	void tryStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(5));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		parseStatementArray(jsonArray.getJsonArrayOrNull(1));
		parseStatementArray(jsonArray.getJsonArrayOrNull(2));
		parseStatementArray(jsonArray.getJsonArrayOrNull(3));
		parseStatementArray(jsonArray.getJsonArrayOrNull(4));
	}

	void forInStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(5));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		parseStatement(jsonArray.getJsonArrayOrNull(1));
		parseStatement(jsonArray.getJsonArrayOrNull(2));
		parseStatement(jsonArray.getJsonArrayOrNull(3));
		parseStatementArray(jsonArray.getJsonArrayOrNull(4));
	}

	void conditionalExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(5));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
		parseStatement(jsonArray.getJsonArrayOrNull(2));
		parseStatement(jsonArray.getJsonArrayOrNull(3));
		parseStatement(jsonArray.getJsonArrayOrNull(4));
	}

	void superExpression(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(5));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
		token(jsonArray.getJsonArrayOrNull(2));
		parseStatementArray(jsonArray.getJsonArrayOrNull(3));
		parseStatement(jsonArray.getJsonArrayOrNull(4));
	}

	void forStatement(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		assertThat(jsonArray.size(), is(6));
		assertThat(jsonArray.getStringOrNull(0), instanceOf(String.class));
		token(jsonArray.getJsonArrayOrNull(1));
		parseStatement(jsonArray.getJsonArrayOrNull(2));
		parseStatement(jsonArray.getJsonArrayOrNull(3));
		parseStatement(jsonArray.getJsonArrayOrNull(4));
		parseStatementArray(jsonArray.getJsonArrayOrNull(5));
	}

	void parseStatementArray(JsonArray jsonArray) throws IllegalStateException, IOException,
			JsonFormatException {
		if (jsonArray == null) {
			return;
		}
		for (int i = 0; i < jsonArray.size(); i++) {
			parseStatement(jsonArray.getJsonArrayOrNull(i));
		}
	}

	void token(JsonArray jsonArray) throws IOException, JsonFormatException {
		if (jsonArray == null) {
			return;
		}
		JsonPullParser parser = JsonPullParser.newParser(jsonArray.toString());
		Token token = JsxTokenConverter.getInstance().parse(parser, null);
		assertThat(token, notNullValue());
	}

	void args(JsonArray jsonArray) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(jsonArray.toString());
		Args args = ArgsConverter.getInstance().parse(parser, null);
		assertThat(args, notNullValue());
	}

	void member(JsonHash jsonHash) throws IOException, JsonFormatException {
		Member member = MemberGen.get(jsonHash.toString());
		assertThat(member, notNullValue());

		parseStatement(member.getInitialValue());
		if (member.getStatements() == null) {
			return;
		}
		for (int i = 0; i < member.getStatements().size(); i++) {
			JsonArray statement = member.getStatements().getJsonArrayOrNull(i);
			parseStatement(statement);
		}
	}

	/**
	 * Test for {@link Jsx#complete(net.vvakame.jsx.wrapper.Jsx.Args, int, int)}
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void complete() throws IOException, InterruptedException, JsonFormatException {

		Builder builder = makeDefault();
		builder.jsxSource(getGitRootDirectory().getAbsolutePath() + "/JSX/t/lib/001.hello.jsx");

		Jsx jsx = Jsx.getInstance();

		List<Complete> completeList = jsx.complete(builder.build(), 6, 4);
		// System.out.println(completeList.toString());

		assertThat(completeList, notNullValue());
	}

	/**
	 * Test for plane {@link Builder}.
	 * @throws IOException
	 * @throws InterruptedException
	 * @author vvakame
	 */
	@Test
	public void noArgs() throws IOException, InterruptedException {
		Builder builder = makeDefault();

		Jsx jsx = Jsx.getInstance();
		Process process = jsx.exec(builder.build());
		process.waitFor();

		assertThat(process.exitValue(), is(1));
	}

	/**
	 * Test for {@link Builder#executable(Executable)} with {@link Executable#Web}
	 * @throws IOException
	 * @throws InterruptedException
	 * @author vvakame
	 */
	@Test
	public void executableWeb() throws IOException, InterruptedException {
		Builder builder = makeDefault();
		builder.executable(Executable.Web);
		builder.jsxSource(getGitRootDirectory().getAbsolutePath() + "/JSX/t/lib/001.hello.jsx");

		Jsx jsx = Jsx.getInstance();
		Process process = jsx.exec(builder.build());
		process.waitFor();

		// System.out.println(streamToString(process.getInputStream()));

		assertThat(process.exitValue(), is(0));
	}

	/**
	 * Test for {@link Builder#executable(Executable)} with {@link Executable#Node}
	 * @throws IOException
	 * @throws InterruptedException
	 * @author vvakame
	 */
	@Test
	public void executableNode() throws IOException, InterruptedException {
		Builder builder = makeDefault();
		builder.executable(Executable.Node);
		builder.jsxSource(getGitRootDirectory().getAbsolutePath() + "/JSX/t/lib/001.hello.jsx");

		Jsx jsx = Jsx.getInstance();
		Process process = jsx.exec(builder.build());
		process.waitFor();

		// System.out.println(streamToString(process.getInputStream()));

		assertThat(process.exitValue(), is(0));
	}

	/**
	 * Test for {@link Builder#mode(Mode)} with {@link Mode#Compile}
	 * @throws IOException
	 * @throws InterruptedException
	 * @author vvakame
	 */
	@Test
	public void modeCompile() throws IOException, InterruptedException {
		Builder builder = makeDefault();
		builder.mode(Mode.Compile);
		builder.jsxSource(getGitRootDirectory().getAbsolutePath() + "/JSX/t/lib/001.hello.jsx");

		Jsx jsx = Jsx.getInstance();
		Process process = jsx.exec(builder.build());
		process.waitFor();

		// System.out.println(streamToString(process.getInputStream()));

		assertThat(process.exitValue(), is(0));
	}

	/**
	 * Test for {@link Builder#mode(Mode)} with {@link Mode#Parse}
	 * @throws IOException
	 * @throws InterruptedException
	 * @author vvakame
	 */
	@Test
	public void modeParse() throws IOException, InterruptedException {
		Builder builder = makeDefault();
		builder.mode(Mode.Parse);
		builder.jsxSource(getGitRootDirectory().getAbsolutePath() + "/JSX/t/lib/001.hello.jsx");

		Jsx jsx = Jsx.getInstance();
		Process process = jsx.exec(builder.build());
		streamToString(process.getInputStream());
		System.out.println(streamToString(process.getInputStream()));

		process.waitFor();

		assertThat(process.exitValue(), is(0));
	}

	/**
	 * Test for {@link Builder#release(boolean)}.
	 * @throws IOException
	 * @throws InterruptedException
	 * @author vvakame
	 */
	@Test
	public void release() throws IOException, InterruptedException {
		Builder builder = makeDefault();
		builder.release(true);
		builder.jsxSource(getGitRootDirectory().getAbsolutePath() + "/JSX/t/lib/001.hello.jsx");

		Jsx jsx = Jsx.getInstance();
		Process process = jsx.exec(builder.build());
		process.waitFor();

		// System.out.println(streamToString(process.getInputStream()));

		assertThat(process.exitValue(), is(0));
	}

	/**
	 * Test for {@link Builder#profile(boolean)}.
	 * @throws IOException
	 * @throws InterruptedException
	 * @author vvakame
	 */
	@Test
	public void profile() throws IOException, InterruptedException {
		Builder builder = makeDefault();
		builder.profile(true);
		builder.jsxSource(getGitRootDirectory().getAbsolutePath() + "/JSX/t/lib/001.hello.jsx");

		Jsx jsx = Jsx.getInstance();
		Process process = jsx.exec(builder.build());
		process.waitFor();

		// System.out.println(streamToString(process.getInputStream()));

		assertThat(process.exitValue(), is(0));
	}

	/**
	 * Test for {@link Builder#enableTypeCheck(boolean)}.
	 * @throws IOException
	 * @throws InterruptedException
	 * @author vvakame
	 */
	@Test
	public void enableTypeCheck() throws IOException, InterruptedException {
		Builder builder = makeDefault();
		builder.enableTypeCheck(true);
		builder.jsxSource(getGitRootDirectory().getAbsolutePath() + "/JSX/t/lib/001.hello.jsx");

		Jsx jsx = Jsx.getInstance();
		Process process = jsx.exec(builder.build());
		process.waitFor();

		System.out.println(streamToString(process.getErrorStream()));
		// System.out.println(streamToString(process.getInputStream()));

		assertThat(process.exitValue(), is(0));
	}

	/**
	 * Test for {@link Builder#enableSourceMap(boolean)}.
	 * @throws IOException
	 * @throws InterruptedException
	 * @author vvakame
	 */
	// TODO source map required --output option.
	@Test
	public void enableSourceMap() throws IOException, InterruptedException {
		Builder builder = makeDefault();
		builder.enableSourceMap(true);
		builder.jsxSource(getGitRootDirectory().getAbsolutePath() + "/JSX/t/lib/001.hello.jsx");

		Jsx jsx = Jsx.getInstance();
		Process process = jsx.exec(builder.build());
		process.waitFor();

		// System.out.println(streamToString(process.getInputStream()));

		assertThat(process.exitValue(), is(0));
	}

	Builder makeDefault() {
		Builder builder = new Jsx.Builder();
		builder.setNodeJsPath("/opt/local/bin/node");
		builder.setJsxPath(getJsxPath());

		return builder;
	}

	static String streamToString(InputStream stream) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] data = new byte[1024];

		int len;
		while ((len = stream.read(data)) != -1) {
			baos.write(data, 0, len);
		}

		return baos.toString();
	}

	static File getGitRootDirectory() {
		final String projectName = "jsx-wrapper";
		File gitRoot = new File("./").getAbsoluteFile();
		while (!projectName.equals(gitRoot.getName())) {
			gitRoot = gitRoot.getParentFile();
		}
		return gitRoot.getParentFile();
	}

	static String getJsxPath() {
		File rootDirectory = getGitRootDirectory();
		File jsxFile = new File(rootDirectory, "JSX/bin/jsx");
		if (!jsxFile.exists()) {
			throw new IllegalStateException("jsx not found");
		}

		return jsxFile.getAbsolutePath();
	}
}
