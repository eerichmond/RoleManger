package com.eerichmond.core.security;

import com.eerichmond.core.domain.Department;
import com.eerichmond.core.domain.Organization;
import com.eerichmond.core.domain.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static com.eerichmond.core.codes.BooleanOperator.AND;
import static com.eerichmond.core.codes.BooleanOperator.OR;
import static com.eerichmond.core.security.AssociationRole.EMPLOYEE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RoleOperatorTest {
	
	private Person person;
	private Department org1;
	private Department org2;
	private Department org3;
	private Department org4;

	@Before
	public void setUp() {
		person = new Person(1L, "First", "First");
		org1 = new Department("Org1");
		org2 = new Department("Org2");
		org3 = new Department("Org3");
		org4 = new Department("Org4");
	}

	@Test
	public void not_trueOperand_returnsFalse() {
		Set<Association> roles = createEmployeeRoles(org1);
		
		RoleExpression operator = RoleOperator.not(EMPLOYEE.of(org1));
		
		assertThat(operator.matches(roles), is(false));
	}

	@Test
	public void not_falseOperand_returnsTrue() {
		Set<Association> roles = createEmployeeRoles(org1);
		
		RoleExpression operator = RoleOperator.not(EMPLOYEE.of(org2));
		
		assertThat(operator.matches(roles), is(true));
	}

	@Test
	public void and_twoTrueOperands_returnsTrue() {
		Set<Association> roles = createEmployeeRoles(org1, org2);

		RoleExpression operator = RoleOperator.create(
			EMPLOYEE.of(org1), AND, EMPLOYEE.of(org2)
		);

		assertThat(operator.matches(roles), is(true));
	}

	@Test
	public void and_oneTrueAndOneFalseOperand_returnsFalse() {
		Set<Association> roles = createEmployeeRoles(org1);

		RoleExpression operator = RoleOperator.create(
			EMPLOYEE.of(org1), AND, EMPLOYEE.of(org2)
		);

		assertThat(operator.matches(roles), is(false));
	}

	@Test
	public void and_falseOperandAndTrueAndOperator_returnsFalse() {
		Set<Association> roles = createEmployeeRoles(org1, org2);

		RoleExpression andOperator = RoleOperator.create(
			EMPLOYEE.of(org1), AND, EMPLOYEE.of(org2)
		);

		assertThat(andOperator.matches(roles), is(true));

		RoleExpression andOperatorChained = EMPLOYEE.of(org3).and(andOperator);

		assertThat(andOperatorChained.matches(roles), is(false));
	}

	@Test
	public void and_trueOperandAndFalseAndOperator_returnsFalse() {
		Set<Association> roles = createEmployeeRoles(org1);

		RoleExpression andOperator = RoleOperator.create(
			EMPLOYEE.of(org2), AND, EMPLOYEE.of(org3)
		);

		assertThat(andOperator.matches(roles), is(false));

		RoleExpression andOperatorChained = EMPLOYEE.of(org1).and(andOperator);

		assertThat(andOperatorChained.matches(roles), is(false));
	}

	@Test
	public void and_trueOperandAndTrueAndOperator_returnsTrue() {
		Set<Association> roles = createEmployeeRoles(org1, org2, org3);

		RoleExpression andOperator = RoleOperator.create(
			EMPLOYEE.of(org1), AND, EMPLOYEE.of(org2)
		);

		assertThat(andOperator.matches(roles), is(true));

		RoleExpression andOperatorChained = EMPLOYEE.of(org3).and(andOperator);

		assertThat(andOperatorChained.matches(roles), is(true));
	}

	@Test
	public void or_twoTrueOperands_returnsTrue() {
		Set<Association> roles = createEmployeeRoles(org1, org2);

		RoleExpression orOperator = RoleOperator.create(
			EMPLOYEE.of(org1), OR, EMPLOYEE.of(org2)
		);

		assertThat(orOperator.matches(roles), is(true));
	}

	@Test
	public void or_oneTrueAndOneFalseOperand_returnsTrue() {
		Set<Association> roles = createEmployeeRoles(org1);

		RoleExpression orOperator = RoleOperator.create(
			EMPLOYEE.of(org1), OR, EMPLOYEE.of(org2)
		);

		assertThat(orOperator.matches(roles), is(true));
	}

	@Test
	public void or_twoFalseOperands_returnsFalse() {
		Set<Association> roles = createEmployeeRoles(org3);

		RoleExpression orOperator = RoleOperator.create(
			EMPLOYEE.of(org1), OR, EMPLOYEE.of(org2)
		);

		assertThat(orOperator.matches(roles), is(false));
	}

	@Test
	public void or_falseOperandAndFalseAndOperator_returnsFalse() {
		Set<Association> roles = createEmployeeRoles(org1);

		RoleExpression andOperator = RoleOperator.create(
			EMPLOYEE.of(org2), AND, EMPLOYEE.of(org3)
		);

		assertThat(andOperator.matches(roles), is(false));

		RoleExpression orOperatorChained = EMPLOYEE.of(org4).or(andOperator);

		assertThat(orOperatorChained.matches(roles), is(false));
	}

	@Test
	public void or_falseOperandAndTrueAndOperator_returnsTrue() {
		Set<Association> roles = createEmployeeRoles(org1, org2);

		RoleExpression andOperator = RoleOperator.create(
			EMPLOYEE.of(org1), AND, EMPLOYEE.of(org2)
		);

		assertThat(andOperator.matches(roles), is(true));

		RoleExpression orOperatorChained = EMPLOYEE.of(org3).or(andOperator);

		assertThat(orOperatorChained.matches(roles), is(true));
	}

	@Test
	public void or_trueOperandAndFalseAndOperator_returnsTrue() {
		Set<Association> roles = createEmployeeRoles(org1);

		RoleExpression andOperator = RoleOperator.create(
			EMPLOYEE.of(org2), AND, EMPLOYEE.of(org3)
		);

		assertThat(andOperator.matches(roles), is(false));

		RoleExpression orOperatorChained = EMPLOYEE.of(org1).or(andOperator);

		assertThat(orOperatorChained.matches(roles), is(true));
	}

	@Test
	public void or_trueOperandAndTrueAndOperator_returnsTrue() {
		Set<Association> roles = createEmployeeRoles(org1, org2, org3);

		RoleExpression andOperator = RoleOperator.create(
			EMPLOYEE.of(org2), AND, EMPLOYEE.of(org3)
		);

		assertThat(andOperator.matches(roles), is(true));

		RoleExpression orOperatorChained = EMPLOYEE.of(org1).or(andOperator);

		assertThat(orOperatorChained.matches(roles), is(true));
	}

	@Test
	public void toString_notOperator_returnsNotDescription() {
		RoleExpression operator = RoleOperator.not(EMPLOYEE.of(org1));

		assertThat(operator.toString(), is("is not an employee of Org1"));
	}

	@Test
	public void toString_notOperatorOfNotOperator_returnsNotDescription() {
		RoleExpression subOperator = RoleOperator.not(EMPLOYEE.of(org1));
		RoleExpression operator = RoleOperator.not(subOperator);

		assertThat(operator.toString(), is("not ( is not an employee of Org1 )"));
	}

	@Test
	public void toString_notOperatorOfAndOperator_returnsNotDescription() {
		RoleExpression andOperator = RoleOperator.create(
				EMPLOYEE.of(org1), AND, EMPLOYEE.of(org2)
			);
		RoleExpression operator = RoleOperator.not(andOperator);

		assertThat(operator.toString(), is("not ( is an employee of Org1 and is an employee of Org2 )"));
	}

	@Test
	public void toString_orOperator_returnsOrDescription() {
		RoleExpression operator = RoleOperator.create(
			EMPLOYEE.of(org1), OR, EMPLOYEE.of(org2)
		);

		assertThat(operator.toString(), is("is an employee of Org1 or is an employee of Org2"));
	}

	@Test
	public void toString_andOperator_returnsAndDescription() {
		RoleExpression operator = RoleOperator.create(
			EMPLOYEE.of(org1), AND, EMPLOYEE.of(org2)
		);

		assertThat(operator.toString(), is("is an employee of Org1 and is an employee of Org2"));
	}

	@Test
	public void toString_operandAndedWithOrOperator_returnsAndDescription() {
		RoleExpression orOperator = RoleOperator.create(
			EMPLOYEE.of(org1), OR, EMPLOYEE.of(org2)
		);

		RoleExpression andOperator = EMPLOYEE.of(org3).and(orOperator);

		assertThat(andOperator.toString(), is("is an employee of Org3 and ( is an employee of Org1 or is an employee of Org2 )"));
	}

	@Test
	public void toString_andOperatorOredWithOrOperator_returnsAndDescription() {
		RoleExpression andOperator = RoleOperator.create(
				EMPLOYEE.of(org1), AND, EMPLOYEE.of(org2)
			);

		RoleExpression orOperator = RoleOperator.create(
			EMPLOYEE.of(org3), OR, EMPLOYEE.of(org4)
		);

		RoleExpression andAndOperator = andOperator.or(orOperator);

		assertThat(andAndOperator.toString(), is("( is an employee of Org1 and is an employee of Org2 ) or ( is an employee of Org3 or is an employee of Org4 )"));
	}

	private Set<Association> createEmployeeRoles(Organization... associatedOrganizations) {
		person.addAssociation(AssociationRole.EMPLOYEE, associatedOrganizations);

		return person.getActiveAssociations();
	}

}
