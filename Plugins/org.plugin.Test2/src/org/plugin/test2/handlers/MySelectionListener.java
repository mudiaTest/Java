package org.plugin.test2.handlers;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;

public class MySelectionListener implements ISelectionChangedListener {

	public MySelectionListener() {
		System.out.println("SelectionListener created");
	}
	
	@Override
	public void selectionChanged(SelectionChangedEvent arg0) {
		//setEnabled(!event.getSelection().isEmpty());
	    System.out.println("Select project (sub)object");	
	    //IStructuredSelection.iterator()
	}

}
