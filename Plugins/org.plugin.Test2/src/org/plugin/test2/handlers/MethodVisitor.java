package org.plugin.test2.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class MethodVisitor extends ASTVisitor {
	
    List<MethodDeclaration> methodDeclaration = new ArrayList<>();
    Map<ASTNode, VariableDeclaration> variables = new HashMap<>(); 
    Map<ASTNode, VariableDeclarationExpression> variableDeclarationExpressions = new HashMap<>(); 
    Map<ASTNode, VariableDeclarationStatement> variableDeclarationStatements = new HashMap<>(); 
    Map<String, List<VariableDeclarationFragment>> variableDeclarationFragments = new HashMap<>(); 
    Map<ASTNode, TypeDeclaration> typeDeclaration = new HashMap<>(); 
    Map<ASTNode, TypeDeclarationStatement> typeDeclarationStatement = new HashMap<>(); 

    @Override
    public boolean visit(MethodDeclaration node) {
        methodDeclaration.add(node);
        return super.visit(node);
    }
    
   /* @Override
    public boolean visit(VariableDeclaration node) {
    	variables.put(null, node);
    	return super.visit(node);
    }*/
    
    @Override
    public boolean visit(VariableDeclarationExpression node) {
    	variableDeclarationExpressions.put(node.getParent(), node);
    	return super.visit(node);
    }
    
    @Override
    public boolean visit(VariableDeclarationStatement node) {
    	variableDeclarationStatements.put(node.getParent(), node);
    	return super.visit(node);
    }
    
    @Override
    public boolean visit(VariableDeclarationFragment node) {
    	String key = node.getName().getIdentifier();
    	if (!variableDeclarationFragments.containsKey(key))
    		variableDeclarationFragments.put(key, new ArrayList<VariableDeclarationFragment>());
    	List<VariableDeclarationFragment> frags = variableDeclarationFragments.get(key);
    	frags.add(node);
    	return super.visit(node);
    }
    
    
    @Override
	public boolean visit(TypeDeclaration node) {
    	typeDeclaration.put(node.getParent(), node);
		return super.visit(node);
	}

	@Override
	public boolean visit(TypeDeclarationStatement node) {
		typeDeclarationStatement.put(node.getParent(), node);
		return super.visit(node);
	}
	
	//--------------------------------------------

	public List<MethodDeclaration> getMethodDeclarations() {
        return methodDeclaration;
    }
    
    public Map<ASTNode, VariableDeclaration> getVariableDeclarations() {
    	return variables;
    }
    
    public Map<ASTNode, VariableDeclarationExpression> getSariableExpressions() {
    	return variableDeclarationExpressions;
    }
    
    public Map<ASTNode, VariableDeclarationStatement> getVariableDeclarationStatements() {
    	return variableDeclarationStatements;
    }
    
    public Map<String, List<VariableDeclarationFragment>> getVariableDeclarationFragments() {
    	return variableDeclarationFragments;
    }
    
    public Map<ASTNode, TypeDeclaration> getTypeDeclarations() {
    	return typeDeclaration;
    }
    
    public Map<ASTNode, TypeDeclarationStatement> getTypeDeclarationStatements() {
    	return typeDeclarationStatement;
    }

}
