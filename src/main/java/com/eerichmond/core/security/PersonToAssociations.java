package com.eerichmond.core.security;

import com.eerichmond.core.domain.BaseObject;
import com.eerichmond.core.domain.BusinessKey;
import com.eerichmond.core.domain.Person;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;

import java.util.Collection;

public class PersonToAssociations extends BaseObject<BusinessKey> {

	@BusinessKey
	@JsonProperty
	private Person person;

	@BusinessKey
	@JsonProperty
	private Collection<Association> associations = Sets.newHashSet();

	public PersonToAssociations(Person person, Collection<Association> associations) {
		this.person = person;
		this.associations = associations;
	}

	public PersonToAssociations(Person person) {
		this.person = person;
	}

	public Person getPerson() { return person; }
	public void setPerson(Person person) { this.person = person; }

	public Collection<Association> getAssociations() { return associations; }
	public void setAssociations(Collection<Association> associations) { this.associations = associations; }

}
