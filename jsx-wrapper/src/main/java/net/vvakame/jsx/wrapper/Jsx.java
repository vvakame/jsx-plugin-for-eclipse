package net.vvakame.jsx.wrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.vvakame.jsx.wrapper.entity.Ast;
import net.vvakame.jsx.wrapper.entity.AstGen;
import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.util.JsonArray;

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
		 * @param columnIndex
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
	 * @throws IOException
	 * @throws InterruptedException
	 * @author vvakame
	 */
	public Process exec(Args args) throws IOException, InterruptedException {
		if (!new File(args.jsxPath).exists()) {
			throw new IllegalArgumentException("jsx. " + args.jsxPath + " is not exists.");
		} else if (!new File(args.nodeJsPath).exists()) {
			throw new IllegalArgumentException("jsx. " + args.jsxPath + " is not exists.");
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

		argList.add(args.jsxSource);

		System.out.println(argList.toString());

		ProcessBuilder builder = new ProcessBuilder(argList);
		addPath(builder, new File(args.nodeJsPath).getParent());

		Process process = builder.start();

		return process;
	}

	/**
	 * Execute --mode, parse and get AST.
	 * @param args
	 * @return {@link List} of {@link Ast}
	 * @throws IOException
	 * @throws JsonFormatException
	 * @throws InterruptedException
	 * @author vvakame
	 */
	public List<Ast> parse(Args args) throws IOException, JsonFormatException, InterruptedException {
		args.mode = Mode.Parse.getOptionValue();

		Process process = exec(args);

		List<Ast> list = AstGen.getList(process.getInputStream());

		return list;
	}

	/**
	 * Execute --complete, get completion list.
	 * @param args
	 * @param lineIndex
	 * @param columnIndex
	 * @return Completion list
	 * @throws IOException
	 * @throws JsonFormatException
	 * @throws InterruptedException
	 * @author vvakame
	 */
	public JsonArray complete(Args args, int lineIndex, int columnIndex) throws IOException,
			JsonFormatException, InterruptedException {
		args.complete = true;
		args.lineIndex = lineIndex;
		args.columnIndex = columnIndex;

		Process process = exec(args);

		JsonArray jsonArray =
				JsonArray.fromParser(JsonPullParser.newParser(process.getInputStream()));

		return jsonArray;
	}

	void addPath(ProcessBuilder builder, String path) {
		Map<String, String> environment = builder.environment();
		environment.put("PATH", path + ":" + environment.get("PATH"));
	}
}
