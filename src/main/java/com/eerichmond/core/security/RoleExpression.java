package com.eerichmond.core.security;

import java.util.Set;

public interface RoleExpression {
	
	/**
	 * Returns true if at least one of the specified associations matches this expression.
	 * @param associations the associations to evaluate against this expression.
	 */
	boolean matches(Set<Association> associations);
	
	/**
	 * Creates a new role expression that AND's this object and the right role expression.
	 * @param rightOperand the role expression to AND to this object.
	 * @return a new role expression for chaining.
	 */
	RoleExpression or(RoleExpression rightOperand);
	
	/**
	 * Creates a new role expression that OR's this object and the right role expression.
	 * @param rightOperand the role expression to OR to this object.
	 * @return a new role expression for chaining.
	 */
	RoleExpression and(RoleExpression rightOperand);
	
}
