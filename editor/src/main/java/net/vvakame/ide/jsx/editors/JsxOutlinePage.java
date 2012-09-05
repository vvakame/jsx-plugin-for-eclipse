package net.vvakame.ide.jsx.editors;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import net.vvakame.ide.jsx.parser.SourceInfo;
import net.vvakame.ide.jsx.parser.SyntaxTree;
import net.vvakame.jsx.antlr.JSXLexer;
import net.vvakame.jsx.antlr.JSXParser;
import net.vvakame.jsx.antlr.JSXParser.programFile_return;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
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
					editor.selectAndReveal(tree.index, 0);
				}
			}
		});

		refresh();
	}

	public void refresh() {
		IDocumentProvider provider = editor.getDocumentProvider();
		IDocument document = provider.getDocument(editor.getEditorInput());
		final String source = document.get();

		JSXLexer lexer = new JSXLexer();
		ByteArrayInputStream stream = new ByteArrayInputStream(
				source.getBytes());
		try {
			lexer.setCharStream(new ANTLRInputStream(stream));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		JSXParser parser = new JSXParser(tokens);

		tokens.LT(1);
		programFile_return programFileReturn;
		try {
			programFileReturn = parser.programFile();
		} catch (RecognitionException e) {
			return;
		}

		SourceInfo srcInfo = new SourceInfo(source);

		Tree t = (Tree) programFileReturn.getTree();
		SyntaxTree syntaxTree = SyntaxTree.construct(srcInfo, t);

		TreeViewer viewer = getTreeViewer();
		viewer.setInput(syntaxTree);
		viewer.refresh();
	}

	private static class JsxContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getChildren(Object parentElement) {
			SyntaxTree tree = (SyntaxTree) parentElement;
			return tree.children.toArray();
		}

		@Override
		public Object getParent(Object element) {
			return ((SyntaxTree) element).parent;
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
			return tree.name;
		}
	}
}
