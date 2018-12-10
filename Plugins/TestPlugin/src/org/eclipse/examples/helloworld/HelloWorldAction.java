package org.eclipse.examples.helloworld;



import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;


/** HelloWorldAction is a simple example of using an
 * action set to extend the Eclipse Workbench with a menu 
 * and toolbar action that prints the "Hello World" message.
 */

public class HelloWorldAction implements IWorkbenchWindowActionDelegate  {
	IWorkbenchWindow activeWindow = null;

	/** Run the action. Display the Hello World message
	 */		
	public void run(IAction proxyAction) {
		// proxyAction has UI information from manifest file (ignored)
		Shell shell = activeWindow.getShell();
		MessageDialog.openInformation(shell, "Hello World", "Hello World!");
	}

	// IActionDelegate method
	public void selectionChanged(IAction proxyAction, ISelection selection) {
		// do nothing, action is not dependent on the selection
	}
	
	// IWorkbenchWindowActionDelegate method
	public void init(IWorkbenchWindow window) {
		activeWindow = window;
	}
	
	// IWorkbenchWindowActionDelegate method
	public void dispose() {
		//  nothing to do
	}
}
