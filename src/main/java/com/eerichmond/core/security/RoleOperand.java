package com.eerichmond.core.security;

import com.eerichmond.core.codes.BooleanOperator;
import com.eerichmond.core.domain.Party;
import com.eerichmond.core.utils.CollectionUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Set;

import static com.eerichmond.core.security.GlobalRole.SUBUNIT;

public class RoleOperand implements RoleExpression {

	private Role roleToEvaluate;
	private Set<Party> partiesToEvaluate = Sets.newHashSet();
	
	public RoleOperand(Role roleToEvaluate, Party partyToEvaluate) {
		this(roleToEvaluate, Sets.newHashSet(partyToEvaluate));
	}
	
	public RoleOperand(Role roleToEvaluate, Collection<? extends Party> partiesToEvaluate) {
		Preconditions.checkNotNull(roleToEvaluate);
		Preconditions.checkNotNull(partiesToEvaluate);

		this.roleToEvaluate = roleToEvaluate;
		this.partiesToEvaluate.addAll(partiesToEvaluate);
	}
	
	/**
	 * Indicates if the role and/or party match.
	 */
	public boolean matches(Set<Association> associations) {
		Preconditions.checkNotNull(this.roleToEvaluate);
		
		boolean matchFound = false;
		
		for (Association association : associations) {
			Party parentParty = association.getParentParty();
			
			if (parentParty == null) {
				throw new IllegalArgumentException("Role associations must have a to party");
			}
			
			if (association.getRole().getCode().equals(roleToEvaluate.getCode())) {
				if (partiesToEvaluate.contains(parentParty)) {
					matchFound = true;
					break;
				}
				else {
					// If the parentParty does not exist in the partiesToEvaluate see if the parentParty is a subunit
					// of one of the partiesToEvaluate.
					matchFound = isASubunit(parentParty);
					
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
	 * @param parentParty the parent party to see if it is a subunit of the the evaluate parties.
	 */
	private boolean isASubunit(Party parentParty) {
		boolean matchFound = false;
		
		for (Party partyToEvaluate : partiesToEvaluate) {
			matchFound = parentParty
				.isA( SUBUNIT.of(partyToEvaluate) )
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
		
		if (!this.partiesToEvaluate.isEmpty()) {
			Set<String> names = Sets.newHashSet();
			
			for (Party party : this.partiesToEvaluate) {
				names.add(party.getName());
			}
			
			builder
				.append(" of ")
				.append(CollectionUtils.toSentence(names, "or"));
		}
		
		return builder.toString();
	}

}
