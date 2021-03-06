package net.vvakame.jsx.wrapper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import net.vvakame.jsx.wrapper.Jsx.Builder;
import net.vvakame.jsx.wrapper.Jsx.Executable;
import net.vvakame.jsx.wrapper.Jsx.Mode;
import net.vvakame.jsx.wrapper.completeentity.Complete;
import net.vvakame.jsx.wrapper.errorentity.CompileError;
import net.vvakame.jsx.wrapper.parseentity.ClassDefinition;
import net.vvakame.util.jsonpullparser.JsonFormatException;

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

					assertThat(file.getAbsolutePath(), astList.size(), is(not(0)));
				}
			}
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
		builder.jsxSource(getGitRootDirectory().getAbsolutePath() + "/jsx-code/jsx/view.jsx");

		Jsx jsx = Jsx.getInstance();

		List<Complete> completeList = jsx.complete(builder.build(), 16, 15);
		// System.out.println(completeList.toString());

		assertThat(completeList, notNullValue());
	}

	/**
	 * Test for {@link Jsx#complete(net.vvakame.jsx.wrapper.Jsx.Args, String, int, int)}
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	@Test
	public void completeWithNewSource() throws IOException, InterruptedException,
			JsonFormatException {

		Builder builder = makeDefault();
		builder.jsxSource(getGitRootDirectory().getAbsolutePath() + "/jsx-code/jsx/view.jsx");

		Jsx jsx = Jsx.getInstance();

		String newJsxCode = "class _Main{}";
		List<Complete> completeList = jsx.complete(builder.build(), newJsxCode, 1, 13);
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

		// System.out.println(streamToString(process.getInputStream()));

		process.waitFor();

		assertThat(process.exitValue(), is(0));
	}

	/**
	 * Test for {@link Builder#inputFilename(String)} with valid jsx code.
	 * @throws IOException
	 * @throws InterruptedException
	 * @author vvakame
	 */
	@Test
	public void inputFilenameValid() throws IOException, InterruptedException {
		Builder builder = makeDefault();
		builder.inputFilename("main.jsx");

		Jsx jsx = Jsx.getInstance();
		Process process = jsx.exec(builder.build());
		OutputStream outputStream = process.getOutputStream();
		outputStream.write("class _Main{static function main(args:string[]):void{log true;}}"
			.getBytes());
		outputStream.close();

		streamToString(process.getInputStream());
		streamToString(process.getErrorStream());
		// System.out.println(streamToString(process.getInputStream()));

		process.waitFor();

		assertThat(process.exitValue(), is(0));
	}

	/**
	 * Test for {@link Builder#inputFilename(String)} with invalid jsx code.
	 * @throws IOException
	 * @throws InterruptedException
	 * @author vvakame
	 */
	@Test
	public void inputFilenameInvalid() throws IOException, InterruptedException {
		Builder builder = makeDefault();
		builder.inputFilename("main.jsx");

		Jsx jsx = Jsx.getInstance();
		Process process = jsx.exec(builder.build());
		OutputStream outputStream = process.getOutputStream();
		outputStream.write("hogehogefugafuga".getBytes());
		outputStream.close();

		streamToString(process.getErrorStream());
		streamToString(process.getInputStream());
		// System.out.println(streamToString(process.getErrorStream()));

		process.waitFor();

		assertThat(process.exitValue(), not(0));
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

	/**
	 * Test for {@link Jsx#checkError(net.vvakame.jsx.wrapper.Jsx.Args)}.
	 * @throws IOException
	 * @author vvakame
	 */
	@Test
	public void checkError() throws IOException {
		Builder builder = makeDefault();
		// builder.mode(Mode.Parse);
		builder.jsxSource(getGitRootDirectory().getAbsolutePath()
				+ "/jsx-wrapper/src/test/resources/jsx/invalid/002.jsx");

		Jsx jsx = Jsx.getInstance();
		List<CompileError> result = jsx.checkError(builder.build());

		System.out.println(result.toString());

		assertThat(result.size(), not(0));
	}

	/**
	 * make default builder.
	 * @return {@link Builder}
	 * @author vvakame
	 */
	public static Builder makeDefault() {
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

	/**
	 * get git root directory.
	 * @return git root
	 * @author vvakame
	 */
	public static File getGitRootDirectory() {
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
