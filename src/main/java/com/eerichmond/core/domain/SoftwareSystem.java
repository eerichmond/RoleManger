package com.eerichmond.core.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Table;

import javax.persistence.*;

@Entity
@SecondaryTable(name="SOFTWARE_SYSTEM")
@Table(appliesTo="SOFTWARE_SYSTEM", optional=false)	// Need this for force an inner join
@DiscriminatorValue("SoftwareSystem")
@Cacheable
public class SoftwareSystem extends Party {

	private static final long serialVersionUID = 1L;
	
	@Column(table="SOFTWARE_SYSTEM") @JsonProperty
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
		super(new Long(name.hashCode()));

		this.name = name;
	}

}
