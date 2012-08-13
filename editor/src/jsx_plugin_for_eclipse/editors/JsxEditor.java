package jsx_plugin_for_eclipse.editors;

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
