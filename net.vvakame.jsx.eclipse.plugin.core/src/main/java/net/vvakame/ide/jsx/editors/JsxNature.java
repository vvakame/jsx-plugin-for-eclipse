package net.vvakame.ide.jsx.editors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * JSX nature.
 * @author vvakame
 */
public class JsxNature implements IProjectNature {

	/** nature id */
	public static final String NATURE_ID = "jsx.eclipse.plugin.core.JsxNature";

	private IProject project;


	@Override
	public void configure() throws CoreException {
		// add builder
		IProjectDescription desc = project.getDescription();
		List<ICommand> list = Arrays.asList(desc.getBuildSpec());
		if (list.contains(JsxBuilder.BUILDER_ID)) {
			return;
		}
		list = new ArrayList<ICommand>(list);
		ICommand command = desc.newCommand();
		command.setBuilderName(JsxBuilder.BUILDER_ID);
		list.add(command);
		desc.setBuildSpec(list.toArray(new ICommand[0]));
		project.setDescription(desc, null);
	}

	@Override
	public void deconfigure() throws CoreException {
		// remove builder
		IProjectDescription desc = getProject().getDescription();
		List<ICommand> list = Arrays.asList(desc.getBuildSpec());
		if (!list.contains(JsxBuilder.BUILDER_ID)) {
			return;
		}
		list = new ArrayList<ICommand>(list);
		list.remove(JsxBuilder.BUILDER_ID);
		desc.setBuildSpec(list.toArray(new ICommand[0]));
	}

	@Override
	public IProject getProject() {
		return this.project;
	}

	@Override
	public void setProject(IProject project) {
		this.project = project;
	}
}
