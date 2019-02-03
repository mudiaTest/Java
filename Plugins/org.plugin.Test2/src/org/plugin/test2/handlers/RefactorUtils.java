package org.plugin.test2.handlers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.internal.corext.refactoring.nls.KeyValuePair;
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
	private ICompilationUnit compilationUnit;
	
	/*
	 * Zwraca klasê nazwan¹
	 */
	private IType getTitleClass(ICompilationUnit unit) throws Exception
	{
		for(IJavaElement s : Arrays.asList(unit.getChildren()).stream().filter(a->a.getElementType() == IJavaElement.TYPE).collect(Collectors.toList()) ) {
			if (Flags.isPublic(((IType)s).getFlags()))
				return (IType)s;
		}
		return null;
	}
	
	/*
	 * Zwraca nadrzêdn¹ klasê/interface
	 */
	public IType getParentType(IJavaElement elem) throws Exception
	{
		if (elem.getParent() == null)
			return null;
		/*
		 * IJavaElement.TYPE to klasa lub interface
		 */
		else if (elem.getParent().getElementType() == IJavaElement.TYPE)
			return (IType)elem.getParent();
		else 
			return getParentType(elem.getParent());
		
		/*for(IJavaElement s : Arrays.asList(unit.getChildren()).stream().filter(a->a.getElementType() == IJavaElement.TYPE).collect(Collectors.toList()) ) {
			if (Flags.isPublic(((IType)s).getFlags()))
				return (IType)s;
		}
		return null;*/
	}
	
	/*
	 * Zwraca nadrzêdn¹ metodê
	 */
	public IMethod getParentMethod(IJavaElement elem) throws Exception
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
	
	/*
	 * Zwraca unit dla ustalonego selectionProvider
	 */
	public ICompilationUnit getCompilationUnit()
	{
		if (compilationUnit != null)
			return compilationUnit;
		else {
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
	}
	
	/*
	 * Zwraca textSelection dla ustalonego selectionProvider
	 */
	public ITextSelection getSelection() {
		return (ITextSelection) selectionProvider.getSelection();
	}
	
	/*
	 * Zwraca metodê, której fragment zaznaczyliœmy
	 */
	public IMethod getEnclosingMethodOld() {
        IJavaElement selected;
		try {
			selected = getCompilationUnit().getElementAt(getSelection().getOffset());
			return getParentMethod(selected);				
		} catch (Exception e) {
			e.printStackTrace();
		} 
        return null;
	}	
	
	/*
	 * 
	 */
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
				IType parentClass = getParentType(selected);
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
		this.compilationUnit = getCompilationUnit();
	}
	
	//-------------------------------------------------------
	
	/*
	 * Zwraca metodê, której fragment zaznaczyliœmy
	 */
	public IJavaElement getSelectedElement() {
        IJavaElement selected;
		try {
			return getCompilationUnit().codeSelect(getSelection().getOffset(), 0)[0];//selected[0].class = LocalVariable
		} catch (Exception e) {
			e.printStackTrace();
		} 
        return null;
	}	
	
	public <T> T getEnclosingByType(IJavaElement elem, Class<T> clazz, boolean returnSelf) {
		//elem.getc
		if (returnSelf && (clazz == IJavaElement.class || clazz.isInstance(elem)))
			return (T)elem;
		else if (elem.getParent() == null)
			return null;
		else 
			return getEnclosingByType(elem.getParent(), clazz, returnSelf);
	}
	
	private boolean isEnclosing(ASTNode node, int position) {
		return node.getStartPosition() <= position && 
				node.getStartPosition() + node.getLength() >= position;
	}
	
	public <T> ASTNode getEnclosingNodeByType(int position, Class<T> clazz) {
		ASTNode result = null;
		if (clazz == null || clazz == MethodDeclaration.class)
		{
			setMethodDeclarations();
			for(MethodDeclaration md : methodDeclarations) {
				if (isEnclosing(md, position) && (result == null || result.getStartPosition() < md.getStartPosition()))
					result = md;
			}
		}
		if (clazz == null || clazz == TypeDeclaration.class) {
			setTypeDeclarations();
			for(TypeDeclaration td : typeDeclarations.values()) {
				if (isEnclosing(td, position) && (result == null || result.getStartPosition() < td.getStartPosition()))
						result = td;
			}	
		}
		if (clazz == null || clazz == VariableDeclarationFragment.class) {
			setVariableDeclarationFragments();
			for(List<VariableDeclarationFragment> lvf : variableDeclarationFragments.values()) {
				for(VariableDeclarationFragment vf : lvf)
			
				if (isEnclosing(vf, position) && (result == null || result.getStartPosition() < vf.getStartPosition()))
						result = vf;
			}
		}
		if (clazz == null || clazz == VariableDeclarationStatement.class) {
			setVariableDeclarationStatements();
			for(VariableDeclarationStatement vs : variableDeclarationStatements.values()) {			
				if (isEnclosing(vs, position) &&  (result == null || result.getStartPosition() < vs.getStartPosition()))
					result = vs;
			}
		}
		return result;
	}
	
	public <T> T getEnclosingByTypeForSelected(Class<T> clazz, boolean returnSelf) {		
		IJavaElement selected = getSelectedElement();
		if (selected != null)
			return getEnclosingByType(selected, clazz, returnSelf);
		else
			return null;
	}
	
	public IJavaElement getEnclosingElement(boolean returnSelf) {
		return getEnclosingByTypeForSelected(IJavaElement.class ,returnSelf);
	}
	
	public IJavaElement getEnclosingElement() {
		return getEnclosingElement(true);
	}
	
	public IMethod getEnclosingMethod(boolean returnSelf) {
		return getEnclosingByTypeForSelected(IMethod.class ,returnSelf);
	}
	
	public IMethod getEnclosingMethod() {
		return getEnclosingMethod(true);
	}
	
	public IType getEnclosingType(boolean returnSelf) {
		return getEnclosingByTypeForSelected(IType.class ,returnSelf);
	}
	
	public IType getEnclosingType() {
		return getEnclosingType(true);
	}
	
	
	
	/*
	 * Visitor
	 */
	
	//Parsuje na podstawie dostarczonych obiektów
	private CompilationUnit parse() {
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(getCompilationUnit());
        parser.setResolveBindings(true);
        return (CompilationUnit) parser.createAST(null); // parse
    }
	
	
    private List<MethodDeclaration> methodDeclarations = null;
     //private Map<ASTNode, VariableDeclarationExpression> sariableExpressions = null;
    private Map<ASTNode, TypeDeclaration> typeDeclarations = null;
     //private Map<ASTNode, TypeDeclarationStatement> typeDeclarationStatements = null;
     //private Map<ASTNode, VariableDeclaration> variableDeclarations = null;
    private Map<String, List<VariableDeclarationFragment>> variableDeclarationFragments = null;
    private Map<ASTNode, VariableDeclarationStatement> variableDeclarationStatements = null;
      
    private Map<ICompilationUnit, MethodVisitor> visitorMap = new HashMap<>();
    
    public IJavaElement getSelectedCode() {
    	IJavaElement result = null;
    	try {
    		IJavaElement[] res = getCompilationUnit().codeSelect(getSelection().getOffset(), 0);
    		result = res.length>0 ? res[0] : null;
    		//result = getCompilationUnit().codeSelect(getSelection().getOffset(), 0)[0];
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
    	return result;
    }
    
    public IJavaElement getSelectedObject() {
    	IJavaElement result = null;
    	try {
			result = getCompilationUnit().getElementAt(getSelection().getOffset());
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
    	return result;
    }
	
	public MethodVisitor getVisitor(ICompilationUnit unit) {
		/*IJavaElement[] selected = null;
		try {
			selected = unit.codeSelect(getSelection().getOffset(), 0);//selected[0].class = LocalVariable
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		if (!visitorMap.containsKey(unit)) {
			CompilationUnit parse = parse();
	        MethodVisitor visitor = new MethodVisitor();
	        parse.accept(visitor);
	        visitorMap.put(unit, visitor);
	        return visitor;
		}
		else
			return visitorMap.get(unit);
	}
	
    public List<MethodDeclaration> getMethodDeclarations(boolean refrersh) {
    	if (refrersh)
    		setMethodDeclarations();
		return methodDeclarations;
	}

//	public Map<ASTNode, VariableDeclarationExpression> getSariableExpressions(boolean refrersh) {
//	if (refrersh)
//		setVariableDeclarationExpressions();
//		return sariableExpressions;
//	}

	public Map<ASTNode, TypeDeclaration> getTypeDeclarations(boolean refrersh) {
    	if (refrersh)
    		setTypeDeclarations();
		return typeDeclarations;
	}

//	public Map<ASTNode, TypeDeclarationStatement> getTypeDeclarationStatements(boolean refrersh) {
//	if (refrersh)
//		return typeDeclarationStatements;
//	}

//	public Map<ASTNode, VariableDeclaration> getVariableDeclarations(boolean refrersh) {
//	if (refrersh)
//		setTypeDeclarationStatements();
//		return variableDeclarations;
//	}

	public Map<String, List<VariableDeclarationFragment>> getVariableDeclarationFragments(boolean refrersh) {
    	if (refrersh)
    		setVariableDeclarationFragments();
		return variableDeclarationFragments;
	}

	public Map<ASTNode, VariableDeclarationStatement> getVariableDeclarationStatements(boolean refrersh) {
    	if (refrersh)
    		setVariableDeclarationStatements();
		return variableDeclarationStatements;
	}
	
	private void setMethodDeclarations() {
		methodDeclarations = getVisitor(getCompilationUnit()).getMethodDeclarations();//methodName.identifier(), startPosition()
	}
	private void setSariableExpressions() {
		//sariableExpressions = getVisitor(getCompilationUnit()).getSariableExpressions(); // --
	}
	private void setTypeDeclarations() {
		typeDeclarations = getVisitor(getCompilationUnit()).getTypeDeclarations();//typeName.identifier(), startPosition()
	}
    private void setTypeDeclarationStatements() {
    	//typeDeclarationStatements = getVisitor(getCompilationUnit()).getTypeDeclarationStatements(); // --
    }
    private void setVariableDeclarations() {
    	//variableDeclarations = getVisitor(getCompilationUnit()).getVariableDeclarations();// --
    }
   	private void setVariableDeclarationFragments() {
   		variableDeclarationFragments = getVisitor(getCompilationUnit()).getVariableDeclarationFragments();// key, array[...].startPosition
   	}
    private void setVariableDeclarationStatements() {
    	variableDeclarationStatements = getVisitor(getCompilationUnit()).getVariableDeclarationStatements();//value.variableDeclarationStatements.array[...].variableNameIdentifier
    }

	private void fillByVisitor() {   
    	MethodVisitor visitor = getVisitor(getCompilationUnit());
        setMethodDeclarations();//methodName.identifier(), startPosition()
        //setSariableExpressions(); // --
        setTypeDeclarations();//typeName.identifier(), startPosition()
        //setTypeDeclarationStatements(); // --
        //setVariableDeclarations();// --
        setVariableDeclarationFragments();// key, array[...].startPosition
        setVariableDeclarationStatements();//value.variableDeclarationStatements.array[...].variableNameIdentifier
        int t = 0;
	}
	
	/*
	 * ASTNode
	 */
	
	public String getNodeName(ASTNode node) {
		if ( MethodDeclaration.class.isInstance(node))
			return ((MethodDeclaration)node).getName().getIdentifier();
		else if ( TypeDeclaration.class.isInstance(node))
			return ((TypeDeclaration)node).getName().getIdentifier();
		else if ( VariableDeclarationFragment.class.isInstance(node))
			return ((VariableDeclarationFragment)node).getName().getIdentifier();
		else
			return "";
	}
	
	public ASTNode getBestNodeForSubquery(ASTNode methodNode, ASTNode variableNode) {
		ASTNode result = null;
        if (variableNode != null && methodNode != null) 
        	result = variableNode.getStartPosition() > methodNode.getStartPosition() ? variableNode : methodNode;
        else if (variableNode == null) 
        	result = methodNode != null ? methodNode : null;
        else if (methodNode == null) 
        	result = variableNode != null ? variableNode : null;
        return result;
	}
	
	public ASTNode getBestNodeForSubquery() {
        ASTNode methodNode = getEnclosingNodeByType(getSelection().getOffset(), MethodDeclaration.class);
        ASTNode variableNode = getEnclosingNodeByType(getSelection().getOffset(), VariableDeclarationFragment.class);
        return getBestNodeForSubquery(methodNode, variableNode);
	}
}

