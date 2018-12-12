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

public class RefactorUtils {

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
//			return getParentMethod(elem.getParent());
			return null;
	}
	
	private String getDefinition(ITextSelection selection) {
		//String selected = textSel.getText();
		return null;
	}
	
	public ICompilationUnit getCompilationUnit()
	{
		ITextSelection sel = (ITextSelection) selectionProvider.getSelection();
        System.out.println("---> Selected text:"+sel.getText());
        if (!sel.getText().equalsIgnoreCase("")) {
            IDocument document = documentProvider.getDocument(editorInput);            
            IJavaElement elem = JavaUI.getEditorInputJavaElement(editorInput);
            if (elem instanceof ICompilationUnit) {
                return (ICompilationUnit) elem;
            }
        }
        return null;
	}
	
	public ITextSelection getSelection() {
		return (ITextSelection) selectionProvider.getSelection();
	}
	
	public IMethod getEnclosingMethod(ICompilationUnit unit) {
        IJavaElement selected;
		try {
			selected = unit.getElementAt(getSelection().getOffset());
			return getParentMethod(selected);				
		} catch (Exception e) {
			e.printStackTrace();
		} 
        return null;
	}
	
	public void doCreateClass() {
		ITextSelection sel = (ITextSelection) selectionProvider.getSelection();
        if (!sel.getText().equalsIgnoreCase("")) {
            ICompilationUnit unit = getCompilationUnit();
            if (unit == null) {
            	System.out.println(" ---> No ICompilationUnit found.");
            	return;
            }
            IJavaElement selected;
			try {
				selected = unit.getElementAt(sel.getOffset()); // odda metodê
				IType titleClazz = getTitleClass(unit);
				IType parentClass = getParentClass(selected);
				IMethod parentMethod = getParentMethod(selected);
				String definition = getDefinition(sel);
				IType code = unit.createType("private class Test{}", null, true, null);
				//getCompilationUnit - naglowki z unitu
				int t = 0;
			} catch (Exception e) {
				e.printStackTrace();
			}
        int t = 0;
        }
	}

	public RefactorUtils(IEditorInput editorInput, IDocumentProvider documentProvider, ISelectionProvider selectionProvider) {
		System.out.println("TextSelectionListener created");
		this.editorInput = editorInput;
		this.documentProvider = documentProvider;
		this.selectionProvider = selectionProvider;
	}
}

