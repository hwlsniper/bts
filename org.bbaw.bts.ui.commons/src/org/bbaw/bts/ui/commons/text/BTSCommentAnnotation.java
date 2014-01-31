package org.bbaw.bts.ui.commons.text;

import org.bbaw.bts.btsmodel.BTSComment;
import org.bbaw.bts.btsmodel.BTSIdentifiableItem;

public class BTSCommentAnnotation extends BTSModelAnnotation {

	public static final String TYPE = "org.bbaw.bts.ui.text.modelAnnotation.comment";

	private BTSComment comment;

	public BTSCommentAnnotation(BTSIdentifiableItem model, BTSComment comment) {
		super(model);
		this.setComment(comment);
	}

	@Override
	public String getType() {
		return TYPE;
	}

	public BTSComment getComment() {
		return comment;
	}

	public void setComment(BTSComment comment) {
		this.comment = comment;
	}

}
