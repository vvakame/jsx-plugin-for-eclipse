package net.vvakame.ide.jsx.editors.jsxprovider;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

import static net.vvakame.ide.jsx.editors.misc.IJsxToken.*;

/**
 * DocumentProvider for JSX code. Detect JSX block comment(not jsxdoc).
 * @author vvakame
 */
public class JsxDocumentProvider extends FileDocumentProvider {

	@Override
	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.createDocument(element);
		if (document != null) {
			IDocumentPartitioner partitioner =
					new FastPartitioner(new JsxPartitionScanner(), new String[] {
						JSX_KEYWORD,
						JSX_COMMENT
					});
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
		return document;
	}
}
