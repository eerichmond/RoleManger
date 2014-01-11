package com.eerichmond.core.security;

import com.eerichmond.core.domain.Party;
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
	 * @param partyToEvaluate the party to evaluate if the person is a role in
	 */
	public RoleExpression of(Party partyToEvaluate) {
		return new RoleOperand(this, partyToEvaluate);
	}
	
	/**
	 * Determines if the user has the role for the specified party. Synonym for of().
	 * @param partyToEvaluate the party to evaluate if the person is a role in
	 */
	public RoleExpression under(Party partyToEvaluate) {
		return this.of(partyToEvaluate);
	}
	
	/**
	 * Determines if the user has the role for any of the specified parties.
	 * @param parties the parties to look for roles for
	 */
	public RoleExpression ofAny(Party... parties) {
		return ofAny(Lists.newArrayList(parties));
	}
	
	/**
	 * Determines if the user has the role for any of the specified parties.
	 * @param parties the parties to look for roles for
	 */
	public RoleExpression ofAny(Collection<? extends Party> parties) {
		return new RoleOperand(this, parties);
	}

	public RoleImpl asRoleImpl() {
		return new RoleImpl(this);
	}
}
