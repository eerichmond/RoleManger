package com.eerichmond.core.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

@Entity
@Cacheable
public class SoftwareSystem extends Party {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty
	private String name;
	
	@Override
	public String getName() { return name; }
	
	/**
	 * Empty constructor for proxy/ORM libraries.
	 */
	public SoftwareSystem() { super(); }
	
	/**
	 * Constructor for testing. Does NOT load up data.
	 * @param name the name of the software system.
	 */
	public SoftwareSystem(String name) {
		super(Long.valueOf(name.hashCode()));

		this.name = name;
	}

}
