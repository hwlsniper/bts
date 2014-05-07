 
package org.bbaw.bts.ui.corpus.handlers;

import javax.inject.Named;

import org.bbaw.bts.btsmodel.BTSAnnotation;
import org.bbaw.bts.btsmodel.BTSCorpusObject;
import org.bbaw.bts.btsmodel.BTSObject;
import org.bbaw.bts.core.corpus.controller.partController.CorpusNavigatorController;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.swt.widgets.Shell;

public class AddAnnotationHandler {
	@Execute
	public void execute(
			@Named(IServiceConstants.ACTIVE_SELECTION) @Optional BTSObject selection,
			@Named(IServiceConstants.ACTIVE_SHELL) final Shell shell,
			EventBroker eventBroker,
			CorpusNavigatorController corpusNavigatorController) {
		if (selection instanceof BTSCorpusObject) {
			final BTSAnnotation object = corpusNavigatorController
					.createNewAnnotation((BTSCorpusObject) selection);
			corpusNavigatorController.save(object);
			eventBroker.post("model_add/BTSAnnotation", object);
		}
	}
	
	
	@CanExecute
	public boolean canExecute(
			@Named(IServiceConstants.ACTIVE_SELECTION) @Optional BTSObject selection) {
		return (selection instanceof BTSCorpusObject && !(selection instanceof BTSAnnotation));
	}
		
}