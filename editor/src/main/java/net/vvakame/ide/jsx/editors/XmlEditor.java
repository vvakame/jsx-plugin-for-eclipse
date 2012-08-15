package net.vvakame.ide.jsx.editors;

import net.vvakame.ide.jsx.editors.misc.ColorManager;
import net.vvakame.ide.xml.editors.viewerconfiguration.XMLConfiguration;
import net.vvakame.ide.xml.editors.xmlprovider.XMLDocumentProvider;

import org.eclipse.ui.editors.text.TextEditor;

public class XmlEditor extends TextEditor {

	private ColorManager colorManager;

	public XmlEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new XMLConfiguration(colorManager));
		setDocumentProvider(new XMLDocumentProvider());
	}

	@Override
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}
}
