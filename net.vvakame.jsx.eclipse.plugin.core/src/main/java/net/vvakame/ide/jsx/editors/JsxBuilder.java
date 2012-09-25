package net.vvakame.ide.jsx.editors;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import net.vvakame.ide.jsx.Activator;
import net.vvakame.ide.jsx.editors.viewerconfiguration.JsxIdentDetector;
import net.vvakame.jsx.wrapper.Jsx;
import net.vvakame.jsx.wrapper.Jsx.Builder;
import net.vvakame.jsx.wrapper.errorentity.CompileError;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * JSX code builder.
 * @author vvakame
 */
public class JsxBuilder extends IncrementalProjectBuilder {

	/** builder id */
	public static final String BUILDER_ID = "jsx.eclipse.plugin.core.JsxBuilder";


	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor)
			throws CoreException {
		if (kind == FULL_BUILD) {
			getProject().accept(new JsxVisitor());
		} else {
			getDelta(getProject()).accept(new JsxDeltaVisitor());
		}

		return null;
	}


	class JsxVisitor implements IResourceVisitor {

		@Override
		public boolean visit(IResource res) throws CoreException {
			if (res instanceof IFile && "jsx".equals(res.getFileExtension())) {
				processJsxFile((IFile) res);
			}
			return true;
		}
	}

	class JsxDeltaVisitor implements IResourceDeltaVisitor {

		@Override
		public boolean visit(IResourceDelta delta) throws CoreException {
			IResource res = delta.getResource();
			int kind = delta.getKind();
			if (kind == IResourceDelta.ADDED || kind == IResourceDelta.CHANGED
					&& res instanceof IFile && "jsx".equals(res.getFileExtension())) {
				processJsxFile((IFile) res);
			}
			return true;
		}
	}


	void processJsxFile(IFile file) throws CoreException {
		file.deleteMarkers(IMarker.PROBLEM, false, IResource.DEPTH_ZERO);

		Builder builder = Activator.getDefault().getJsxCommandBuilder();
		builder.jsxSource(file.getRawLocation().makeAbsolute().toFile().getAbsolutePath());

		List<CompileError> errorList = Jsx.getInstance().checkError(builder.build());
		if (errorList != null) {
			for (CompileError error : errorList) {
				if (!error.getFilename().endsWith(file.getRawLocation().toFile().getAbsolutePath())) {
					continue;
				}

				IMarker marker = file.createMarker(IMarker.PROBLEM);
				marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
				marker.setAttribute(IMarker.MESSAGE, error.getMessage());

				// TODO optimize
				int start = 0;
				int end = 0;
				{
					int line = 1;
					int column = 0;
					int c;
					InputStreamReader isr = new InputStreamReader(file.getContents());
					try {
						while ((c = isr.read()) != -1) {
							column++;
							start++;
							if (c == '\n') { // Unix LF
								line++;
								column = 0;
							} else if (c == '\r') { // Windows CR+LF or Old Mac CR
								line++;
								column = 0;

								c = isr.read();
								start++;
								if (c != '\n') { // Old Mac CR
									column++;
									start++;
								}
							}
							if (error.getLine() == line && error.getColumn() == column) {
								break;
							}
						}

						end = start;
						JsxIdentDetector ident = new JsxIdentDetector();
						while ((c = isr.read()) != -1) {
							if (ident.isWordPart((char) c)) {
								end++;
							} else {
								break;
							}
						}
						isr.close();

					} catch (IOException e) {
						throw new IllegalStateException(e);
					}
				}
				marker.setAttribute(IMarker.CHAR_START, start);
				marker.setAttribute(IMarker.CHAR_END, end);
			}
		}
	}
}
