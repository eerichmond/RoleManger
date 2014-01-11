package com.eerichmond.core.security;

import com.eerichmond.core.codes.BooleanOperator;
import com.google.common.base.Preconditions;

import java.util.Set;

public final class RoleOperator implements RoleExpression {
	
	private RoleExpression leftOperand;
	private BooleanOperator operator;
	private RoleExpression rightOperand;
	
	/**
	 * Constructor for the AND and OR operations.
	 * @param leftOperand the left hand role expression operand
	 * @param operator the Boolean operator
	 * @param rightOperand the left hand role expression operand
	 */
	private RoleOperator(RoleExpression leftOperand, BooleanOperator operator, RoleExpression rightOperand) {
		Preconditions.checkNotNull(leftOperand);
		Preconditions.checkNotNull(operator);
		Preconditions.checkNotNull(rightOperand);
		
		this.leftOperand = leftOperand;
		this.operator = operator;
		this.rightOperand = rightOperand;
	}
	
	/**
	 * Constructor for the NOT operation.
	 * @param operand the role expression to NOT
	 */
	private RoleOperator(RoleExpression operand) {
		Preconditions.checkNotNull(operand);
		
		this.operator = BooleanOperator.NOT;
		this.rightOperand = operand;
	}
	
	public boolean matches(Set<Association> associations) {
		switch (this.operator) {
			case NOT:
				return !rightOperand.matches(associations);
				
			case OR:
				return leftOperand.matches(associations) || rightOperand.matches(associations);

			case AND:
				return leftOperand.matches(associations) && rightOperand.matches(associations);
			
			default:
				throw new IllegalArgumentException("Invalid operator type " + this.operator);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sentence = new StringBuilder();
		
		if (operator == BooleanOperator.NOT) {
			boolean isTypeRoleOperand = rightOperand.getClass().isAssignableFrom(RoleOperand.class);
			
			// If the right operand is a single operand then pass the "NOT" down to the right operand's toString to
			// make the sentence more "English" like.
			if (isTypeRoleOperand) {
				sentence.append(((RoleOperand) rightOperand).toStringNegated());
			}
			else {
				sentence.append("not ( ").append(rightOperand.toString()).append(" )");
			}
		}
		else {
			wrapInParentheses(leftOperand, sentence);
			
			sentence.append(" ").append(this.operator.name().toLowerCase()).append(" ");
			
			wrapInParentheses(rightOperand, sentence);
		}
		
		return sentence.toString();
	}

	/**
	 * If the rule expression is an operator of other operands wrap the role expression in parentheses.
	 * @param roleExpression the role expression to wrap in parentheses if needed.
	 * @param sentence the string builder to append to
	 */
	private void wrapInParentheses(RoleExpression roleExpression, StringBuilder sentence) {
		boolean isOperandTypeOperator = roleExpression.getClass().isAssignableFrom(RoleOperator.class);
		
		if (isOperandTypeOperator) {
			sentence.append("( ");
		}
		
		sentence.append(roleExpression.toString());
		
		if (isOperandTypeOperator) {
			sentence.append(" )");
		}
	}
	
	/**
	 * "OR"s this role expression with the right role expression.
	 * @param rightOperand the right hand role expression.
	 * @return a new role expression ORing this expression and the right expression
	 */
	public RoleExpression or(RoleExpression rightOperand) {
		return new RoleOperator(this, BooleanOperator.OR, rightOperand);
	}
	
	/**
	 * "AND"s this role expression with the right role expression.
	 * @param rightOperand the right hand role expression.
	 * @return a new role expression ANDing this expression and the right expression
	 */
	public RoleExpression and(RoleExpression rightOperand) {
		return new RoleOperator(this, BooleanOperator.AND, rightOperand);
	}
	
	public static RoleExpression create(RoleExpression leftOperand, BooleanOperator operator, RoleExpression rightOperand) {
		return new RoleOperator(leftOperand, operator, rightOperand);
	}

	public static RoleExpression not(RoleExpression operand) {
		return new RoleOperator(operand);
	}
	
}
