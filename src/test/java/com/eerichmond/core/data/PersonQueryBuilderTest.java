package com.eerichmond.core.data;

import com.eerichmond.core.domain.Institution;
import com.eerichmond.core.security.AssociationRole;
import com.mysema.query.types.expr.BooleanExpression;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class PersonQueryBuilderTest {

	private PersonQueryBuilder queryBuilder;
	
	private PersonSearchCriteria criteria = new PersonSearchCriteria();
	
	@Before
	public void setUp() {
		queryBuilder = new PersonQueryBuilder(criteria);
	}
	
	@Test
	public void where_default_isNull() {
		// Act
		BooleanExpression where = queryBuilder.where();

		assertThat(where, is(nullValue()));
	}
	
	@Test
	public void where_hasStudentId_containsStudentId() {
		// Arrange
		criteria.setStudentId("123456789");
		
		// Act
		BooleanExpression where = queryBuilder.where();
				
		assertThat(where.toString(), is("person.studentId = 123456789"));
	}
	
	@Test
	public void where_hasEmployeeId_containsEmployeeId() {
		// Arrange
		criteria.setEmployeeId("123456789");
		
		// Act
		BooleanExpression where = queryBuilder.where();
				
		assertThat(where.toString(), is("person.employeeId = 123456789"));
	}
	
	@Test
	public void where_hasEmployeeIdAndStudentId_containsEmployeeIdAndStudentId() {
		// Arrange
		criteria.setEmployeeId("123456789")
			.setStudentId("123456789");
		
		// Act
		BooleanExpression where = queryBuilder.where();
				
		assertThat(where.toString(), is("person.studentId = 123456789 || person.employeeId = 123456789"));
	}
	
	@Test
	public void where_hasFirstAndLastName_containsFirstAndLastName() {
		// Arrange
		criteria.setFullName("Potter, Harry");
		
		// Act
		BooleanExpression where = queryBuilder.where();
				
		assertThat(where.toString(), is("startsWith(person.lastNameSearchable,POTTER) && startsWith(person.firstNameSearchable,HARRY)"));
	}
	
	@Test
	public void where_hasCommaFirstName_containsFirstName() {
		// Arrange
		criteria.setFullName(",Harry");
		
		// Act
		BooleanExpression where = queryBuilder.where();
				
		assertThat(where.toString(), is("startsWith(person.firstNameSearchable,HARRY)"));
	}

	@Test
	public void where_hasLastNameComma_containsLastName() {
		// Arrange
		criteria.setFullName("Potter,");
		
		// Act
		BooleanExpression where = queryBuilder.where();
				
		assertThat(where.toString(), is("startsWith(person.lastNameSearchable,POTTER)"));
	}
	
	@Test
	public void where_hasLastName_containsLastName() {
		// Arrange
		criteria.setFullName("Potter");
		
		// Act
		BooleanExpression where = queryBuilder.where();
				
		assertThat(where.toString(), is("startsWith(person.lastNameSearchable,POTTER)"));
	}
	
	@Test
	public void where_hasNameFragmentAndNoComma_containsFirstOrLastName() {
		// Arrange
		criteria.setNameFragment("Potter");
		
		// Act
		BooleanExpression where = queryBuilder.where();
				
		assertThat(where.toString(), is("startsWith(person.lastNameSearchable,POTTER) || startsWith(person.firstNameSearchable,POTTER)"));
	}

	@Test
	public void where_roleAndLastName_containsRoleAndLastName() {
		// Arrange
		criteria
			.setRole(AssociationRole.STUDENT)
			.setLastName("Doe");

		// Act
		BooleanExpression where = queryBuilder.where();

		assertThat(where.toString(), is("startsWith(person.lastNameSearchable,DOE) && association.role = STUDENT"));
	}

	@Test
	public void where_role_containsRole() {
		// Arrange
		criteria
			.setRole(AssociationRole.STUDENT);

		// Act
		BooleanExpression where = queryBuilder.where();

		assertThat(where.toString(), is("association.role = STUDENT"));
	}

	@Test
	public void where_institution_searchesCurrentAssociationsAndUpTheHierarchyTwoLevels() {
		// Arrange
		Institution institution = new Institution("Hogwarts");

		criteria
			.addOrganization(institution);

		// Act
		BooleanExpression where = queryBuilder.where();

		assertThat(where.toString(), is("association.organization = " + institution.getName() +
			" || parentOrgAssociation.role = SUBUNIT && parentOrgAssociation.organization = " + institution.getName() +
			" || parentParentOrgAssociation.role = SUBUNIT && parentParentOrgAssociation.organization = " + institution.getName()));
	}
}
