package com.eerichmond.core.security;

import com.eerichmond.core.domain.Organization;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Collection;

public enum GlobalRole implements Role {
	EMPLOYEE,
	FACULTY,
	STUDENT,
	SUBUNIT,
	SUPERVISOR,
	SYSTEM_ADMIN,
	SYSTEM_TESTER,
	USER							// Generic system user
	;

	@Override
	public String getCode() { return name(); }

	@Override
	public String getDescription() {
		return WordUtils.capitalizeFully(name().replace("_", " "));
	}
	
	/**
	 * Determines if the user has the role for the specified party.
	 * @param orgToEvaluate the organization to evaluate if the person is a role in
	 */
	public RoleExpression of(Organization orgToEvaluate) {
		return new RoleOperand(this, orgToEvaluate);
	}
	
	/**
	 * Determines if the user has the role for the specified party. Synonym for of().
	 * @param orgToEvaluate the organization to evaluate if the person is a role in
	 */
	public RoleExpression under(Organization orgToEvaluate) {
		return this.of(orgToEvaluate);
	}
	
	/**
	 * Determines if the user has the role for any of the specified organizations.
	 * @param organizations the organizations to look for roles for
	 */
	public RoleExpression ofAny(Organization... organizations) {
		return ofAny(Lists.newArrayList(organizations));
	}
	
	/**
	 * Determines if the user has the role for any of the specified organizations.
	 * @param organizations the organizations to look for roles for
	 */
	public RoleExpression ofAny(Collection<? extends Organization> organizations) {
		return new RoleOperand(this, organizations);
	}

	public RoleImpl asRoleImpl() {
		return new RoleImpl(this);
	}
}
