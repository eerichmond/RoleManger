package com.eerichmond.core.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Persistable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Cacheable
public class Institution extends Organization implements Persistable<Long> {

	private static final long serialVersionUID = 1L;

    @NotBlank @Column(length = 25)
	private String code;
	
	/**
	 * Empty constructor for proxy/ORM/serialization libraries.
	 */
	public Institution() { super(); }
	
	/**
	 * Constructor for testing. Does NOT load up the record.
	 * @param institutionCode the institution code.
	 */
	public Institution(String institutionCode) {
		super(institutionCode);
	}
	
	public String getCode() { return code; }

	@Override
	public String toString() { return getName(); }

	@Override
	public HierarchyLevel getHierarchyLevel() { return HierarchyLevel.BRANCH; }
	
}