/**
 */
package org.bbaw.bts.corpus.btsCorpusModel.impl;

import java.util.Collection;

import org.bbaw.bts.btsmodel.impl.BTSReferencableItemImpl;
import org.bbaw.bts.corpus.btsCorpusModel.BTSLemmaSubentry;
import org.bbaw.bts.corpus.btsCorpusModel.BTSPassport;
import org.bbaw.bts.corpus.btsCorpusModel.BTSWord;
import org.bbaw.bts.corpus.btsCorpusModel.BtsCorpusModelPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>BTS Lemma Subentry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.bbaw.bts.corpus.btsCorpusModel.impl.BTSLemmaSubentryImpl#getPassport <em>Passport</em>}</li>
 *   <li>{@link org.bbaw.bts.corpus.btsCorpusModel.impl.BTSLemmaSubentryImpl#getWords <em>Words</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BTSLemmaSubentryImpl extends BTSReferencableItemImpl implements BTSLemmaSubentry {
	/**
	 * The cached value of the '{@link #getPassport() <em>Passport</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassport()
	 * @generated
	 * @ordered
	 */
	protected BTSPassport passport;

	/**
	 * The cached value of the '{@link #getWords() <em>Words</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWords()
	 * @generated
	 * @ordered
	 */
	protected EList<BTSWord> words;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BTSLemmaSubentryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BtsCorpusModelPackage.Literals.BTS_LEMMA_SUBENTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BTSPassport getPassport() {
		return passport;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPassport(BTSPassport newPassport, NotificationChain msgs) {
		BTSPassport oldPassport = passport;
		passport = newPassport;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BtsCorpusModelPackage.BTS_LEMMA_SUBENTRY__PASSPORT, oldPassport, newPassport);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPassport(BTSPassport newPassport) {
		if (newPassport != passport) {
			NotificationChain msgs = null;
			if (passport != null)
				msgs = ((InternalEObject)passport).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BtsCorpusModelPackage.BTS_LEMMA_SUBENTRY__PASSPORT, null, msgs);
			if (newPassport != null)
				msgs = ((InternalEObject)newPassport).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BtsCorpusModelPackage.BTS_LEMMA_SUBENTRY__PASSPORT, null, msgs);
			msgs = basicSetPassport(newPassport, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BtsCorpusModelPackage.BTS_LEMMA_SUBENTRY__PASSPORT, newPassport, newPassport));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BTSWord> getWords() {
		if (words == null) {
			words = new EObjectContainmentEList<BTSWord>(BTSWord.class, this, BtsCorpusModelPackage.BTS_LEMMA_SUBENTRY__WORDS);
		}
		return words;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BtsCorpusModelPackage.BTS_LEMMA_SUBENTRY__PASSPORT:
				return basicSetPassport(null, msgs);
			case BtsCorpusModelPackage.BTS_LEMMA_SUBENTRY__WORDS:
				return ((InternalEList<?>)getWords()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BtsCorpusModelPackage.BTS_LEMMA_SUBENTRY__PASSPORT:
				return getPassport();
			case BtsCorpusModelPackage.BTS_LEMMA_SUBENTRY__WORDS:
				return getWords();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BtsCorpusModelPackage.BTS_LEMMA_SUBENTRY__PASSPORT:
				setPassport((BTSPassport)newValue);
				return;
			case BtsCorpusModelPackage.BTS_LEMMA_SUBENTRY__WORDS:
				getWords().clear();
				getWords().addAll((Collection<? extends BTSWord>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case BtsCorpusModelPackage.BTS_LEMMA_SUBENTRY__PASSPORT:
				setPassport((BTSPassport)null);
				return;
			case BtsCorpusModelPackage.BTS_LEMMA_SUBENTRY__WORDS:
				getWords().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case BtsCorpusModelPackage.BTS_LEMMA_SUBENTRY__PASSPORT:
				return passport != null;
			case BtsCorpusModelPackage.BTS_LEMMA_SUBENTRY__WORDS:
				return words != null && !words.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //BTSLemmaSubentryImpl