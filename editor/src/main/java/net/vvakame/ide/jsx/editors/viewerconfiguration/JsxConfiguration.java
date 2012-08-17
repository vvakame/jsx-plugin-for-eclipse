package net.vvakame.ide.jsx.editors.viewerconfiguration;

import static net.vvakame.ide.jsx.editors.misc.IJsxToken.*;

import java.util.Map;
import java.util.WeakHashMap;

import net.vvakame.ide.jsx.editors.misc.ColorManager;
import net.vvakame.ide.jsx.editors.misc.IJsxColorConstants;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class JsxConfiguration extends SourceViewerConfiguration {
	Map<Class<? extends ITokenScanner>, ITokenScanner> scannerHash = new WeakHashMap<Class<? extends ITokenScanner>, ITokenScanner>();

	private ColorManager colorManager;

	public JsxConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}

	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] { IDocument.DEFAULT_CONTENT_TYPE, JSX_KEYWORD,
				JSX_COMMENT, JSX_STRING };
	}

	protected ITokenScanner getJsxScanner() {
		if (scannerHash.containsKey(JsxScanner.class)) {
			return scannerHash.get(JsxScanner.class);
		} else {
			JsxScanner scanner = new JsxScanner(colorManager);
			scanner.setDefaultReturnToken(new Token(new TextAttribute(
					colorManager.getColor(IJsxColorConstants.DEFAULT))));
			scannerHash.put(JsxScanner.class, scanner);
			return scanner;
		}
	}

	protected ITokenScanner getBlockCommentScanner() {
		if (scannerHash.containsKey(BlockCommentScanner.class)) {
			return scannerHash.get(BlockCommentScanner.class);
		} else {
			BlockCommentScanner scanner = new BlockCommentScanner(colorManager);
			scanner.setDefaultReturnToken(new Token(new TextAttribute(
					colorManager.getColor(IJsxColorConstants.COMMENT))));
			scannerHash.put(BlockCommentScanner.class, scanner);
			return scanner;
		}
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		{
			DefaultDamagerRepairer dr = new DefaultDamagerRepairer(
					getJsxScanner());
			reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
			reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
		}

		{
			NonRuleBasedDamagerRepairer ndr = new NonRuleBasedDamagerRepairer(
					new TextAttribute(
							colorManager.getColor(IJsxColorConstants.COMMENT)));
			reconciler.setDamager(ndr, JSX_COMMENT);
			reconciler.setRepairer(ndr, JSX_COMMENT);
		}

		return reconciler;
	}
}