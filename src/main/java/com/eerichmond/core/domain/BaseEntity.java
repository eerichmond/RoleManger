package com.eerichmond.core.domain;

import javax.persistence.Id;

public abstract class BaseEntity extends BaseObject<Id> {

	private static final long serialVersionUID = 1L;

	/**
	 * Empty constructor for subclass.
	 */
	protected BaseEntity() { super(); }

}