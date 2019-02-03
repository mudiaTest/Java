package org.plugin.test2.handlers;

import java.util.concurrent.ExecutionException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;

public class SampleHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws org.eclipse.core.commands.ExecutionException {
		String id = "org.eclipse.jdt.ui.PackageExplorer";
		
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewPart viewPart = page.findView(id);
		ISelectionProvider selProvider = viewPart.getSite().getSelectionProvider();
		//event na wybieranie 
		selProvider.addSelectionChangedListener(new MySelectionListener());
		
		/**/
		
		try { 
			/*
			 * Uzyskanie dostêpu do edytora 
			 */
		    IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		    if ( part instanceof ITextEditor ) {
		        final ITextEditor editor = (ITextEditor)part;
		        //IDocumentProvider prov = editor.getDocumentProvider();
		        //IDocumentAdapter doc = prov.getDocument( editor.getEditorInput() );
		        ISelectionProvider textSelectionProv = editor.getSelectionProvider();
		        textSelectionProv.addSelectionChangedListener(new TextSelectionListener(editor.getEditorInput(), editor.getDocumentProvider(), editor.getSelectionProvider()));
		        
//		        ISelection sel = editor.getSelectionProvider().getSelection();
//		        if ( sel instanceof TextSelection ) {
//
//		            // Here is your String
//		            final TextSelection textSel = (TextSelection)sel;
//
//		        }
		    }
		} catch ( Exception ex ) {
		    ex.printStackTrace();
		}
		/**/
		
		
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(
				window.getShell(),
				"Test2",
				"Hello, Eclipse world");
		return null;
	}
}
