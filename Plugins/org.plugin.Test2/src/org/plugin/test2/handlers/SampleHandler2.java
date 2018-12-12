package org.plugin.test2.handlers;

import java.util.concurrent.ExecutionException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
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

public class SampleHandler2 extends AbstractHandler {
	
	//Parsuje na podstawie dostarczonych obiektów
	private static CompilationUnit parse(ICompilationUnit unit) {
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(unit);
        parser.setResolveBindings(true);
        return (CompilationUnit) parser.createAST(null); // parse
    }

	@Override
	public Object execute(ExecutionEvent event) throws org.eclipse.core.commands.ExecutionException {
		
		String id = "org.eclipse.jdt.ui.PackageExplorer";
		
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewPart viewPart = page.findView(id);
		ISelectionProvider selProvider = viewPart.getSite().getSelectionProvider();
		//event na wybieranie 
		selProvider.addSelectionChangedListener(new SelectionListener());
		
		/**/
		
		try {               
		    IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		    if ( part instanceof ITextEditor ) {
		        final ITextEditor editor = (ITextEditor)part;
		        RefactorUtils cc = new RefactorUtils(editor.getEditorInput(), editor.getDocumentProvider(), editor.getSelectionProvider());
		        ICompilationUnit unit = cc.getCompilationUnit();
		        IMethod enclosingMethod = cc.getEnclosingMethod(unit);
		        if (unit == null) {
	            	System.out.println(" ---> No ICompilationUnit found.");
	            	return null;
	            }
		        CompilationUnit parse = parse(unit);
	            MethodVisitor visitor = new MethodVisitor();
	            parse.accept(visitor);
	            
	            MethodDeclaration enclosingMethodDeclaration = null;
	            //enclosingMethod.getNameRange() = 20799
	            for (MethodDeclaration method : visitor.getMethods()) {
	                if (method.getName().getStartPosition() == enclosingMethod.getNameRange().getOffset())
	                {
	                	enclosingMethodDeclaration = method;
	                	break;
	                }	                
	            }
	            int t = 0;
	            
		    }
		} catch ( Exception ex ) {
		    ex.printStackTrace();
		}
		/**/
		
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(
				window.getShell(),
				"Test2",
				"Hello, Eclipse world 2");
		return null;
	}
}
