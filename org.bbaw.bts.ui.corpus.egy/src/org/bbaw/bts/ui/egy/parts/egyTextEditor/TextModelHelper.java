package org.bbaw.bts.ui.egy.parts.egyTextEditor;

import java.util.HashMap;
import java.util.Iterator;

import org.bbaw.bts.btsmodel.BTSAmbivalence;
import org.bbaw.bts.btsmodel.BTSAmbivalenceItem;
import org.bbaw.bts.btsmodel.BTSIdentifiableItem;
import org.bbaw.bts.btsmodel.BTSLemmaCase;
import org.bbaw.bts.btsmodel.BTSMarker;
import org.bbaw.bts.btsmodel.BTSSenctence;
import org.bbaw.bts.btsmodel.BTSSentenceItem;
import org.bbaw.bts.btsmodel.BTSText;
import org.bbaw.bts.btsmodel.BTSTextContent;
import org.bbaw.bts.btsmodel.BTSWord;
import org.bbaw.bts.btsmodel.BtsmodelFactory;
import org.bbaw.bts.commons.BTSConstants;
import org.bbaw.bts.corpus.text.egy.egyDsl.AbstractMarker;
import org.bbaw.bts.corpus.text.egy.egyDsl.Ambivalence;
import org.bbaw.bts.corpus.text.egy.egyDsl.Case;
import org.bbaw.bts.corpus.text.egy.egyDsl.Marker;
import org.bbaw.bts.corpus.text.egy.egyDsl.Sentence;
import org.bbaw.bts.corpus.text.egy.egyDsl.SentenceItem;
import org.bbaw.bts.corpus.text.egy.egyDsl.SentenceItemNoAmbivalence;
import org.bbaw.bts.corpus.text.egy.egyDsl.TextContent;
import org.bbaw.bts.corpus.text.egy.egyDsl.TextItem;
import org.bbaw.bts.corpus.text.egy.egyDsl.VersFrontierMarker;
import org.bbaw.bts.corpus.text.egy.egyDsl.VersbreakMarker;
import org.bbaw.bts.corpus.text.egy.egyDsl.Word;
import org.bbaw.bts.ui.egy.parts.egyTextEditor.model.ModelAnnotation;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

public class TextModelHelper {

	private HashMap<Position, Annotation> annotationMap;
	private BTSTextContent oldTextContent;

	public void updateModelFromTextContent(BTSText text, EObject eo,
			IAnnotationModel am) {
		if (am != null) {
			loadAnnotationMapping(eo, am);
		}
		if (text.getTextContent() == null) {
			text.setTextContent(BtsmodelFactory.eINSTANCE
					.createBTSTextContent());

		}
 else {
			oldTextContent = text.getTextContent();
			text.getTextContent().getTextItems().clear();
		}
		if (eo instanceof TextContent) {
			TextContent tc = (TextContent) eo;
			BTSSenctence lastModelSentence = null;
			for (TextItem item : tc.getItems()) {
				if (item instanceof Sentence) {
					Sentence sentence = (Sentence) item;
					BTSSenctence modelSentence = null;
					INode node = NodeModelUtils.getNode(sentence);
					ModelAnnotation ma = (ModelAnnotation) annotationMap
							.get(new Position(node.getOffset(), node
									.getLength()));
					if (ma != null) {
						modelSentence = (BTSSenctence) ma.getModelObject();
					}
					if (modelSentence == null) {
						modelSentence = makeNewModelSentence(sentence,
								lastModelSentence, text);
					}
					// add model sentence to btstextcontent
					if (lastModelSentence != null) {
						int lastIndex = text.getTextContent().getTextItems()
								.indexOf(lastModelSentence);
						text.getTextContent().getTextItems()
								.add(lastIndex + 1, modelSentence);
					} else {
						text.getTextContent().getTextItems()
								.add(0, modelSentence);
					}
					BTSIdentifiableItem lastItem = null;
					EList<BTSSentenceItem> oldSentenceItems = modelSentence
							.getSentenceItems();
					modelSentence.getSentenceItems().clear();
					for (SentenceItem si : sentence.getItems()) {

						lastItem = updateItemFromTextContent(si, lastItem,
								sentence,
								modelSentence);
					}
					lastModelSentence = modelSentence;
				}
			}
		}

	}

