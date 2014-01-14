package com.eerichmond.core.domain;

import com.google.common.base.Preconditions;
import org.joda.time.DateTime;
import org.springframework.data.domain.Auditable;
import org.springframework.data.domain.Persistable;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@MappedSuperclass
public abstract class AuditableEntity<T extends Serializable> extends BaseEntity implements Auditable<Party, T>, Persistable<T> {
	
	private static final long serialVersionUID = 1L;

    @NotNull
	private DateTime createdDate;

    @NotNull
	private DateTime lastModifiedDate;
	
	@ManyToOne(fetch=FetchType.LAZY) @JoinColumn @NotNull
	private Party createdBy;
	
	@ManyToOne(fetch=FetchType.LAZY) @JoinColumn @NotNull
	private Party lastModifiedBy;

	public abstract T getId();

	public DateTime getCreatedDate() { return createdDate; }
	/**
	 * Sets the created date.
	 * @param createdDate the date this entity was created.
	 * @throws IllegalStateException if the created date is already set.
	 */
	public void setCreatedDate(DateTime createdDate) {
		Preconditions.checkState(this.createdDate == null, "The created date is already set");
		this.createdDate = createdDate;
	}
	
	public DateTime getLastModifiedDate() { return lastModifiedDate; }
	public void setLastModifiedDate(DateTime lastModifiedDate) { this.lastModifiedDate = lastModifiedDate; }
	
	public Party getCreatedBy() { return createdBy; }
	/**
	 * Sets the created by.
	 * @param createdBy the person / software system creating this entity.
	 * @throws IllegalStateException if the created by is already set.
	 */
	public void setCreatedBy(Party createdBy) {
		Preconditions.checkState(this.createdBy == null, "The created by is already set");
		this.createdBy = createdBy;
	}
	
	public Party getLastModifiedBy() { return lastModifiedBy; }
	public void setLastModifiedBy(Party lastModifiedBy) {
		Preconditions.checkNotNull(lastModifiedBy);
		this.lastModifiedBy = lastModifiedBy;
	}

}