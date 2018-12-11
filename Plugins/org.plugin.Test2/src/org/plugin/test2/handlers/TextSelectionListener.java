package org.plugin.test2.handlers;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

public class TextSelectionListener implements ISelectionChangedListener {

	private IEditorInput editorInput;
	private IDocumentProvider documentProvider;
	private ISelectionProvider selectionProvider;
	
	@Override
	public void selectionChanged(SelectionChangedEvent arg0) {
		// TODO Auto-generated method stub
		
		 /*ISelection sel = editor.getSelectionProvider().getSelection();
	        if ( sel instanceof TextSelection ) {

	            // Here is your String
	            final TextSelection textSel = (TextSelection)sel;

	        }*/
		System.out.println("Text selection chanded.");
		
		//ISelection sel = editor.getSelectionProvider().getSelection();		
		ITextSelection sel = (ITextSelection) selectionProvider.getSelection();
		
		
		
        if ( sel instanceof TextSelection ) {

            // Here is your String
            final TextSelection textSel = (TextSelection)sel;
            textSel.getText();
                        
            IDocument document = documentProvider.getDocument(editorInput);            
            
            IJavaElement elem = JavaUI.getEditorInputJavaElement(editorInput);
            if (elem instanceof ICompilationUnit) {
                ICompilationUnit unit = (ICompilationUnit) elem;
                IJavaElement selected;
				try {
					selected = unit.getElementAt(sel.getOffset());
					System.out.println("selected=" + selected);
					System.out.println("selected.class=" + selected.getClass());
					for(IJavaElement s : unit.getChildren()) {
						int t = 0;
						s.getElementType()
					}
					IType code = unit.createType("private class Test{}", unit.getChildren()[0], true, null);
					int t = 0;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            
            int t = 0;
        }
	}

	public TextSelectionListener(IEditorInput editorInput, IDocumentProvider documentProvider, ISelectionProvider selectionProvider) {
		System.out.println("TextSelectionListener created");
		this.editorInput = editorInput;
		this.documentProvider = documentProvider;
		this.selectionProvider = selectionProvider;
	}
}

