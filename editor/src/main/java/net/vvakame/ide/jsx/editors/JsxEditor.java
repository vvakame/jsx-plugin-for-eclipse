package net.vvakame.ide.jsx.editors;

import net.vvakame.ide.jsx.editors.jsxprovider.JsxDocumentProvider;
import net.vvakame.ide.jsx.editors.misc.ColorManager;
import net.vvakame.ide.jsx.editors.viewerconfiguration.JsxConfiguration;

import org.eclipse.ui.editors.text.TextEditor;

public class JsxEditor extends TextEditor {

	private ColorManager colorManager;

	public JsxEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new JsxConfiguration(colorManager));
		setDocumentProvider(new JsxDocumentProvider());
	}

	@Override
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}
}
