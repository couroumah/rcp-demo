 
package org.eclipse.e4.demo.mailapp.handlers;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.demo.mailapp.PreferenceDialog;

@SuppressWarnings("restriction")
public class PreferenceHandler {
	@Execute
	public void execute(IEclipseContext context) {
		PreferenceDialog dialog = ContextInjectionFactory.make(PreferenceDialog.class, context);
		dialog.open();
	}
		
}