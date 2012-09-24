package net.vvakame.ide.jsx.editors.preference;

import net.vvakame.ide.jsx.Activator;
import net.vvakame.ide.jsx.editors.JsxEditorMessages;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * JSX Editor preference.
 * @author vvakame
 */
public class JsxEditorPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	/**
	 * the constructor.
	 * @category constructor
	 */
	public JsxEditorPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	@Override
	public void init(IWorkbench workbench) {
	}

	@Override
	protected void createFieldEditors() {
		addField(new FileFieldEditor(PreferenceConstants.JsxPath,
				JsxEditorMessages.Preference_jsxPath, getFieldEditorParent()));
		addField(new FileFieldEditor(PreferenceConstants.NodeJsPath,
				JsxEditorMessages.Preference_nodejsPath, getFieldEditorParent()));
	}
}
