package net.vvakame.jsx.wrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Jsx {

	static Jsx instance;

	private Jsx() {
	}

	public static Jsx getInstance() {
		if (instance != null) {
			return instance;
		}

		instance = new Jsx();
		return instance;
	}

	public static class Builder {
		String nodeJsPath;
		String jsxPath;
		boolean help;
		Excecutable executable;
		String jsxSource = "main.jsx";
		Mode mode;
		boolean release;
		boolean profile;
		boolean enableTypeCheck;
		boolean enableSourceMap;

		public Builder setNodeJsPath(String nodeJsPath) {
			this.nodeJsPath = nodeJsPath;
			return this;
		}

		public Builder setJsxPath(String jsxPath) {
			this.jsxPath = jsxPath;
			return this;
		}

		public Builder help(boolean help) {
			this.help = help;
			return this;
		}

		public Builder executable(Excecutable executable) {
			this.executable = executable;
			return this;
		}

		public Builder jsxSource(String jsxSource) {
			this.jsxSource = jsxSource;
			return this;
		}

		public Builder mode(Mode mode) {
			this.mode = mode;
			return this;
		}

		public Builder release(boolean release) {
			this.release = release;
			return this;
		}

		public Builder profile(boolean profile) {
			this.profile = profile;
			return this;
		}

		public Builder enableTypeCheck(boolean enableTypeCheck) {
			this.enableTypeCheck = enableTypeCheck;
			return this;
		}

		public Builder enableSourceMap(boolean enableSourceMap) {
			this.enableSourceMap = enableSourceMap;
			return this;
		}

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
			return args;
		}
	}

	public static enum Excecutable {
		Web, Node;

		String getOptionValue() {
			return name().toLowerCase();
		}
	}

	public static enum Mode {
		Compile, Parse;

		String getOptionValue() {
			return name().toLowerCase();
		}
	}

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
	}

	public Process exec(Args args) throws IOException, InterruptedException {
		if (!new File(args.jsxPath).exists()) {
			throw new IllegalArgumentException("jsx. " + args.jsxPath
					+ " is not exists.");
		} else if (!new File(args.nodeJsPath).exists()) {
			throw new IllegalArgumentException("jsx. " + args.jsxPath
					+ " is not exists.");
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

		argList.add(args.jsxSource);

		System.out.println(argList.toString());

		ProcessBuilder builder = new ProcessBuilder(argList);
		addPath(builder, new File(args.nodeJsPath).getParent());

		Process process = builder.start();

		return process;
	}

	void addPath(ProcessBuilder builder, String path) {
		Map<String, String> environment = builder.environment();
		environment.put("PATH", path + ":" + environment.get("PATH"));
	}
}
