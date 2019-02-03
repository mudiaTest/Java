package org.plugin.test2.handlers;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
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
	
	private IType getTitleClass(ICompilationUnit unit) throws Exception
	{
		for(IJavaElement s : Arrays.asList(unit.getChildren()).stream().filter(a->a.getElementType() == IJavaElement.TYPE).collect(Collectors.toList()) ) {
			if (Flags.isPublic(((IType)s).getFlags()))
				return (IType)s;
		}
		return null;
	}
	
	private IType getParentClass(IJavaElement elem) throws Exception
	{
		if (elem.getParent() == null)
			return null;
		else if (elem.getParent().getElementType() == IJavaElement.TYPE)
			return (IType)elem.getParent();
		else 
			return getParentClass(elem.getParent());
		
		/*for(IJavaElement s : Arrays.asList(unit.getChildren()).stream().filter(a->a.getElementType() == IJavaElement.TYPE).collect(Collectors.toList()) ) {
			if (Flags.isPublic(((IType)s).getFlags()))
				return (IType)s;
		}
		return null;*/
	}
	private IMethod getParentMethod(IJavaElement elem) throws Exception
	{
		if (elem.getElementType() == IJavaElement.METHOD)
			return (IMethod)elem;
		else if (elem.getParent() == null)
			return null;
		else if (elem.getParent().getElementType() == IJavaElement.METHOD)
			return (IMethod)elem.getParent();
		else 
			return null;
	}
	
	
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
            System.out.println("----"+textSel.getText());
                
            if (!textSel.getText().equalsIgnoreCase("")) {
	            IDocument document = documentProvider.getDocument(editorInput);            
	            
	            IJavaElement elem = JavaUI.getEditorInputJavaElement(editorInput);
	            if (elem instanceof ICompilationUnit) {
	                ICompilationUnit unit = (ICompilationUnit) elem;
	                IJavaElement selected;
					try {
						selected = unit.getElementAt(textSel.getOffset()); // odda metodê
	//					System.out.println("selected=" + selected);
	//					System.out.println("selected.class=" + selected.getClass());
						
						IType titleClazz = getTitleClass(unit);
						IType parentClass = getParentClass(selected);
						IMethod parentMethod = getParentMethod(selected);
						
						
						IType code = unit.createType("private class Test{}", null, true, null);
						
						int t = 0;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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

