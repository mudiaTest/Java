package org.plugin.test2.handlers;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
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
	
	private IType getActiveVirtualTable(ICompilationUnit key) {
		return Global.ActiveVirtualTable.get(key);
	}
	private void setActiveVirtualTable(ICompilationUnit key, IType val) {
		 Global.ActiveVirtualTable.put(key, val);
	}
	
	private MethodDeclaration getMethodDeclaration(ASTNode node) {
		if (node.getParent() == null)
			return null;
		else if (node.getParent() instanceof MethodDeclaration)
			return (MethodDeclaration)node.getParent();
		else 
			return getMethodDeclaration(node.getParent());
	}

	@Override
	public Object execute(ExecutionEvent event) throws org.eclipse.core.commands.ExecutionException {
		String info = "";
		
		String id = "org.eclipse.jdt.ui.PackageExplorer";
		
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewPart viewPart = page.findView(id);
		ISelectionProvider selProvider = viewPart.getSite().getSelectionProvider();
		//event na wybieranie(select)
		selProvider.addSelectionChangedListener(new MySelectionListener());
		
		/**/
		
		try {     
			/*
			 * part to aktywny edytor (AE) na aktywnej "stronie"
			 */
		    IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		    if ( part instanceof ITextEditor ) {
		    	/*
		    	 * AE rzutowany na ITextEditor
		    	 */
		        final ITextEditor editor = (ITextEditor)part;
		        /*
		         * cc - zestaw narzêdzi do zmiany kodu unitu 
		         */
		        RefactorUtils cc = new RefactorUtils(editor.getEditorInput(), editor.getDocumentProvider(), editor.getSelectionProvider());

		        
		        //------------------------------------------------------------------
		        /*
		         * TESTY
		         */
	            IJavaElement sel1 = cc.getSelectedCode();
	            IJavaElement sel2 = cc.getSelectedObject();
	            
	            IJavaElement e11 = null;
	            IJavaElement e21 = null;
	            IJavaElement e31 = null;
	            IJavaElement e41 = null;
	            if (sel1 != null) {
	            	e11 = cc.getEnclosingByType(sel1,IMethod.class,true);
	            	e31 = cc.getEnclosingByType(sel1,IType.class,true);
	            }
	            if (sel2 != null) {
	            	e21 = cc.getEnclosingByType(sel2,IMethod.class,true);
	            	e41 = cc.getEnclosingByType(sel2,IType.class,true);
	            }
	            
	            IJavaElement e1 = cc.getEnclosingElement();
	            IMethod e2 = cc.getEnclosingMethod();
	            IType e3 = cc.getEnclosingType();
	            
	            ASTNode node1 = cc.getEnclosingNodeByType(cc.getSelection().getOffset(), null);
	            ASTNode node2 = cc.getEnclosingNodeByType(cc.getSelection().getOffset(), MethodDeclaration.class);
	            ASTNode node3 = cc.getEnclosingNodeByType(cc.getSelection().getOffset(), TypeDeclaration.class);
	            ASTNode node4 = cc.getEnclosingNodeByType(cc.getSelection().getOffset(), VariableDeclarationFragment.class);
	            ASTNode node5 = cc.getEnclosingNodeByType(cc.getSelection().getOffset(), VariableDeclarationStatement.class);
	            
	            
	            String enclosingNodeName = cc.getNodeName(node1);
	            info = enclosingNodeName;
	            Class enclosingNodeClass;
	            //---------------------------------------------------------------------
	            
	            
	            /*
	             * Pobieranie nazwy u klasy enclosing type
	             */
	            ASTNode bestNode = cc.getBestNodeForSubquery(node2, node4);
	            
        		if (bestNode != null)
	            {
	            	enclosingNodeName = cc.getNodeName(bestNode);
	            	enclosingNodeClass = bestNode.getClass();
	            }
	            else{
	            	enclosingNodeName = "";
	            	enclosingNodeClass = null;
	            }
	            
	            
	            int i = 0;
		    }
		} catch ( Exception ex ) {
		    ex.printStackTrace();
		}
		
		/*
		 * Wystawienie Popup z informacj¹ kontroln¹
		 */		
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(
				window.getShell(),
				"Test2",
				info);
		return null;
	}
}
