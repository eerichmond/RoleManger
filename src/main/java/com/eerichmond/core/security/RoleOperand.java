package com.eerichmond.core.security;

import com.eerichmond.core.codes.BooleanOperator;
import com.eerichmond.core.domain.Organization;
import com.eerichmond.core.domain.Party;
import com.eerichmond.core.utils.CollectionUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Set;

import static com.eerichmond.core.security.GlobalRole.SUBUNIT;

public class RoleOperand implements RoleExpression {

	private Role roleToEvaluate;
	private Set<Organization> organizationsToEvaluate = Sets.newHashSet();
	
	public RoleOperand(Role roleToEvaluate, Organization orgToEvaluate) {
		this(roleToEvaluate, Sets.newHashSet(orgToEvaluate));
	}
	
	public RoleOperand(Role roleToEvaluate, Collection<? extends Organization> organizationsToEvaluate) {
		Preconditions.checkNotNull(roleToEvaluate);
		Preconditions.checkNotNull(organizationsToEvaluate);

		this.roleToEvaluate = roleToEvaluate;
		this.organizationsToEvaluate.addAll(organizationsToEvaluate);
	}
	
	/**
	 * Indicates if the role and/or party match.
	 */
	public boolean matches(Set<Association> associations) {
		Preconditions.checkNotNull(this.roleToEvaluate);
		
		boolean matchFound = false;
		
		for (Association association : associations) {
			Organization org = association.getOrganization();
			
			if (org == null) {
				throw new IllegalArgumentException("Role associations must have a to party");
			}
			
			if (association.getRole().getCode().equals(roleToEvaluate.getCode())) {
				if (organizationsToEvaluate.contains(org)) {
					matchFound = true;
					break;
				}
				else {
					// If the org does not exist in the organizationsToEvaluate see if the org is a subunit
					// of one of the organizationsToEvaluate.
					matchFound = isASubunit(org);
					
					if (matchFound) {
						break;
					}
				}
			}
		}
		
		return matchFound;
	}

	/**
	 * If the to party is a subunit of one of the parties to evaluate return true.
	 * @param organization the organization to see if it is a subunit of the evaluated organizations.
	 */
	private boolean isASubunit(Organization organization) {
		boolean matchFound = false;
		
		for (Organization orgToEvaluate : organizationsToEvaluate) {
			matchFound = organization
				.isA( SUBUNIT.of(orgToEvaluate) )
				.isTrue();
			
			if (matchFound) {
				break;
			}
		}
		
		return matchFound;
	}
	
	public RoleExpression or(RoleExpression rightOperand) {
		return RoleOperator.create(this, BooleanOperator.OR, rightOperand);
	}
	
	public RoleExpression and(RoleExpression rightOperand) {
		return RoleOperator.create(this, BooleanOperator.AND, rightOperand);
	}
	
	@Override
	public String toString() {
		return toStringInternal(false);
	}
	
	public String toStringNegated() {
		return toStringInternal(true);
	}

	private String toStringInternal(boolean negated) {
		if (this.roleToEvaluate == null) {
			return "";
		}
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("is ");
		
		if (negated) {
			builder.append("not ");
		}

		// If the role starts with a vowel use "an" instead of "a"
		// (?iu) means case-insensitive
		if (this.roleToEvaluate.getCode().matches("(?iu)^[aeou].+")) {
			builder.append("an ");
		}
		else {
			builder.append("a ");
		}
		
		builder.append(this.roleToEvaluate.getDescription().toLowerCase());
		
		if (!this.organizationsToEvaluate.isEmpty()) {
			Set<String> names = Sets.newHashSet();
			
			for (Party party : this.organizationsToEvaluate) {
				names.add(party.getName());
			}
			
			builder
				.append(" of ")
				.append(CollectionUtils.toSentence(names, "or"));
		}
		
		return builder.toString();
	}

}
