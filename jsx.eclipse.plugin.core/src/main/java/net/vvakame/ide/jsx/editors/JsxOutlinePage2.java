package net.vvakame.ide.jsx.editors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.vvakame.ide.jsx.Activator;
import net.vvakame.ide.jsx.parser.SyntaxTree;
import net.vvakame.jsx.wrapper.Jsx;
import net.vvakame.jsx.wrapper.Jsx.Builder;
import net.vvakame.jsx.wrapper.parseentity.AstHelper;
import net.vvakame.jsx.wrapper.parseentity.ClassDefinition;
import net.vvakame.jsx.wrapper.parseentity.Member;
import net.vvakame.jsx.wrapper.parseentity.Token;
import net.vvakame.util.jsonpullparser.JsonFormatException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

/**
 * JSX code outline page. by JSX binary --mode parse.
 * @author vvakame
 */
public class JsxOutlinePage2 extends ContentOutlinePage {

	private JsxEditor editor;


	/**
	 * the constructor.
	 * @param editor
	 * @category constructor
	 */
	public JsxOutlinePage2(JsxEditor editor) {
		this.editor = editor;
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);

		TreeViewer viewer = getTreeViewer();

		viewer.setContentProvider(new JsxContentProvider());
		viewer.setLabelProvider(new JsxLabelProvider());

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection sel = (IStructuredSelection) event.getSelection();
				Object el = sel.getFirstElement();
				Token token = null;
				if (el == null) {
				} else if (el instanceof ClassDefinition) {
					ClassDefinition clazz = (ClassDefinition) el;
					token = clazz.getToken();
				} else if (el instanceof Member) {
					Member member = (Member) el;
					token = member.getToken();
				} else {
					throw new IllegalStateException("unknown class "
							+ el.getClass().getCanonicalName());
				}

				if (token != null) {
					IDocumentProvider provider = editor.getDocumentProvider();
					IDocument document = provider.getDocument(editor.getEditorInput());
					try {
						int offset =
								document.getLineOffset(token.getLineNumber() - 1)
										+ token.getColumnNumber();
						editor.selectAndReveal(offset, 0);
					} catch (BadLocationException e) {
						Activator.getDefault().getLog()
							.log(new Status(Status.ERROR, Activator.PLUGIN_ID, "raise error", e));
						return;
					}
				}
			}
		});

		refresh();
	}

	/**
	 * Refresh outline.
	 * @author vvakame
	 */
	public void refresh() {
		String jsxCodePath;
		{
			IFile file = (IFile) editor.getEditorInput().getAdapter(IFile.class);
			jsxCodePath = file.getRawLocation().makeAbsolute().toFile().getAbsolutePath();
		}
		List<ClassDefinition> classes;
		{
			Builder builder = Activator.getDefault().getJsxCommandBuilder();
			builder.jsxSource(jsxCodePath);
			try {
				classes = Jsx.getInstance().parse(builder.build());
			} catch (IOException e) {
				Activator.getDefault().getLog()
					.log(new Status(Status.ERROR, Activator.PLUGIN_ID, "raise error", e));
				return;
			} catch (JsonFormatException e) {
				Activator.getDefault().getLog()
					.log(new Status(Status.ERROR, Activator.PLUGIN_ID, "raise error", e));
				return;
			} catch (InterruptedException e) {
				Activator.getDefault().getLog()
					.log(new Status(Status.ERROR, Activator.PLUGIN_ID, "raise error", e));
				return;
			}
			classes = AstHelper.filterByDefinedFile(classes, jsxCodePath);
		}

		TreeViewer viewer = getTreeViewer();
		viewer.setInput(classes);
		viewer.refresh();
	}


	private static class JsxContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof ClassDefinition) {
				List<Member> members = ((ClassDefinition) parentElement).getMembers();
				List<Member> newList = new ArrayList<Member>();
				for (Member member : members) {
					if (member.isFunction()) {
						newList.add(member);
					} else {
						newList.add(member);
						System.out.println(member);
					}
				}

				return newList.toArray();
			} else if (parentElement instanceof Member) {
				return new Object[0];
			} else if (parentElement instanceof List) {
				@SuppressWarnings("unchecked")
				List<ClassDefinition> classes = (List<ClassDefinition>) parentElement;
				return classes.toArray();
			} else {
				throw new IllegalStateException("unknown class "
						+ parentElement.getClass().getCanonicalName());
			}
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
			if (element instanceof ClassDefinition) {
				ClassDefinition clazz = (ClassDefinition) element;
				return clazz.getName();
			} else if (element instanceof Member) {
				Member member = (Member) element;
				if (((Member) element).isFunction()) {
					return member.getNameToken().getValue();
				} else {
					return member.getName();
				}
			} else {
				throw new IllegalStateException("unknown class "
						+ element.getClass().getCanonicalName());
			}
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof ClassDefinition) {
				ClassDefinition clazz = (ClassDefinition) element;
				if (clazz.isInterface()) {
					return JsxImages.INTERFACE_ICON.createImage();
				} else if (clazz.isMixin()) {
					return JsxImages.MIXIN_ICON.createImage();
				} else {
					return JsxImages.CLASS_ICON.createImage();
				}
			} else if (element instanceof Member) {
				Member member = (Member) element;
				if (member.isFunction()) {
					return JsxImages.METHOD_ICON.createImage();
				} else {
					return JsxImages.VARIABLE_ICON.createImage();
				}
			} else {
				throw new IllegalStateException("unknown class "
						+ element.getClass().getCanonicalName());
			}
		}
	}
}
