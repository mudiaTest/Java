package org.plugin.test2.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class MethodVisitor extends ASTVisitor {
	
    List<MethodDeclaration> methods = new ArrayList<>();
    Map<ASTNode, VariableDeclaration> variables = new HashMap<>(); 
    Map<ASTNode, VariableDeclarationExpression> variableExpressions = new HashMap<>(); 
    Map<ASTNode, VariableDeclarationStatement> variableStatements = new HashMap<>(); 
    Map<ASTNode, VariableDeclarationFragment> variableFragments = new HashMap<>(); 

    @Override
    public boolean visit(MethodDeclaration node) {
        methods.add(node);
        return super.visit(node);
    }
    
   /* @Override
    public boolean visit(VariableDeclaration node) {
    	variables.put(null, node);
    	return super.visit(node);
    }*/
    
    @Override
    public boolean visit(VariableDeclarationExpression node) {
    	variableExpressions.put(node.getParent(), node);
    	return super.visit(node);
    }
    
    @Override
    public boolean visit(VariableDeclarationStatement node) {
    	variableStatements.put(node.getParent(), node);
    	return super.visit(node);
    }
    
    @Override
    public boolean visit(VariableDeclarationFragment node) {
    	variableFragments.put(node.getParent(), node);
    	return super.visit(node);
    }
    
    

    public List<MethodDeclaration> getMethods() {
        return methods;
    }
    
    public Map<ASTNode, VariableDeclaration> getVariables() {
    	return variables;
    }
    
    public Map<ASTNode, VariableDeclarationExpression> getsariableExpressions() {
    	return variableExpressions;
    }
    
    public Map<ASTNode, VariableDeclarationStatement> getVariablesStatements() {
    	return variableStatements;
    }
    
    public Map<ASTNode, VariableDeclarationFragment> getVariablesFragments() {
    	return variableFragments;
    }

}
