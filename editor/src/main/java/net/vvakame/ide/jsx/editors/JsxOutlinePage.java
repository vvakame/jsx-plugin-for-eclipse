package net.vvakame.ide.jsx.editors;

import mouse.runtime.Source;
import mouse.runtime.SourceString;
import net.vvakame.ide.jsx.parser.SyntaxTree;
import net.vvakame.jsx.peg.JsxParser;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

public class JsxOutlinePage extends ContentOutlinePage {

	private JsxEditor editor;

	public JsxOutlinePage(JsxEditor editor) {
		this.editor = editor;
	}

	public void createControl(Composite parent) {
		super.createControl(parent);

		TreeViewer viewer = getTreeViewer();

		viewer.setContentProvider(new JsxContentProvider());
		viewer.setLabelProvider(new JsxLabelProvider());

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection sel = (IStructuredSelection) event
						.getSelection();
				Object el = sel.getFirstElement();
				if (el instanceof SyntaxTree) {
					SyntaxTree tree = (SyntaxTree) el;
					System.out.println(tree);
					editor.selectAndReveal(tree.index(), 0);
				}
			}
		});

		refresh();
	}

	public void refresh() {
		IDocumentProvider provider = editor.getDocumentProvider();
		IDocument document = provider.getDocument(editor.getEditorInput());
		final String source = document.get();

		Source src = new SourceString(source);

		JsxParser parser = new JsxParser();
		boolean result = parser.parse(src);

		SyntaxTree syntaxTree;

		if (result) {
			syntaxTree = parser.semantics().getSyntaxTreeWithCompress();
		} else {
			syntaxTree = null;
		}

		TreeViewer viewer = getTreeViewer();
		viewer.setInput(syntaxTree);
		viewer.refresh();
	}

	private static class JsxContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getChildren(Object parentElement) {
			SyntaxTree tree = (SyntaxTree) parentElement;
			return tree.children().toArray();
		}

		@Override
		public Object getParent(Object element) {
			return ((SyntaxTree) element).parent();
		}

		@Override
		public boolean hasChildren(Object element) {
			return getChildren(element).length != 0;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	private static class JsxLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			SyntaxTree tree = (SyntaxTree) element;
			return tree.name();
		}
	}
}
