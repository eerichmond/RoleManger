package com.eerichmond.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Department")
@Cacheable
public class Department extends Organization {

	private static final long serialVersionUID = 1L;

	@Column(name="DEPT_CODE")
	private String deptCode;
	
	/**
	 * Empty constructor for proxy/ORM libraries.
	 */
	public Department() { super(); }
	
	/**
	 * Constructor for testing. Does not load up data.
	 * @param name the name of the department. The hash code of the name is used as the ID.
	 */
	public Department(String name) { super(name); }

	@JsonProperty
	public String getDeptCode() { return deptCode; }

	@Override
	public HierarchyLevel getHierarchyLevel() { return HierarchyLevel.BRANCH; }

}