	private BTSSenctence makeNewModelSentence(Sentence sentence,
			BTSSenctence lastModelSentence, BTSText text) {
		BTSSenctence newModelSentence = BtsmodelFactory.eINSTANCE
				.createBTSSenctence();

		return newModelSentence;
	}

	private BTSIdentifiableItem updateItemFromTextContent(SentenceItem si,
			BTSIdentifiableItem lastItem, Sentence sentence,
			BTSSenctence modelSentence) {
		if (si instanceof Word) {
			return updateWordFromTextContent((Word) si, lastItem, sentence,
					modelSentence);
		} else if (si instanceof AbstractMarker) {
			return updateMarkerFromTextContent((AbstractMarker) si, lastItem,
					sentence,
					modelSentence);
		} else if (si instanceof Ambivalence) {
			return updateAmbivalenceFromTextContent((Ambivalence) si, lastItem,
					sentence,
					modelSentence);
		}
		return null;

	}

	private BTSIdentifiableItem updateAmbivalenceFromTextContent(
			Ambivalence ambivalence, BTSIdentifiableItem lastItem,
			Sentence sentence,
			BTSSenctence modelSentence) {
		BTSAmbivalence modelAmbivalence = null;
		INode node = NodeModelUtils.getNode(ambivalence);
		ModelAnnotation ma = (ModelAnnotation) annotationMap.get(new Position(
				node.getOffset() - 1, node.getLength()));
		if (ma != null) {
			modelAmbivalence = (BTSAmbivalence) ma.getModelObject();
		}
		if (modelAmbivalence == null) {
			modelAmbivalence = makeNewModelAmbivalence(ambivalence, lastItem,
					modelSentence);
		}
		// add modelMarker to btsSentence
		if (lastItem != null) {
			int lastIndex = modelSentence.getSentenceItems().indexOf(lastItem);
			modelSentence.getSentenceItems().add(lastIndex + 1,
					modelAmbivalence);
		} else {
			modelSentence.getSentenceItems().add(0, modelAmbivalence);
		}
		EList<BTSLemmaCase> oldLemmaCases = modelAmbivalence.getCases();
		modelAmbivalence.getCases().clear();
		BTSLemmaCase lastModelCase = null;
		for (Case item : ambivalence.getCases()) {
			BTSLemmaCase modelCase = null;
			INode nodeCase = NodeModelUtils.getNode(item);
			ModelAnnotation maCase = (ModelAnnotation) annotationMap
					.get(new Position(nodeCase.getOffset() - 1, nodeCase
							.getLength()));
			if (maCase != null) {
				modelCase = (BTSLemmaCase) maCase.getModelObject();
			}
			if (modelCase == null) {
				modelCase = makeNewModelCase(ambivalence, lastModelCase);
			}
			// add model case to ambivalence
			if (lastModelCase != null) {
				int lastIndex = modelAmbivalence.getCases().indexOf(
						lastModelCase);
				modelAmbivalence.getCases().add(lastIndex + 1, modelCase);
			} else {
				modelAmbivalence.getCases().add(0, modelCase);
			}
			modelCase.setName(item.getName());
			BTSAmbivalenceItem lastAmbivalenceItem = null;
			EList<BTSAmbivalenceItem> oldAmbivalenceItems = modelCase
					.getScenario();
			modelCase.getScenario().clear();
			for (SentenceItemNoAmbivalence ai : item.getItems()) {

				lastAmbivalenceItem = updateAmbivalenceItemFromTextContent(ai,
						lastAmbivalenceItem, item, modelCase);
			}
			lastModelCase = modelCase;

		}
		return modelAmbivalence;

	}

