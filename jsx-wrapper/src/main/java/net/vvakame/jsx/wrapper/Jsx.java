package net.vvakame.jsx.wrapper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.vvakame.jsx.wrapper.completeentity.Complete;
import net.vvakame.jsx.wrapper.completeentity.CompleteGen;
import net.vvakame.jsx.wrapper.parseentity.ClassDefinition;
import net.vvakame.jsx.wrapper.parseentity.ClassDefinitionGen;
import net.vvakame.util.jsonpullparser.JsonFormatException;

/**
 * Wrapper of JSX command.
 * @author vvakame
 */
public class Jsx {

	static Jsx instance;


	private Jsx() {
	}

	/**
	 * get instance.
	 * @return {@link Jsx}
	 * @author vvakame
	 */
	public static Jsx getInstance() {
		if (instance != null) {
			return instance;
		}

		instance = new Jsx();
		return instance;
	}


	/**
	 * Builder for {@link Args}.
	 * @author vvakame
	 */
	public static class Builder {

		String nodeJsPath;

		String jsxPath;

		boolean help;

		Executable executable;

		String jsxSource = "main.jsx";

		String inputFilename;

		Mode mode;

		boolean release;

		boolean profile;

		boolean enableTypeCheck;

		boolean enableSourceMap;

		boolean complete;

		int lineIndex;

		int columnIndex;


		/**
		 * set node.js binary path.
		 * @param nodeJsPath
		 * @return this
		 * @author vvakame
		 */
		public Builder setNodeJsPath(String nodeJsPath) {
			this.nodeJsPath = nodeJsPath;
			return this;
		}

		/**
		 * set jsx binary path.
		 * @param jsxPath
		 * @return this
		 * @author vvakame
		 */
		public Builder setJsxPath(String jsxPath) {
			this.jsxPath = jsxPath;
			return this;
		}

		/**
		 * set --help option.
		 * @param help
		 * @return this
		 * @author vvakame
		 */
		public Builder help(boolean help) {
			this.help = help;
			return this;
		}

		/**
		 * set --executable option.
		 * @param executable
		 * @return this
		 * @author vvakame
		 */
		public Builder executable(Executable executable) {
			this.executable = executable;
			return this;
		}

		/**
		 * set jsx code about compile target.
		 * @param jsxSource
		 * @return this
		 * @author vvakame
		 */
		public Builder jsxSource(String jsxSource) {
			this.jsxSource = jsxSource;
			return this;
		}

		/**
		 * set jsx code with --input-filename option.
		 * @param jsxSource
		 * @return this
		 * @author vvakame
		 */
		public Builder inputFilename(String jsxSource) {
			this.inputFilename = jsxSource;
			return this;
		}

		/**
		 * set --mode option.
		 * @param mode
		 * @return this
		 * @author vvakame
		 */
		public Builder mode(Mode mode) {
			this.mode = mode;
			return this;
		}

		/**
		 * set --release option.
		 * @param release
		 * @return this
		 * @author vvakame
		 */
		public Builder release(boolean release) {
			this.release = release;
			return this;
		}

		/**
		 * set --profile option.
		 * @param profile
		 * @return this
		 * @author vvakame
		 */
		public Builder profile(boolean profile) {
			this.profile = profile;
			return this;
		}

		/**
		 * set --enable-type-check option.
		 * @param enableTypeCheck
		 * @return this
		 * @author vvakame
		 */
		public Builder enableTypeCheck(boolean enableTypeCheck) {
			this.enableTypeCheck = enableTypeCheck;
			return this;
		}

		/**
		 * set --enable-source-map option.
		 * @param enableSourceMap
		 * @return this
		 * @author vvakame
		 */
		public Builder enableSourceMap(boolean enableSourceMap) {
			this.enableSourceMap = enableSourceMap;
			return this;
		}

		/**
		 * set --complete X:X option.
		 * @param complete
		 * @param lineIndex
		 * @param columnIndex columnIndex. tab length is 1.
		 * @return this
		 * @author vvakame
		 */
		public Builder complete(boolean complete, int lineIndex, int columnIndex) {
			this.complete = complete;
			this.lineIndex = lineIndex;
			this.columnIndex = columnIndex;
			return this;
		}

		/**
		 * construct {@link Args}.
		 * @return {@link Args}
		 * @author vvakame
		 */
		public Args build() {
			Args args = new Args();
			args.nodeJsPath = nodeJsPath;
			args.jsxPath = jsxPath;
			args.help = help;
			if (executable != null) {
				args.executable = executable.getOptionValue();
			}
			args.jsxSource = jsxSource;
			args.inputFilename = inputFilename;
			if (mode != null) {
				args.mode = mode.getOptionValue();
			}
			args.release = release;
			args.profile = profile;
			args.enableTypeCheck = enableTypeCheck;
			args.enableSourceMap = enableSourceMap;
			args.complete = complete;
			args.lineIndex = lineIndex;
			args.columnIndex = columnIndex;

			return args;
		}
	}

	/**
	 * Option value for --executable.
	 * @author vvakame
	 */
	public static enum Executable {
		/** js code for web. */
		Web,
		/** js code for node.js */
		Node;

		String getOptionValue() {
			return name().toLowerCase();
		}
	}

	/**
	 * Option value for --mode.
	 * @author vvakame
	 */
	public static enum Mode {
		/** compile mode. generate js code. */
		Compile,
		/** parse mode. generate AST. */
		Parse;

		String getOptionValue() {
			return name().toLowerCase();
		}
	}

	/**
	 * JSX command args. build by {@link Builder}.
	 * @author vvakame
	 */
	public static class Args {

		String nodeJsPath;

		String jsxPath;

