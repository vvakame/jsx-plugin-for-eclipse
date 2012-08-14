package net.vvakame.ide.jsx.editors;

import net.vvakame.ide.jsx.editors.jsxprovider.XMLDocumentProvider;
import net.vvakame.ide.jsx.editors.misc.ColorManager;
import net.vvakame.ide.jsx.editors.viewerconfiguration.XMLConfiguration;

import org.eclipse.ui.editors.text.TextEditor;

public class JsxEditor extends TextEditor {

	private ColorManager colorManager;

	public JsxEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new XMLConfiguration(colorManager));
		setDocumentProvider(new XMLDocumentProvider());
	}

	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}
}
