package com.eerichmond.core.security;

import com.eerichmond.core.codes.BooleanOperator;

import java.util.Set;

/**
 * A role expression that takes in a boolean value for the hasPermission regardless of the person's associations along
 * with a description of what the boolean value means.
 */
public class StaticRoleExpression implements RoleExpression {

	private final boolean hasPermission;
	private final String description;

	public StaticRoleExpression(boolean hasPermission, String description) {
		this.hasPermission = hasPermission;
		this.description = description;
	}

	@Override
	public boolean matches(Set<Association> associations) {
		return hasPermission;
	}

	@Override
	public RoleExpression or(RoleExpression rightOperand) {
		return RoleOperator.create(this, BooleanOperator.OR, rightOperand);
	}

	@Override
	public RoleExpression and(RoleExpression rightOperand) {
		return RoleOperator.create(this, BooleanOperator.AND, rightOperand);
	}

	@Override
	public String toString() {
		return this.description;
	}

	public String toStringNegated() {
		return "not " + toString();
	}
}