		String inputFilename;

		boolean help;

		String executable;

		String jsxSource;

		String mode;

		boolean release;

		boolean profile;

		boolean enableTypeCheck;

		boolean enableSourceMap;

		boolean complete;

		int lineIndex;

		int columnIndex;
	}


	/**
	 * execute JSX command.
	 * @param args
	 * @return {@link Process}
	 * @author vvakame
	 */
	public Process exec(Args args) {
		if (!new File(args.jsxPath).exists()) {
			throw new IllegalArgumentException("jsx. " + args.jsxPath + " is not exists.");
		} else if (!new File(args.nodeJsPath).exists()) {
			throw new IllegalArgumentException("jsx. " + args.nodeJsPath + " is not exists.");
		}

		List<String> argList = new ArrayList<String>();
		argList.add(args.jsxPath);

		if (args.help) {
			argList.add("--help");
		}

		if (args.executable != null) {
			argList.add("--executable");
			argList.add(args.executable);

		}

		if (args.mode != null) {
			argList.add("--mode");
			argList.add(args.mode);
		}

		if (args.release) {
			argList.add("--release");
		}

		if (args.profile) {
			argList.add("--profile");
		}

		if (args.enableTypeCheck) {
			argList.add("--enable-type-check");
		}

		if (args.enableSourceMap) {
			argList.add("--enable-source-map");
		}

		if (args.complete) {
			argList.add("--complete");
			argList.add(String.valueOf(args.lineIndex) + ":" + String.valueOf(args.columnIndex));
		}

		if (args.inputFilename != null) {
			argList.add("--input-filename");
			argList.add(args.inputFilename);
			argList.add("--");
			argList.add("-");
		} else {
			argList.add(args.jsxSource);
		}

		StringBuilder stringBuilder = new StringBuilder();
		for (String str : argList) {
			stringBuilder.append(str).append(" ");
		}
		System.out.println(stringBuilder.toString());

		ProcessBuilder builder = new ProcessBuilder(argList);
		addPath(builder, new File(args.nodeJsPath).getParent());

		Process process;
		try {
			process = builder.start();
		} catch (IOException e) {
			throw new JsxCommandException("raise IOException.", e);
		}

		return process;
	}

	/**
	 * Execute --mode, parse and get AST.
	 * @param args
	 * @return {@link List} of {@link ClassDefinition}
	 * @throws JsxCommandException 
	 * @author vvakame
	 */
	public List<ClassDefinition> parse(Args args) throws JsxCommandException {
		args.mode = Mode.Parse.getOptionValue();

		Process process = exec(args);

		List<ClassDefinition> list;
		try {
			list = ClassDefinitionGen.getList(process.getInputStream());
		} catch (IOException e) {
			throw new JsxCommandException("raise IOException.", e);
		} catch (JsonFormatException e) {
			throw new JsxCommandException("ast list JSON was unexpected.", e);
		}

		return list;
	}

	/**
	 * Execute --complete, get completion list.
	 * @param args
	 * @param lineIndex
	 * @param columnIndex columnIndex. tab length is 1.
	 * @return Completion list
	 * @throws JsxCommandException 
	 * @author vvakame
	 */
	public List<Complete> complete(Args args, int lineIndex, int columnIndex)
			throws JsxCommandException {
		args.complete = true;
		args.lineIndex = lineIndex;
		args.columnIndex = columnIndex;

		try {
			Process process = exec(args);

			List<Complete> list = CompleteGen.getList(process.getInputStream());

			process.waitFor();
			if (process.exitValue() != 0) {
				throw new JsxCommandException("jsx command failure");
			}

			return list;
		} catch (JsonFormatException e) {
			throw new JsxCommandException("complete list JSON was unexpected. " + lineIndex + ":"
					+ columnIndex, e);
		} catch (IOException e) {
			throw new JsxCommandException("raise IOException. " + lineIndex + ":" + columnIndex, e);
		} catch (InterruptedException e) {
			throw new JsxCommandException("raise InterruptedException. " + lineIndex + ":"
					+ columnIndex, e);
		}
	}

	/**
	 * Execute --complete with --input-filename option, get completion list.
	 * @param args
	 * @param newSource new jsx source code. (unsaved)
	 * @param lineIndex
	 * @param columnIndex columnIndex. tab length is 1.
	 * @return Completion list
	 * @throws JsxCommandException 
	 * @author vvakame
	 */
	public List<Complete> complete(Args args, String newSource, int lineIndex, int columnIndex)
			throws JsxCommandException {
		args.complete = true;
		args.lineIndex = lineIndex;
		args.columnIndex = columnIndex;
		args.inputFilename = args.jsxSource;
		args.jsxSource = null;

		try {
			Process process = exec(args);
			OutputStream outputStream = process.getOutputStream();
			outputStream.write(newSource.getBytes());
			outputStream.flush();
			outputStream.close();

			List<Complete> list = CompleteGen.getList(process.getInputStream());

			process.waitFor();
			if (process.exitValue() != 0) {
				throw new JsxCommandException("jsx command failure");
			}

			return list;
		} catch (JsonFormatException e) {
			throw new JsxCommandException("complete list JSON was unexpected. " + lineIndex + ":"
					+ columnIndex, e);
		} catch (IOException e) {
			throw new JsxCommandException("raise IOException. " + lineIndex + ":" + columnIndex, e);
		} catch (InterruptedException e) {
			throw new JsxCommandException("raise InterruptedException. " + lineIndex + ":"
					+ columnIndex, e);
		}
	}

	void addPath(ProcessBuilder builder, String path) {
		Map<String, String> environment = builder.environment();
		environment.put("PATH", path + ":" + environment.get("PATH"));
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
}
