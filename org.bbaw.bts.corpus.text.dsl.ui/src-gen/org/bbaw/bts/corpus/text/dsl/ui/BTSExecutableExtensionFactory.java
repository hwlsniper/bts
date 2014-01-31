/*
 * generated by Xtext
 */
package org.bbaw.bts.corpus.text.dsl.ui;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

import org.bbaw.bts.corpus.text.dsl.ui.internal.BTSActivator;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class BTSExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return BTSActivator.getInstance().getBundle();
	}
	
	@Override
	protected Injector getInjector() {
		return BTSActivator.getInstance().getInjector(BTSActivator.ORG_BBAW_BTS_CORPUS_TEXT_DSL_BTS);
	}
	
}
