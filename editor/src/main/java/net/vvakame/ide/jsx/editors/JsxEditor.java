package net.vvakame.ide.jsx.editors;

import net.vvakame.ide.jsx.editors.jsxprovider.JsxDocumentProvider;
import net.vvakame.ide.jsx.editors.misc.ColorManager;
import net.vvakame.ide.jsx.editors.viewerconfiguration.JsxConfiguration;

import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

/**
 * Implementation of JSX code editor.
 * @author vvakame
 */
public class JsxEditor extends TextEditor {

	private ColorManager colorManager;

	private JsxOutlinePage outlinePage;


	/**
	 * the constructor.
	 * @category constructor
	 */
	public JsxEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new JsxConfiguration(colorManager));
		setDocumentProvider(new JsxDocumentProvider());
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		if (IContentOutlinePage.class.equals(adapter)) {
			if (outlinePage == null) {
				outlinePage = new JsxOutlinePage(this);
			}
			return outlinePage;
		}
		return super.getAdapter(adapter);
	}

	@Override
	public void dispose() {
		colorManager.dispose();
		if (outlinePage != null) {
			outlinePage.dispose();
			outlinePage = null;
		}
		super.dispose();
	}
}
