package com.eerichmond.core.security;

import com.eerichmond.core.domain.Department;
import com.eerichmond.core.domain.Organization;
import com.eerichmond.core.domain.Person;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RoleOperandTest {

	private Person person1;
	private Department org1;
	private Department org2;
	private Department org3;
	
	@Before
	public void setUp() {
		person1 = new Person(1L, "First", "First");
		org1 = new Department("Org1");
		org2 = new Department("Org2");
		org3 = new Department("Org3");
	}
	
	@Test
	public void matches_oneDeptRoleMatchesDept_returnsTrue() {
		Set<Association> roles = createEmployeeRoles(org1);
		
		RoleOperand resolver = new RoleOperand(AssociationRole.EMPLOYEE, org1);
		
		assertThat(resolver.matches(roles), is(true));
	}
	
	@Test
	public void matches_noRoles_returnsFalse() {
		Set<Association> roles = Sets.newHashSet();
		
		RoleOperand resolver = new RoleOperand(AssociationRole.EMPLOYEE, org1);

		assertThat(resolver.matches(roles), is(false));
	}
	
	@Test
	public void matches_twoDeptRoles_deptMatchesOneOfDepts_returnsTrue() {
		Set<Association> roles = createEmployeeRoles(org1, org2);
		
		RoleOperand resolver = new RoleOperand(AssociationRole.EMPLOYEE, org1);
		
		assertThat(resolver.matches(roles), is(true));
	}
	
	
	@Test
	public void matches_oneDeptRole_deptsAreDifferent_returnsFalse() {
		Set<Association> roles = createEmployeeRoles(org1);
		
		RoleOperand resolver = new RoleOperand(AssociationRole.EMPLOYEE, org2);
		
		assertThat(resolver.matches(roles), is(false));
	}
	
	@Test
	public void matches_oneDeptRole_deptIsASubUnitOfParentUnit_returnsTrue() {
		Department parentUnit = new Department("PARENT1");
		
		org1.addAssociation(AssociationRole.SUBUNIT, parentUnit);
		
		Set<Association> roles = createEmployeeRoles(org1);
		
		RoleOperand resolver = new RoleOperand(AssociationRole.EMPLOYEE, parentUnit);
		
		assertThat(resolver.matches(roles), is(true));
	}
	
	@Test
	public void matches_oneDeptRole_deptHasTwoParentUnits_deptIsASubSubUnitOfParentUnit_returnsTrue() {
		Department parentUnit1 = new Department("PARENT1");
		org1.addAssociation(AssociationRole.SUBUNIT, parentUnit1);
		
		Department grandparentUnit = new Department("GRANDPARENT1");
		parentUnit1.addAssociation(AssociationRole.SUBUNIT, grandparentUnit);
		
		Department parentUnit2 = new Department("PARENT2");
		org1.addAssociation(AssociationRole.SUBUNIT, parentUnit2);

		Set<Association> roles = createEmployeeRoles(org1);
		
		RoleOperand resolver = new RoleOperand(AssociationRole.EMPLOYEE, grandparentUnit);
		
		assertThat(resolver.matches(roles), is(true));
	}
	
	@Test
	public void matches_oneDeptRole_deptIsAsubSubUnitOfParentUnit_returnsTrue() {
		Department parentUnit = new Department("PARENT1");
		org1.addAssociation(AssociationRole.SUBUNIT, parentUnit);
		
		Department grandparentUnit = new Department("PARENT1_1");
		parentUnit.addAssociation(AssociationRole.SUBUNIT, grandparentUnit);
		
		Set<Association> roles = createEmployeeRoles(org1);
		
		RoleOperand resolver = new RoleOperand(AssociationRole.EMPLOYEE, grandparentUnit);
		
		assertThat(resolver.matches(roles), is(true));
	}
	
	@Test
	public void matches_oneDeptRole_deptIsASubUnitOfDifferentParentUnit_returnsFalse() {
		Department parentUnit1 = new Department("PARENT1");
		Department parentUnit2 = new Department("PARENT2");
		
		org1.addAssociation(AssociationRole.SUBUNIT, parentUnit1);
		
		Set<Association> roles = createEmployeeRoles(org1);
		
		RoleOperand resolver = new RoleOperand(AssociationRole.EMPLOYEE, parentUnit2);
		
		assertThat(resolver.matches(roles), is(false));
	}
	
	@Test
	public void matches_oneDeptRole_deptIsOneOfTwoValidDepts_returnsTrue() {
		Set<Association> roles = createEmployeeRoles(org1);
		
		RoleOperand resolver = new RoleOperand( AssociationRole.EMPLOYEE, Lists.newArrayList( org1, org2 ));
		
		assertThat(resolver.matches(roles), is(true));
	}
	
	@Test
	public void matches_oneDeptRole_deptIsNotOneOfTwoValidDept_returnsFalse() {
		Set<Association> roles = createEmployeeRoles(org1);
		
		RoleOperand resolver = new RoleOperand( AssociationRole.EMPLOYEE, Lists.newArrayList( org2, org3 ));
		
		assertThat(resolver.matches(roles), is(false));
	}
	
	@Test
	public void toString_roleEmployee_returnsDescription() {
		RoleOperand resolver = new RoleOperand(AssociationRole.EMPLOYEE, org1);
		
		assertThat(resolver.toString(), is("is an employee of Org1"));
	}
	
	@Test
	public void toString_roleEmployeeAndTwoParties_returnsDescription() {
		RoleOperand resolver = new RoleOperand( AssociationRole.EMPLOYEE, Lists.newArrayList( org1, org2 ));
		
		assertThat(resolver.toString(), is("is an employee of Org1 or Org2"));
	}
	
	@Test
	public void toString_roleEmployeeOfAndThreeParties_returnsDescription() {
		RoleOperand resolver = new RoleOperand( AssociationRole.EMPLOYEE, Lists.newArrayList( org1, org2, org3 ));
		
		assertThat(resolver.toString(), is("is an employee of Org1, Org2 or Org3"));
	}
	
	@Test
	public void toString_roleStudent_returnsDescription() {
		RoleOperand resolver = new RoleOperand(AssociationRole.STUDENT, org1);
		
		assertThat(resolver.toString(), is("is a student of Org1"));
	}
	
	private Set<Association> createEmployeeRoles(Organization... toParties) {
		person1.addAssociation(AssociationRole.EMPLOYEE, toParties);
		
		return person1.getActiveAssociations();
	}
	
}
