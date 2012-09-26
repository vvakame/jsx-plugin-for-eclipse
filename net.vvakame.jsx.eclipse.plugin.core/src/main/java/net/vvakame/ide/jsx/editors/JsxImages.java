package net.vvakame.ide.jsx.editors;

import java.net.MalformedURLException;
import java.net.URL;

import net.vvakame.ide.jsx.Activator;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;

/**
 * Images.
 * @author vvakame
 */
public class JsxImages {

	static final ImageDescriptor PROJECT_DECORATOR = create("icons/project_decorator.gif");

	static final ImageDescriptor CLASS_ICON = create("icons/class.gif");

	static final ImageDescriptor INTERFACE_ICON = create("icons/interface.gif");

	static final ImageDescriptor MIXIN_ICON = create("icons/mixin.gif");

	static final ImageDescriptor METHOD_ICON = create("icons/method.gif");

	static final ImageDescriptor VARIABLE_ICON = create("icons/variable.gif");


	private JsxImages() {
	}

	static ImageDescriptor create(String localPath) {
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		URL baseUrl = bundle.getEntry("/");
		try {
			URL url = new URL(baseUrl, localPath);
			return ImageDescriptor.createFromURL(url);
		} catch (MalformedURLException e) {
			Activator.getDefault().getLog()
				.log(new Status(Status.ERROR, Activator.PLUGIN_ID, "raise error", e));
		}
		return null;
	}
}
