package net.vvakame.ide.jsx.editors.preference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.vvakame.ide.jsx.editors.JsxEditorMessages;
import net.vvakame.ide.jsx.editors.JsxNature;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.dialogs.PropertyPage;

/**
 * JSX project preference.
 * @author vvakame
 */
public class JsxWorkbenchPropertyPage extends PropertyPage implements IWorkbenchPropertyPage {

	private Button check;


	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		check = new Button(composite, SWT.CHECK);
		check.setText(JsxEditorMessages.Workbench_addJsxNature);
		try {
			IProject project = (IProject) getElement();
			check.setSelection(project.hasNature(JsxNature.NATURE_ID()));
		} catch (CoreException e) {
			throw new IllegalStateException(e);
		}
		return composite;
	}

	@Override
	public boolean performOk() {
		try {
			if (check.getSelection()) {
				addNature();
			} else {
				removeNature();
			}
			return true;
		} catch (CoreException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	void addNature() throws CoreException {
		IProject project = (IProject) getElement();
		IProjectDescription description = project.getDescription();
		List<String> list = Arrays.asList(description.getNatureIds());
		if (list.contains(JsxNature.NATURE_ID())) {
			return;
		}
		list = new ArrayList<String>(list);
		list.add(JsxNature.NATURE_ID());
		description.setNatureIds(list.toArray(new String[0]));
		project.setDescription(description, null);
	}

	void removeNature() throws CoreException {
		IProject project = (IProject) getElement();
		IProjectDescription description = project.getDescription();
		List<String> list = Arrays.asList(description.getNatureIds());
		if (!list.contains(JsxNature.NATURE_ID())) {
			return;
		}
		list = new ArrayList<String>(list);
		list.remove(JsxNature.NATURE_ID());
		description.setNatureIds(list.toArray(new String[0]));
		project.setDescription(description, null);
	}
}
