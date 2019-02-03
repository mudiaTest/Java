package org.plugin.test2.handlers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;

public class Global {
	static Map<ICompilationUnit, IType> ActiveVirtualTable = new HashMap<>();  
}
