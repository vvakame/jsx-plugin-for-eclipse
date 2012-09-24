package net.vvakame.ide.jsx;

import java.util.ResourceBundle;

import net.vvakame.ide.jsx.editors.JsxEditorMessages;
import net.vvakame.ide.jsx.editors.preference.PreferenceConstants;
import net.vvakame.jsx.wrapper.Jsx;
import net.vvakame.jsx.wrapper.Jsx.Builder;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	/** The plug-in ID */
	public static final String PLUGIN_ID = "jsx.eclipse.plugin.core"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
		.getBundle(JsxEditorMessages.class.getSimpleName());


	/**
	 * The constructor
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * get string from resource bundle.
	 * @param key
	 * @return {@link String}
	 * @author vvakame
	 */
	public static String getString(String key) {
		return RESOURCE_BUNDLE.getString(key);
	}

	/**
	 * get resouce bundle.
	 * @return {@link ResourceBundle}
	 * @author vvakame
	 */
	public static ResourceBundle getResourceBundle() {
		return RESOURCE_BUNDLE;
	}

	/**
	 * get JSX command builder with JSX bin path and Node.js bin path.
	 * @return {@link Builder}
	 * @author vvakame
	 */
	public Builder getJsxCommandBuilder() {
		Builder builder = new Jsx.Builder();
		String jsxPath = getPreferenceStore().getString(PreferenceConstants.JsxPath);
		String nodeJsPath = getPreferenceStore().getString(PreferenceConstants.NodeJsPath);
		builder.setJsxPath(jsxPath);
		builder.setNodeJsPath(nodeJsPath);

		return builder;
	}
}
