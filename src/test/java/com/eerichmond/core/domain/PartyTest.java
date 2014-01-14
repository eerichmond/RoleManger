package com.eerichmond.core.domain;

import com.eerichmond.core.codes.ActiveStatusCode;
import com.eerichmond.core.security.AssociationRole;
import com.google.common.collect.Iterables;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class PartyTest {

	@Test
	public void getActiveAssociations_oneActiveOneInactive_returnsOne() {
        Person person = new Person(1L, "Harry", "Potter");
        Department department = new Department("Gryffindor");
        Institution school = new Institution("Hogwarts");

		person.addAssociation(AssociationRole.EMPLOYEE, department)
			.addAssociation(AssociationRole.STUDENT, school);

		Iterables.getFirst(person.getActiveAssociations(), null).setActiveStatusCode(ActiveStatusCode.INACTIVE);

		assertThat(person.getActiveAssociations().size(), is(1));
	}

	@Test
	public void findAssociationsByRole_oneMatchingRole_returnsDepartment() {
        Person person = new Person(1L, "Harry", "Potter");
        Department department = new Department("Gryffindor");

		person.addAssociation(AssociationRole.EMPLOYEE, department);
		
		// Assert
		Set<Department> associations = person.findAssociationsByRole(AssociationRole.EMPLOYEE);

		assertThat(associations.size(), is(1));
		assertThat(associations, contains(department));
	}

	@Test(expected = ClassCastException.class)
	public void findAssociationsByRole_oneMatchingRoleButWrongReturnType_returnsNothing() {
		Person person = new Person(1L, "First", "Last");
        Department department = new Department("Gryffindor");

		person.addAssociation(AssociationRole.EMPLOYEE, department);

		// Assert
		Set<Institution> associations = person.findAssociationsByRole(AssociationRole.EMPLOYEE);

		// Throws cast exception
        Institution institution = Iterables.getFirst(associations, null);
	}

	@Test
	public void findAssociationsByRole_oneMatchingRoleWithCorrectTypeMatchingReturnType_returnsInstitution() {
        Person person = new Person(1L, "Harry", "Potter");
        Department department = new Department("Gryffindor");
        Institution school = new Institution("Hogwarts");

		person.addAssociation(AssociationRole.EMPLOYEE, department);
		person.addAssociation(AssociationRole.STUDENT, school);

		// Assert
		Set<Institution> associations = person.findAssociationsByTypeAndRole(Institution.class, AssociationRole.STUDENT);

		assertThat(associations.size(), is(1));

        Institution actualInstitution = Iterables.getFirst(associations, null);
		assertThat(actualInstitution, is(school));
	}
	
	@Test
	public void findAssociationsByRole_oneNonMatchingRole_returnsZeroParties() {
        Person person = new Person(1L, "Harry", "Potter");
        Department department = new Department("Gryffindor");

		person.addAssociation(AssociationRole.EMPLOYEE, department);
		
		// Assert
		assertThat(person.findAssociationsByRole(AssociationRole.STUDENT).size(), is(0));
	}
	
	@Test
	public void findAssociationsByRole_twoMatchingRoles_returnsTwoParties() {
         Person person = new Person(1L, "Harry", "Potter");
		Department department = new Department("Gryffindor");
		Institution school = new Institution("Hogwarts");
		
		person.addAssociation(AssociationRole.EMPLOYEE, department);
		person.addAssociation(AssociationRole.STUDENT, school);
		
		// Assert
		Set<Organization> parties = person.findAssociationsByRole(AssociationRole.EMPLOYEE, AssociationRole.STUDENT);
		
		assertThat(parties.size(), is(2));
		assertThat(parties, hasItems(department, school));
	}
	
	@Test
	public void findAssociationsByRole_oneMatchingAndOneNotMatchingRole_returnsOneParty() {
        Person person = new Person(1L, "Harry", "Potter");
        Department department = new Department("Gryffindor");
        Institution school = new Institution("Hogwarts");
		
		person.addAssociation(AssociationRole.EMPLOYEE, department);
		person.addAssociation(AssociationRole.STUDENT, school);
		
		// Assert
		Set<Department> parties = person.findAssociationsByRole(AssociationRole.EMPLOYEE, AssociationRole.FACULTY);
		
		assertThat(parties.size(), is(1));
		assertThat(parties, contains(department));
	}

}
