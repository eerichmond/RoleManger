package com.eerichmond.core.security;

import com.eerichmond.core.domain.Department;
import com.eerichmond.core.domain.Person;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RoleRelationshipTest {

	@Test
	public void toString_returnsRoleAndToPartyId() {
		Person person = new Person(1L, "First", "First");
		
		Department dept = new Department("DEPT1");

        Association association = new Association(person, dept, AssociationRole.EMPLOYEE);
		
		assertThat(association.toString(), is("Employee of DEPT1"));
	}
	
}
