package net.vvakame.jsx.peg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import mouse.runtime.Source;
import mouse.runtime.SourceString;

/**
 * {@link Source} implementation for use {@link InputStream}.
 * @author vvakame
 */
public class SourceStream implements Source {

	SourceString sourceString;

	/** source code string */
	public final String src;


	/**
	 * the constructor.
	 * @param stream
	 * @throws IOException
	 * @category constructor
	 */
	public SourceStream(InputStream stream) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		byte[] date = new byte[1024];
		int len = 0;
		while ((len = stream.read(date)) != -1) {
			baos.write(date, 0, len);
		}

		src = baos.toString();
		sourceString = new SourceString(src);
	}

	@Override
	public boolean created() {
		return sourceString.created();
	}

	@Override
	public int end() {
		return sourceString.end();
	}

	@Override
	public char at(int p) {
		return sourceString.at(p);
	}

	@Override
	public String at(int p, int q) {
		return sourceString.at(p, q);
	}

	@Override
	public String where(int p) {
		return sourceString.where(p);
	}
}