	private BTSAmbivalenceItem updateAmbivalenceItemFromTextContent(
			SentenceItemNoAmbivalence ai,
			BTSAmbivalenceItem lastAmbivalenceItem,
			Case item, BTSLemmaCase modelCase) {
		if (ai instanceof Word) {
			return updateWordFromTextAmbivalence((Word) ai,
					lastAmbivalenceItem, item, modelCase);
		} else if (ai instanceof Marker) {
			return updateMarkerFromTextAmbivalence((Marker) ai,
					lastAmbivalenceItem, item, modelCase);
		}
		return null;
	}

	private BTSAmbivalenceItem updateMarkerFromTextAmbivalence(Marker marker,
			BTSAmbivalenceItem lastAmbivalenceItem, Case item,
			BTSLemmaCase modelCase) {
		BTSMarker modelMarker = null;
		INode node = NodeModelUtils.getNode(marker);
		ModelAnnotation ma = (ModelAnnotation) annotationMap.get(new Position(
				node.getOffset(), node.getLength()));
		if (ma != null) {
			modelMarker = (BTSMarker) ma.getModelObject();
		}
		if (modelMarker == null) {
			modelMarker = makeNewModelMarker(null, null, null);
		}
		// add modelMarker to btsSentence
		if (lastAmbivalenceItem != null) {
			int lastIndex = modelCase.getScenario()
					.indexOf(lastAmbivalenceItem);
			modelCase.getScenario().add(lastIndex + 1, modelMarker);
		} else {
			modelCase.getScenario().add(0, modelMarker);
		}
		String text = node.getText();
		if (text != null) {
			if (!text.equals(modelMarker.getValue())) {
				modelMarker.setValue(text);
			}
		} else {
			modelMarker.setValue(null);
		}
		return modelMarker;
	}

	private BTSAmbivalenceItem updateWordFromTextAmbivalence(Word word,
			BTSAmbivalenceItem lastAmbivalenceItem, Case item,
			BTSLemmaCase modelCase) {
		BTSWord modelWord = null;
		INode node = NodeModelUtils.getNode(word);
		// System.out.println("word " + node.getText() + " node offset "
		// + node.getOffset() + " node length " + node.getLength());
		ModelAnnotation ma = (ModelAnnotation) annotationMap.get(new Position(
				node.getOffset(), node.getLength()));
		if (ma != null) {
			modelWord = (BTSWord) ma.getModelObject();
		}
		if (modelWord == null) {
			modelWord = makeNewModelWord(null, null, null);
		}
		// add modelWord to btssentence
		if (lastAmbivalenceItem != null) {
			int lastIndex = modelCase.getScenario()
					.indexOf(lastAmbivalenceItem);
			modelCase.getScenario().add(lastIndex + 1, modelWord);
		} else {
			modelCase.getScenario().add(0, modelWord);
		}
		String text = node.getText();
		if (!modelWord.getWChar().equals(text)) {
			modelWord.setWChar(text);
		}
		return modelWord;
	}

	private BTSLemmaCase makeNewModelCase(Ambivalence ambivalence,
			BTSLemmaCase lastModelCase) {
		BTSLemmaCase newModelLemmaCase = BtsmodelFactory.eINSTANCE
				.createBTSLemmaCase();
		return newModelLemmaCase;
	}

	private BTSAmbivalence makeNewModelAmbivalence(Ambivalence ambivalence,
			BTSIdentifiableItem lastItem, BTSSenctence modelSentence) {
		BTSAmbivalence newModelAmbivalence = BtsmodelFactory.eINSTANCE
				.createBTSAmbivalence();
		return newModelAmbivalence;
	}

