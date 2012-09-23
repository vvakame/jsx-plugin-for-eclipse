package net.vvakame.jsx.wrapper.parseentity;

import java.io.IOException;
import java.util.List;

import net.vvakame.jsx.wrapper.Jsx;
import net.vvakame.jsx.wrapper.Jsx.Builder;
import net.vvakame.jsx.wrapper.Jsx.Mode;
import net.vvakame.jsx.wrapper.parseentity.AstHelper;
import net.vvakame.jsx.wrapper.parseentity.ClassDefinition;
import net.vvakame.jsx.wrapper.parseentity.Member;
import net.vvakame.jsx.wrapper.JsxTest;
import net.vvakame.util.jsonpullparser.JsonFormatException;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Test for {@link AstHelper}.
 * @author vvakame
 */
public class AstHelperTest {

	/**
	 * Test.
	 * @throws IOException
	 * @throws JsonFormatException
	 * @throws InterruptedException
	 * @author vvakame
	 */
	@Test
	public void getAst() throws IOException, JsonFormatException, InterruptedException {
		Builder builder = JsxTest.makeDefault();
		builder.jsxSource(JsxTest.getGitRootDirectory().getAbsolutePath()
				+ "/JSX/t/run/001.hello.jsx");
		builder.mode(Mode.Parse);

		Jsx jsx = Jsx.getInstance();
		Process process = jsx.exec(builder.build());

		List<ClassDefinition> astList = AstHelper.getAst(process.getInputStream());

		assertThat(astList.size(), is(not(0)));
	}

	/**
	 * Test.
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void filterByDefinedFile() throws IOException, InterruptedException, JsonFormatException {
		List<ClassDefinition> list = getSampleAst();

		List<ClassDefinition> filtered = AstHelper.filterByDefinedFile(list, "001.hello.jsx");

		assertThat(filtered.size(), is(1));
	}

	/**
	 * Test.
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void filterFunction() throws IOException, InterruptedException, JsonFormatException {
		List<ClassDefinition> list = getSampleAst();

		List<ClassDefinition> filtered = AstHelper.filterByDefinedFile(list, "001.hello.jsx");
		List<Member> filteredMembers = AstHelper.filterFunction(filtered.get(0).getMembers());

		assertThat(filteredMembers.size(), is(1));
	}

	static List<ClassDefinition> getSampleAst() throws IOException, InterruptedException,
			JsonFormatException {
		Builder builder = JsxTest.makeDefault();
		builder.jsxSource(JsxTest.getGitRootDirectory().getAbsolutePath()
				+ "/JSX/t/run/001.hello.jsx");
		builder.mode(Mode.Parse);

		Jsx jsx = Jsx.getInstance();
		Process process = jsx.exec(builder.build());

		return AstHelper.getAst(process.getInputStream());
	}
}
