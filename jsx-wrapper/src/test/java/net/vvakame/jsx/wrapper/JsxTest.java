package net.vvakame.jsx.wrapper;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.vvakame.jsx.wrapper.Jsx.Builder;
import net.vvakame.jsx.wrapper.Jsx.Excecutable;
import net.vvakame.jsx.wrapper.Jsx.Mode;

import org.junit.Test;

public class JsxTest {

	// hint: if remove process.waitFor(),
	// flush process.getInputStream() or process.getErrorStream()

	@Test
	public void exec() throws IOException, InterruptedException {
		Builder builder = new Jsx.Builder();
		builder.setNodeJsPath("/opt/local/bin/node");
		builder.setJsxPath("/Users/vvakame/work/JSX/bin/jsx");

		builder.help(true);

		Jsx jsx = Jsx.getInstance();

		Process process = jsx.exec(builder.build());
		process.waitFor();

		assertThat(process.exitValue(), is(0));
	}

	@Test
	public void noArgs() throws IOException, InterruptedException {
		Builder builder = makeDefault();

		Jsx jsx = Jsx.getInstance();
		Process process = jsx.exec(builder.build());
		process.waitFor();

		assertThat(process.exitValue(), is(1));
	}

	@Test
	public void executableWeb() throws IOException, InterruptedException {
		Builder builder = makeDefault();
		builder.executable(Excecutable.Web);
		builder.jsxSource(getGitRootDirectory().getAbsolutePath()
				+ "/JSX/t/lib/001.hello.jsx");

		Jsx jsx = Jsx.getInstance();
		Process process = jsx.exec(builder.build());
		process.waitFor();

		// System.out.println(streamToString(process.getInputStream()));

		assertThat(process.exitValue(), is(0));
	}

	@Test
	public void executableNode() throws IOException, InterruptedException {
		Builder builder = makeDefault();
		builder.executable(Excecutable.Node);
		builder.jsxSource(getGitRootDirectory().getAbsolutePath()
				+ "/JSX/t/lib/001.hello.jsx");

		Jsx jsx = Jsx.getInstance();
		Process process = jsx.exec(builder.build());
		process.waitFor();

		// System.out.println(streamToString(process.getInputStream()));

		assertThat(process.exitValue(), is(0));
	}

	@Test
	public void modeCompile() throws IOException, InterruptedException {
		Builder builder = makeDefault();
		builder.mode(Mode.Compile);
		builder.jsxSource(getGitRootDirectory().getAbsolutePath()
				+ "/JSX/t/lib/001.hello.jsx");

		Jsx jsx = Jsx.getInstance();
		Process process = jsx.exec(builder.build());
		process.waitFor();

		// System.out.println(streamToString(process.getInputStream()));

		assertThat(process.exitValue(), is(0));
	}

	@Test
	public void modeParse() throws IOException, InterruptedException {
		Builder builder = makeDefault();
		builder.mode(Mode.Parse);
		builder.jsxSource(getGitRootDirectory().getAbsolutePath()
				+ "/JSX/t/lib/001.hello.jsx");

		Jsx jsx = Jsx.getInstance();
		Process process = jsx.exec(builder.build());
		streamToString(process.getInputStream());
		process.waitFor();

		// System.out.println(streamToString(process.getInputStream()));

		assertThat(process.exitValue(), is(0));
	}

	Builder makeDefault() {
		Builder builder = new Jsx.Builder();
		builder.setNodeJsPath("/opt/local/bin/node");
		builder.setJsxPath("/Users/vvakame/work/JSX/bin/jsx");

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
}
