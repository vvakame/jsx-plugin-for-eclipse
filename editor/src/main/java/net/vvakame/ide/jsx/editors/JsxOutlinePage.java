package net.vvakame.ide.jsx.editors;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private static class SyntaxTree {
		SyntaxTree parent;
		List<SyntaxTree> children = new ArrayList<SyntaxTree>();
		@SuppressWarnings("unused")
		String type = "unknown";
		String name = "no name";
		int index;
		int line;
		int column;

		static void construct(SourceInfo srcInfo, SyntaxTree parent, Tree t) {
			SyntaxTree st = new SyntaxTree();

			for (int i = 0; i < t.getChildCount(); i++) {
				Tree child = t.getChild(i);
				construct(srcInfo, st, child);
			}

			st.name = t.getText();
			st.line = t.getLine();
			st.column = t.getCharPositionInLine();
			st.index = srcInfo.getIndex(st.line, st.column);

			parent.children.add(st);
			st.parent = parent;
		}

		static SyntaxTree construct(SourceInfo srcInfo, Tree t) {
			SyntaxTree st = new SyntaxTree();
			construct(srcInfo, st, t);
			return st.children.get(0);
		}

		@Override
		public String toString() {
			return "SyntaxTree [name=" + name + ", index=" + index + ", line="
					+ line + ", column=" + column + "]";
		}
	}

	private class SourceInfo {
		final String src;

		Map<Integer, Integer> lengthMemo = new HashMap<Integer, Integer>();
		String lines[];

		SourceInfo(String src) {
			this.src = src;
		}

		int getIndex(int line, int column) {
			if (lines == null) {
				lines = src.split("(\r\n|\r|\n)");
			}
			if (lines.length < line) {
				return getIndex(line - 1, column);
			}

			if (line == 1) {
				return column;
			} else {
				int lineTotal = getTotalLength(line - 1);
				return lineTotal + column;
			}
		}

		private int getTotalLength(final int includeLine) {
			{
				Integer lineFirst = lengthMemo.get(includeLine);
				if (lineFirst != null) {
					return lineFirst;
				}
			}

			if (includeLine == 1) {
				int len = lines[0].length() + 1;
				lengthMemo.put(includeLine, len);
				return len;
			} else {
				int beforeLine = getTotalLength(includeLine - 1);
				int thisLine = beforeLine + lines[includeLine - 1].length() + 1;
				lengthMemo.put(includeLine, thisLine);
				return thisLine;
			}
		}
	}
}