	private BTSIdentifiableItem updateMarkerFromTextContent(AbstractMarker si,
			BTSIdentifiableItem lastItem, Sentence sentence,
			BTSSenctence modelSentence) {
		BTSMarker modelMarker = null;
		INode node = NodeModelUtils.getNode(si);
		ModelAnnotation ma = (ModelAnnotation) annotationMap.get(new Position(
				node.getOffset() - 1, node.getLength()));
		if (ma != null) {
			modelMarker = (BTSMarker) ma.getModelObject();
		}
 else if (modelMarker == null) {
			ma = (ModelAnnotation) annotationMap.get(new Position(node
					.getOffset() - 1, node.getLength() + 1));
			if (ma != null) {
				modelMarker = (BTSMarker) ma.getModelObject();
			}
		}
		if (modelMarker == null) {
			modelMarker = makeNewModelMarker(sentence, lastItem, modelSentence);
		}
		// add modelMarker to btsSentence
		if (lastItem != null) {
			int lastIndex = modelSentence.getSentenceItems().indexOf(lastItem);
			modelSentence.getSentenceItems().add(lastIndex + 1, modelMarker);
		} else {
			modelSentence.getSentenceItems().add(0, modelMarker);
		}
		if (si instanceof Marker) {
			Marker m = (Marker) si;
			if (m.getType() != null) {
				if (!m.getType().equals(modelMarker.getType())) {
					modelMarker.setType(m.getType());
				}
			} else {
				modelMarker.setType(null);
			}
			if (m.getName() != null) {
				if (!m.getName().equals(modelMarker.getName())) {
					modelMarker.setName(m.getName());
				}
			} else {
				modelMarker.setName(null);
			}
		} else if (si instanceof VersbreakMarker) {
			if (modelMarker.getType() == null
					|| !modelMarker.getType().equals(
							BTSConstants.TEXT_VERS_BREAK_MARKER)) {
				modelMarker.setType(BTSConstants.TEXT_VERS_BREAK_MARKER);
			}
		} else if (si instanceof VersFrontierMarker) {
			if (modelMarker.getType() == null
					|| !modelMarker.getType().equals(
							BTSConstants.TEXT_VERS_FRONTIER_MARKER)) {
				modelMarker.setType(BTSConstants.TEXT_VERS_FRONTIER_MARKER);
			}
		}
		return modelMarker;

	}

	private BTSMarker makeNewModelMarker(Sentence sentence,
			BTSIdentifiableItem lastItem, BTSSenctence modelSentence) {
		BTSMarker newModelMarker = BtsmodelFactory.eINSTANCE.createBTSMarker();

		return newModelMarker;
	}

	private BTSIdentifiableItem updateWordFromTextContent(Word word,
			BTSIdentifiableItem lastItem, Sentence sentence,
			BTSSenctence modelSentence) {
		BTSWord modelWord = null;
		INode node = NodeModelUtils.getNode(word);
		System.out.println("word " + node.getText() + " node offset "
				+ node.getOffset() + " node length " + node.getLength());
		ModelAnnotation ma = (ModelAnnotation) annotationMap
.get(new Position(
				node.getOffset(), node.getLength()));
		if (ma != null) {
			modelWord = (BTSWord) ma.getModelObject();
		}
		if (modelWord == null) {
			modelWord = makeNewModelWord(sentence, lastItem, modelSentence);
		}
		// add modelWord to btssentence
		if (lastItem != null) {
			int lastIndex = modelSentence.getSentenceItems().indexOf(lastItem);
			modelSentence.getSentenceItems().add(lastIndex + 1, modelWord);
		} else {
			modelSentence.getSentenceItems().add(0, modelWord);
		}
		String text = node.getText();
		if (modelWord.getWChar() == null || !modelWord.getWChar().equals(text)) {
			modelWord.setWChar(text);
		}
		return modelWord;

	}

	private BTSWord makeNewModelWord(Sentence sentence,
			BTSIdentifiableItem lastItem, BTSSenctence modelSentence) {
		BTSWord newModelWord = BtsmodelFactory.eINSTANCE.createBTSWord();
		newModelWord.setWChar("");

		return newModelWord;
	}

	private void loadAnnotationMapping(EObject TextContent, IAnnotationModel am) {
		annotationMap = new HashMap<Position, Annotation>();
		Iterator it = am.getAnnotationIterator();
		while (it.hasNext()) {
			Annotation an = (Annotation) it.next();
			if (an instanceof ModelAnnotation) {
				if (((ModelAnnotation) an).getModelObject() != null) {
					Position pos = am.getPosition(an);
					annotationMap.put(pos, an);
				}
			}
		}

	}

}