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

	static final ImageDescriptor CLASS_ICON = create("icons/class.png");

	static final ImageDescriptor INTERFACE_ICON = create("icons/interface.png");

	static final ImageDescriptor MIXIN_ICON = create("icons/mixin.png");

	static final ImageDescriptor METHOD_ICON = create("icons/method.png");

	static final ImageDescriptor VARIABLE_ICON = create("icons/variable.png");


	private JsxImages() {
	}

	static ImageDescriptor create(String localPath) {
		Bundle bundle = Platform.getBundle("jsx-plugin-editor-for-eclipse");
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
