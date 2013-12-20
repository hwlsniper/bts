/*
* generated by Xtext
*/
package org.bbaw.bts.corpus.text.egy.dsl.validation;

import org.bbaw.bts.corpus.text.egy.dsl.egyBts.DisputableReading;
import org.bbaw.bts.corpus.text.egy.dsl.egyBts.EgyBtsPackage;
import org.bbaw.bts.corpus.text.egy.dsl.egyBts.Expanded;
import org.bbaw.bts.corpus.text.egy.dsl.egyBts.Word;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.validation.Check;

/**
 * Custom validation rules. 
 *
 * see http://www.eclipse.org/Xtext/documentation.html#validation
 */
public class EgyBtsJavaValidator extends org.bbaw.bts.corpus.text.egy.dsl.validation.AbstractEgyBtsJavaValidator {

	@Check
	public void checkWordItemsDoNotRepeteThemselves(Word word) {
		if (!word.getWChar().isEmpty()) {
			if (word.getWChar()
					.get(0)
					.getClass()
					.getName()
					.startsWith(
							"org.bbaw.bts.corpus.text.egy.dsl.secrets.impl.Interfix")) {
				error("Interfix may not be at the beginning of a word!", word,
						EgyBtsPackage.Literals.WORD__WCHAR, 0);
			}
			String last = "";
			int index = 0;
			for (EObject item : word.getWChar()) {
				index++;
				if (item.getClass().getName().equals(last)) {
					error("Items may not repete themselves", word,
							EgyBtsPackage.Literals.WORD__WCHAR, index - 1);
				}
				last = item.getClass().getName();
			}
			if (last.startsWith("org.bbaw.bts.corpus.text.egy.dsl.secrets.impl.Interfix")) {
				error("Interfix may not be at the end of a word!", word,
						EgyBtsPackage.Literals.WORD__WCHAR, index - 1);
			}
		}
	}

	@Check
	public void checkExpandedItemsDoNotRepeteThemselves(Expanded expanded) {
		if (!expanded.getWChar().isEmpty()) {
			String last = "";
			int index = 0;
			for (EObject item : expanded.getWChar()) {
				index++;
				if (item.getClass().getName().equals(last)) {
					error("Items may not repete themselves", expanded,
							EgyBtsPackage.Literals.EXPANDED__WCHAR, index - 1);
				}
				last = item.getClass().getName();
			}
			// if (last.startsWith("Interfix"))
			// {
			// error("Interfix may not be at the end of a word!", expanded,
			// SecretsPackage.Literals.EXPANDED__WCHAR, index - 1);
			// }
		}
	}

	@Check
	public void checkDisputableReadingItemsDoNotRepeteThemselves(
			DisputableReading bracket) {
		if (!bracket.getWChar().isEmpty()) {
			String last = "";
			int index = 0;
			for (EObject item : bracket.getWChar()) {
				index++;
				if (item.getClass().getName().equals(last)) {
					error("Items may not repete themselves", bracket,
							EgyBtsPackage.Literals.DISPUTABLE_READING__WCHAR,
							index - 1);
				}
				last = item.getClass().getName();
			}
			// if (last.startsWith("Interfix"))
			// {
			// error("Interfix may not be at the end of a word!", bracket,
			// SecretsPackage.Literals.DISPUTABLE_READING__WCHAR, index - 1);
			// }
		}
	}
}
