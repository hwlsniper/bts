 
package org.bbaw.bts.ui.corpus.handlers;

import javax.inject.Named;

import org.bbaw.bts.btsmodel.AdministrativDataObject;
import org.bbaw.bts.btsmodel.BTSDBBaseObject;
import org.bbaw.bts.commons.BTSConstants;
import org.bbaw.bts.core.commons.BTSCoreConstants;
import org.bbaw.bts.core.controller.generalController.EditingDomainController;
import org.bbaw.bts.core.corpus.controller.generalController.CorpusCommandController;
import org.bbaw.bts.ui.commons.navigator.StructuredViewerProvider;
import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class DeleteHandler {
	@Execute
	public void execute(
			@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Object selection,
			EditingDomainController editingDomainController,
			CorpusCommandController commandController, @Optional @Active MPart activePart,
			final Shell shell) {
		
		if (selection instanceof EObject) {
			AdministrativDataObject selectedObject = (AdministrativDataObject) selection;

			if (MessageDialog.openConfirm(shell, 
					"Confirm deletion", 
					"Object " + selectedObject.get_id() + " will be moved to trash. Proceed?")) {
				selectedObject
						.setState(BTSConstants.OBJECT_STATE_TERMINATED);
			}

			//General Command Controller... save!
			commandController.save((BTSDBBaseObject) selection);
			if (activePart != null)
			{
				Object o = activePart.getObject();
				if (o instanceof StructuredViewerProvider)
				{
					StructuredViewerProvider viewerProvider = (StructuredViewerProvider) o;
					viewerProvider.getActiveStructuredViewer().refresh();
				}
			}
		}
	}
	@CanExecute
	public boolean canExecute(
			@Optional @Named(IServiceConstants.ACTIVE_SELECTION) BTSDBBaseObject selection,
			@Optional @Named(BTSCoreConstants.CORE_EXPRESSION_MAY_DELETE) Boolean mayDelete) {
		if (mayDelete != null && mayDelete.booleanValue())
		{
			if (selection != null && selection instanceof AdministrativDataObject) {
				return !((AdministrativDataObject)selection).getState().equals(BTSConstants.OBJECT_STATE_TERMINATED);
			}
		}
		return false;
	}	
}