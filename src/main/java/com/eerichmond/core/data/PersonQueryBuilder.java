package com.eerichmond.core.data;

import com.eerichmond.core.domain.HierarchyLevel;
import com.eerichmond.core.domain.QPerson;
import com.eerichmond.core.security.GlobalRole;
import com.eerichmond.core.security.QRoleRelationship;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class PersonQueryBuilder {
	
	private static final Logger LOG = LoggerFactory.getLogger(PersonQueryBuilder.class);
	
    public final static QPerson Q_PERSON = QPerson.person;
	public final static QRoleRelationship ORG_ASSOCIATION = new QRoleRelationship("association");
	public final static QRoleRelationship PARENT_ORG_ASSOCIATION = new QRoleRelationship("parentOrgAssociation");
	public final static QRoleRelationship PARENT_PARENT_ORG_ASSOCIATION = new QRoleRelationship("parentParentOrgAssociation");

	private final PersonSearchCriteria criteria;
	
	public PersonQueryBuilder(PersonSearchCriteria criteria) {
		this.criteria = criteria;
	}

	public JPAQuery query(EntityManager em) {
		JPAQuery query = new JPAQuery(em)
			.from(Q_PERSON)
			.innerJoin(Q_PERSON.relationships, ORG_ASSOCIATION);

		// If an ID was NOT specified join to the person's group associations' parent units to help filter down the population.
		// Also multiple records could be returned, so order by last name and first name.
		if (isBlank(criteria.getEmployeeId()) && isBlank(criteria.getStudentId())) {
			if (criteria.highestOrgHierarchyLevel().compareTo(HierarchyLevel.TWIG) >= 0) {
				query.innerJoin(ORG_ASSOCIATION.parentParty.relationships, PARENT_ORG_ASSOCIATION);
			}

			if (criteria.highestOrgHierarchyLevel().compareTo(HierarchyLevel.BRANCH) >= 0) {
				query.innerJoin(PARENT_ORG_ASSOCIATION.parentParty.relationships, PARENT_PARENT_ORG_ASSOCIATION);
			}

			query.orderBy(Q_PERSON.lastNameSearchable.asc(), Q_PERSON.firstNameSearchable.asc());
		}

		query.where(where());

		return query;
	}

	/**
	 * Returns the where clause for the query based on the criteria passed into the constructor. This method has
	 * package level access to allow for testing.
	 * @return the where clause.
	 */
	BooleanExpression where() {
		BooleanExpression where = null;

		// If an employee ID is provided search by that only
		if (isNotBlank(criteria.getStudentId()) && isNotBlank(criteria.getEmployeeId())) {
			
			where = Q_PERSON.studentId.eq(criteria.getStudentId())
				.or(Q_PERSON.employeeId.eq(criteria.getEmployeeId()));
			
		}
		// If student ID was provided just search by that
		else if (isNotBlank(criteria.getStudentId())) {
			
			where = Q_PERSON.studentId.eq(criteria.getStudentId());
			
		}
		// If employee ID was provided just search by that
		else if (isNotBlank(criteria.getEmployeeId())) {
			
			where = Q_PERSON.employeeId.eq(criteria.getEmployeeId());
			
		}
		// Else search by name and filter down by the person's associations if there are any
		else {
			String firstName = criteria.makeSearchable(criteria.getFirstName());
			String lastName = criteria.makeSearchable(criteria.getLastName());
			String nameFragment = criteria.makeSearchable(criteria.getNameFragment());
			
			if (isNotBlank(lastName) && isNotBlank(firstName)) {
				
				where = Q_PERSON.lastNameSearchable.startsWith(lastName)
					.and(Q_PERSON.firstNameSearchable.startsWith(firstName));
				
			} else if (isNotBlank(lastName)) {
				
				where = Q_PERSON.lastNameSearchable.startsWith(lastName);
				
			} else if (isNotBlank(firstName)) {
				
				where = Q_PERSON.firstNameSearchable.startsWith(firstName);
				
			} else if (isNotBlank(nameFragment)) {
				
				where = Q_PERSON.lastNameSearchable.startsWith(nameFragment)
						.or(Q_PERSON.firstNameSearchable.startsWith(nameFragment));
				
			}

			// Reduce the population by the person's associations
			if (!criteria.getOrganizations().isEmpty()) {
				where = where == null ? associationClause() : where.and(associationClause());
			}
		}

		// Reduce the population to only those that have the specified role(s)
		if (criteria.getRole() != null) {
			where = where == null ? roleClause() : where.and(roleClause());
		}

		LOG.debug( where == null ? "" : where.toString());

		return where;
	}

	private BooleanExpression roleClause() {
		return ORG_ASSOCIATION.role.eq(criteria.getRole().asRoleImpl());
	}

	private BooleanExpression associationClause() {
		BooleanExpression associationClause = ORG_ASSOCIATION.parentParty.in(criteria.getOrganizations());

		if (criteria.highestOrgHierarchyLevel().compareTo(HierarchyLevel.TWIG) >= 0) {
			associationClause = associationClause
				.or(
					PARENT_ORG_ASSOCIATION.role.eq(GlobalRole.SUBUNIT.asRoleImpl())
						.and(PARENT_ORG_ASSOCIATION.parentParty.in(criteria.getOrganizations()))
				);
		}

		if (criteria.highestOrgHierarchyLevel().compareTo(HierarchyLevel.BRANCH) >= 0) {
			associationClause = associationClause
				.or(
					PARENT_PARENT_ORG_ASSOCIATION.role.eq(GlobalRole.SUBUNIT.asRoleImpl())
						.and(PARENT_PARENT_ORG_ASSOCIATION.parentParty.in(criteria.getOrganizations()))
				);
		}

		return associationClause;
	}
}
